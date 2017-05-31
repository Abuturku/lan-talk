package dhbw.lan.lantalk.application.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.ocpsoft.prettytime.PrettyTime;

import dhbw.lan.lantalk.persistence.factory.CommentFactory;
import dhbw.lan.lantalk.persistence.factory.PointFactory;
import dhbw.lan.lantalk.persistence.factory.PostFactory;
import dhbw.lan.lantalk.persistence.factory.ReportFactory;
import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Comment;
import dhbw.lan.lantalk.persistence.objects.Point;
import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.Report;
import dhbw.lan.lantalk.persistence.objects.Role;
import dhbw.lan.lantalk.persistence.objects.TextType;
import dhbw.lan.lantalk.persistence.objects.User;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class CommentManagerBean implements Serializable {

	private static final long serialVersionUID = 822448000842189585L;

	@Inject
	private CommentFactory commentFactory;

	@Inject
	private PointFactory pointFactory;

	@Inject
	private UserFactory userFactory;

	@Inject
	private PostFactory postFactory;
	
	@Inject
	private ReportFactory reportFactory;

	private String commentText;

	@Transactional
	public void createComment(User user, Post post) {
		user = userFactory.get(user);
		Comment comment = new Comment();
		comment.setText(this.commentText);
		comment.setPost(post);
		comment.setTime(System.currentTimeMillis());
		post.getCommentList().add(comment);
		commentFactory.create(comment, user);
		userFactory.update(user);
		postFactory.update(post);
	}

	@Transactional
	public void upvoteComment(Comment comment, User votingUser) {
		votingUser = userFactory.get(votingUser);
		comment = commentFactory.get(comment);

		List<Point> allPoints = comment.getPointList();

		boolean isVoteAllowed = true;
		for (int i = 0; i < allPoints.size(); i++) {
			Point point = allPoints.get(i);
			if (point.getUser().equals(votingUser) && point.getTextComponent().equals(comment) && point.isUpVote()) {
				isVoteAllowed = false;
			} else if (point.getUser().equals(votingUser) && point.getTextComponent().equals(comment) && !point.isUpVote()) {
				point.setVote(true);
				pointFactory.update(point);
				comment.addPoint(point);
				commentFactory.update(comment);
				userFactory.update(votingUser);
				userFactory.update(comment.getUser());
				return;
			}
		}

		if (isVoteAllowed) {
			Point point = new Point();
			point.setVote(true);
			point.setTime(System.currentTimeMillis());

			pointFactory.create(point, comment.getUser(), comment);
			commentFactory.update(comment);
			userFactory.update(comment.getUser());

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "You already upvoted this comment.", ""));
		}
	}

	@Transactional
	public void downvoteComment(Comment comment, User votingUser) {
		votingUser = userFactory.get(votingUser);
		comment = commentFactory.get(comment);

		List<Point> allPoints = comment.getPointList();

		boolean isVoteAllowed = true;
		for (int i = 0; i < allPoints.size(); i++) {
			Point point = allPoints.get(i);
			if (point.getUser().equals(votingUser) && point.getTextComponent().equals(comment) && !point.isUpVote()) {
				isVoteAllowed = false;
			} else if (point.getUser().equals(votingUser) && point.getTextComponent().equals(comment) && point.isUpVote()) {
				point.setVote(true);
				pointFactory.update(point);
				comment.addPoint(point);
				commentFactory.update(comment);
				userFactory.update(votingUser);
				userFactory.update(comment.getUser());
				return;
			}
		}

		if (isVoteAllowed) {
			Point point = new Point();
			point.setVote(true);
			point.setTime(System.currentTimeMillis());

			pointFactory.create(point, comment.getUser(), comment);
			commentFactory.update(comment);
			userFactory.update(comment.getUser());

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "You already downvoted this comment.", ""));
		}
	}

	@Transactional
	public void deleteComment(Comment comment, User loggedInUser) {
		loggedInUser = userFactory.get(loggedInUser);
		User commentUser = userFactory.get(comment.getUser());

		List<Report> reports = reportFactory.getAll();
		
		if (commentUser.equals(loggedInUser) || loggedInUser.getRole().equals(Role.Administrator) || loggedInUser.getRole().equals(Role.Moderator)) {
			
			for (int i = 0; i < reports.size(); i++) {
				if (reports.get(i).getTextComponent().getTextType() == TextType.Comment && reports.get(i).getTextComponent().getId() == comment.getId()) {
					reportFactory.delete(reports.get(i));
				}
			}
			
			commentFactory.delete(comment);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"You do not have permission to delete this comment.", ""));
		}
	}

	public List<Comment> getComments(Post post) {
		List<Comment> commentList = post.getCommentList();
		
		commentList.sort(new Comparator<Comment>() {

			@Override
			public int compare(Comment comment1, Comment comment2) {
				return (comment1.getTime() > comment2.getTime()) ? 1 : (comment1.getTime() < comment2.getTime()) ? -1 : 0;
			}
		});
		
		return commentList;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	public String getTimeDiff(Comment comment){
		PrettyTime prettyTime = new PrettyTime(FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
		return prettyTime.format(new Date(comment.getTime()));
	}
}
