package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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

@Named
@SessionScoped
public class PostManagerBean implements Serializable {
	private static final long serialVersionUID = -8086231512451529155L;

	private List<Post> allPosts;

	private String newPostText;
	
	private Map<Integer, Boolean> toggleStates;
	
	@Inject
	private PostFactory postFactory;

	@Inject
	private UserFactory userFactory;

	@Inject
	private PointFactory pointFactory;

	@Inject
	private ReportFactory reportFactory;

	@Inject
	private CommentFactory commentFactory;

	/* UI */
	private int sortBy;
	private Map<String, Integer> sortMethods;

	private String showAll = "1";

	final ResourceBundle msgs = FacesContext.getCurrentInstance().getApplication()
			.getResourceBundle(FacesContext.getCurrentInstance(), "msgs");
	/* /UI */

	@PostConstruct
	public void init() {
		/* UI */
		sortMethods = new LinkedHashMap<String, Integer>();
		// TODO
		// sortMethods.put(msgs.getString("newestFirst"), 0);
		// sortMethods.put(msgs.getString("oldestFirst"), 1);
		// sortMethods.put(msgs.getString("mostPopFirst"), 2);
		sortMethods.put("Newest first", 0);
		sortMethods.put("Oldest first", 1);
		sortMethods.put("Most popular first", 2);
		/* /UI */

		allPosts = postFactory.getAll();
		
		toggleStates = new HashMap<>();
		for (int i = 0; i < allPosts.size(); i++) {
			toggleStates.put(allPosts.get(i).getId(), false);
		}
		
		sortAllPosts();
	}

	@Transactional
	public void createPost(User user) {
		user = userFactory.get(user);
		Post newPost = new Post();
		newPost.setText(newPostText);
		newPost.setTime(System.currentTimeMillis());
		newPost.setCommentList(new ArrayList<>());
		newPost.setPointList(new ArrayList<>());
		allPosts.add(newPost);
		postFactory.create(newPost, user);
		sortAllPosts();
		toggleStates.put(newPost.getId(), false);
		newPostText = "";
	}

	public void sortAllPosts() {
		Comparator<Post> comparator = new Comparator<Post>() {

			@Override
			public int compare(Post post1, Post post2) {
				return (post1.getTime() < post2.getTime()) ? 1 : (post1.getTime() > post2.getTime()) ? -1 : 0;
			}
		};

		switch (sortBy) {
		case 0:
			break;

		case 1:
			comparator = new Comparator<Post>() {

				@Override
				public int compare(Post post1, Post post2) {
					return (post1.getTime() > post2.getTime()) ? 1 : (post1.getTime() < post2.getTime()) ? -1 : 0;
				}
			};
			break;

		case 2:
			comparator = new Comparator<Post>() {

				@Override
				public int compare(Post post1, Post post2) {
					return (post1.getVotes() < post2.getVotes()) ? 1 : (post1.getVotes() > post2.getVotes()) ? -1 : 0;
				}
			};
			break;

		default:
			break;
		}

		allPosts.sort(comparator);
	}

	@Transactional
	public void upvotePost(Post post, User votingUser) {
		votingUser = userFactory.get(votingUser);
		post = postFactory.get(post);

		List<Point> allPoints = post.getPointList();

		boolean isVoteAllowed = true;
		for (int i = 0; i < allPoints.size(); i++) {
			Point point = allPoints.get(i);
			if (point.getUser().equals(votingUser) && point.getTextComponent().equals(post) && point.isUpVote()) {
				isVoteAllowed = false;
			} else if (point.getUser().equals(votingUser) && point.getTextComponent().equals(post) && !point.isUpVote()) {
				point.setVote(true);
				pointFactory.update(point);
				post.addPoint(point);
				postFactory.update(post);
				userFactory.update(votingUser);
				userFactory.update(post.getUser());
				return;
			}
		}

		if (isVoteAllowed) {
			System.out.println("creating new vote");
			Point point = new Point();
			point.setVote(true);
			point.setTime(System.currentTimeMillis());

			pointFactory.create(point, votingUser, post);
			postFactory.update(post);
			userFactory.update(post.getUser());

			sortAllPosts();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "You already upvoted this post.", ""));
		}

	}

	@Transactional
	public void downvotePost(Post post, User votingUser) {
		votingUser = userFactory.get(votingUser);
		post = postFactory.get(post);

		List<Point> allPoints = post.getPointList();

		boolean isVoteAllowed = true;
		for (int i = 0; i < allPoints.size(); i++) {
			Point point = allPoints.get(i);
			if (point.getUser().equals(votingUser) && point.getTextComponent().equals(post) && !point.isUpVote()) {
				isVoteAllowed = false;
			} else if (point.getUser().equals(votingUser) && point.getTextComponent().equals(post) && point.isUpVote()) {
				point.setVote(false);
				pointFactory.update(point);
				post.addPoint(point);
				postFactory.update(post);
				userFactory.update(votingUser);
				userFactory.update(post.getUser());
				return;
			}
		}

		if (isVoteAllowed) {
			Point point = new Point();
			point.setVote(false);
			point.setTime(System.currentTimeMillis());

			pointFactory.create(point, votingUser, post);
			postFactory.update(post);
			userFactory.update(post.getUser());

			sortAllPosts();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "You already downvoted this post.", ""));
		}
	}

	@Transactional
	public void deletePost(Post post, User loggedInUser) {
		loggedInUser = userFactory.get(loggedInUser);
		User postUser = userFactory.get(post.getUser());
		post = postFactory.get(post);

		List<Report> reports = reportFactory.getAll();

		if (postUser.getName().equals(loggedInUser.getName()) || loggedInUser.getRole().equals(Role.Administrator)
				|| loggedInUser.getRole().equals(Role.Moderator)) {
			List<Comment> commentList = post.getCommentList();

			// Delete all comments
			for (int i = 0; i < commentList.size(); i++) {

				// Delete all comment-reports
				for (int j = 0; j < reports.size(); j++) {
					if (reports.get(j).getTextComponent().getTextType() == TextType.Comment
							&& reports.get(j).getTextComponent().getId() == commentList.get(i).getId()) {
						reportFactory.delete(reports.get(j));
					}
				}
				
				//delete all comment points
				List<Point> commentPointList = commentList.get(i).getPointList();
				for (int j = 0; j < commentPointList.size(); j++) {
					pointFactory.delete(commentPointList.get(j));
				}
				
				commentFactory.delete(commentList.get(i));
			}

			// Delete all post-reports
			for (int i = 0; i < reports.size(); i++) {
				if (reports.get(i).getTextComponent().getTextType() == TextType.Post
						&& reports.get(i).getTextComponent().getId() == post.getId()) {
					reportFactory.delete(reports.get(i));
				}
			}
			
			List<Point> postPointList = post.getPointList();
			for (int i = 0; i < postPointList.size(); i++) {
				pointFactory.delete(postPointList.get(i));
			}
			
			postFactory.delete(post);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"You do not have permission to delete this post.", ""));
		}
	}

	@Transactional
	public List<Post> getAllPosts(User user) {
		if (showAll.equals("1")) {
			allPosts = postFactory.getAll();
			sortAllPosts();
			return allPosts;

		} else {
			user = userFactory.get(user);
			allPosts = postFactory.getAll();

			List<Post> posts = new ArrayList<>();

			for (int i = 0; i < allPosts.size(); i++) {
				if (allPosts.get(i).getUser().equals(user)) {
					posts.add(allPosts.get(i));
				}
			}

			allPosts = new ArrayList<>();
			allPosts.addAll(posts);
			sortAllPosts();
			return allPosts;
		}
	}

	public String getNewPostText() {
		return newPostText;
	}

	public void setNewPostText(String newPostText) {
		this.newPostText = newPostText;
	}

	/* UI */
	public int getSortBy() {
		return sortBy;
	}

	public void setSortBy(int sortBy) {
		this.sortBy = sortBy;
	}

	public Map<String, Integer> getSortMethods() {
		return sortMethods;
	}

	public void onSortByChange() {
		// todo: reload displayed posts
	}

	public String getShowAll() {
		return showAll;
	}

	public void setShowAll(String showAll) {
		this.showAll = showAll;
	}

	/* /UI */
	
	public boolean getToggleState(int id){
		return toggleStates.get(id);
	}
	
	public void switchToggleState(int id){
		boolean currentState = getToggleState(id);
		toggleStates.replace(id, !currentState);
	}

	public String getTimeDiff(Post post) {
		PrettyTime prettyTime = new PrettyTime(
				FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
		return prettyTime.format(new Date(post.getTime()));
	}

}
