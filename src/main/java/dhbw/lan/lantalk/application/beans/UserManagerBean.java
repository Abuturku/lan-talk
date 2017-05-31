package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import dhbw.lan.lantalk.persistence.factory.CommentFactory;
import dhbw.lan.lantalk.persistence.factory.PostFactory;
import dhbw.lan.lantalk.persistence.factory.ReportFactory;
import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Comment;
import dhbw.lan.lantalk.persistence.objects.Point;
import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.Report;
import dhbw.lan.lantalk.persistence.objects.Role;
import dhbw.lan.lantalk.persistence.objects.TextComponent;
import dhbw.lan.lantalk.persistence.objects.TextType;
import dhbw.lan.lantalk.persistence.objects.User;

@Named
@RequestScoped
public class UserManagerBean implements Serializable {
	private static final long serialVersionUID = 3413209755795108052L;

	private User loggedInUser;

	@Inject
	private UserFactory userFactory;

	@Inject
	private ReportFactory reportFactory;

	@Inject
	private CommentFactory commentFactory;

	@Inject
	private PostFactory postFactory;

	@PostConstruct
	public void init() {

		this.loggedInUser = getUser();
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	private Optional<Principal> getPrincipal() {
		return Optional.ofNullable(getRequest().getUserPrincipal());
	}

	public String getLoginName() {
		return getPrincipal().map(p -> p.getName()).orElse("");
	}

	public boolean isAuthenticated() {
		return getPrincipal().isPresent();
	}

	public boolean isUserInRole(String role) {
		return getRequest().isUserInRole(role) || loggedInUser.getRole().equals(role);
	}

	public boolean isUserOwnerOrMod(TextComponent textcomponent) {

		if (textcomponent.getUser().getName().equals(loggedInUser.getName()) || loggedInUser.getRole().equals(Role.Administrator)
				|| loggedInUser.getRole().equals(Role.Moderator)) {
			return true;
		}
		return false;

	}

	public User getUser() {
		final Optional<Principal> principal = getPrincipal();
		if (principal.isPresent()) {
			if ((loggedInUser == null) || !principal.get().getName().equals(loggedInUser.getName())) {

				loggedInUser = userFactory.getByName(principal.get().getName());
				return loggedInUser;
			}
		}
		return null;
	}

	public String logout() {
		final HttpServletRequest request = getRequest();

		loggedInUser = null;

		try {
			request.logout();
			request.getSession().invalidate();
		} catch (final ServletException e) {
			e.printStackTrace();
		}

		return "login";
	}

	@Transactional
	@RolesAllowed(value = { Role.Administrator })
	public void promoteUserToMod(User user) {
		user = userFactory.get(user);
		user.setRole(Role.Moderator);
		userFactory.update(user);
	}

	@Transactional
	@RolesAllowed(value = { Role.Administrator })
	public void deleteUser(User user) {
		user = userFactory.get(user);

		List<Report> reports = reportFactory.getAll();

		List<Comment> commentList = getUserComments(user);
		List<Post> postList = getUserPosts(user);
		
		for (int i = 0; i < reports.size(); i++) {
			if (reports.get(i).getTextComponent().getUser().equals(user) || reports.get(i).getReporter().equals(user)) {
				reportFactory.delete(reports.get(i));
			}
		}
		
		for (int i = 0; i < commentList.size(); i++) {
			commentFactory.delete(commentList.get(i));
		}
		
		for (int i = 0; i < postList.size(); i++) {
			postFactory.delete(postList.get(i));
		}

		userFactory.delete(user);
	}

	public User getLoggedInUser() {
		return loggedInUser;
	}

	public String getUserName() {
		return userFactory.get(loggedInUser).getName();
	}

	public String getUserRank() {
		// TODO calculate user rank
		// Beginner, Advanced, Professional, Godlike
		return "Beginner";
	}

	public String getUserRole() {
		return userFactory.get(loggedInUser).getRole().toString();
	}

	public String getUserRegTime() {
		Date date = new Date(userFactory.get(loggedInUser).getRegTime());
		String pattern = "dd.MM.yyyy";
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	@Transactional
	public int getAmountOfPosts() {
		int amount = 0;
		List<Post> posts = userFactory.get(loggedInUser).getPostList();

		for (TextComponent post : posts) {
			if (post.getTextType() == TextType.Post) {
				amount++;
			}
		}

		return amount;
	}

	@Transactional
	public int getAmountOfComments() {
		int amount = 0;
		List<Comment> comments = userFactory.get(loggedInUser).getCommentList();

		for (TextComponent comment : comments) {
			if (comment.getTextType() == TextType.Comment) {
				amount++;
			}
		}

		return amount;
	}

	@Transactional
	public int getAmountOfVotes() {
		User user = userFactory.get(loggedInUser);
		
		int amount = 0;
		List<Point> pointList = user.getPoints();
		
		for (int i = 0; i < pointList.size(); i++) {
			if (pointList.get(i).isUpVote()) {
				amount++;
			}
		}
		
		return amount;
	}

	@Transactional
	public List<Comment> getUserComments(User user) {
		user = userFactory.get(user);
		List<Comment> commentList = user.getCommentList();
		for (int i = 0; i < commentList.size(); i++) {
			if (commentList.get(i).getTextType() != TextType.Comment && !commentList.get(i).getUser().equals(user)) {
				commentList.remove(commentList.get(i));
			}
		}

		return commentList;
	}

	@Transactional
	public List<Post> getUserPosts(User user) {
		user = userFactory.get(user);
		List<Post> postList = user.getPostList();
		for (int i = 0; i < postList.size(); i++) {
			if (postList.get(i).getTextType() != TextType.Post && !postList.get(i).getUser().equals(user)) {
				postList.remove(postList.get(i));
			}
		}

		return postList;
	}

}
