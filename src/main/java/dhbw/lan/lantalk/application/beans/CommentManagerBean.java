package dhbw.lan.lantalk.application.beans;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
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

	//This is the comment field in the gui
	private String commentText;

	/**
	 * Create a comment.
	 * 
	 * @param user 
	 * 					The {@link User} that is creating the comment
	 * @param post 
	 * 					The {@link Post} that the {@link User} is commenting on
	 */
	@Transactional
	public void createComment(User user, Post post) {
		System.out.println("adding comment with text: " + commentText + " to " + post.getText());
		if (commentText.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null,
            		new FacesMessage(FacesMessage.SEVERITY_ERROR, "You can't submit an empty comment", ""));
		} else {
			user = userFactory.get(user);
			post = postFactory.get(post);
			Comment comment = new Comment();
			comment.setText(this.commentText);
			comment.setPost(post);
			comment.setTime(System.currentTimeMillis());
			comment.setPointList(new ArrayList<Point>());
			post.getCommentList().add(comment);
			commentFactory.create(comment, user);
			commentText = "";
		}
	}

	/**
	 * Upvoting of a comment. 
	 * It is important that one user can only vote once on a TextComponent.
	 * This functions checks, if the user already upvoted the comment and discards the operation in this case.
	 * In the second case a user already downvoted a comment. An upvote is allowed in this case. Therefore the {@link Point} record in the database is updated from upvote = 0 to upvote = 1
	 * If a user neither up- or downvoted a comment a new Point will be created.
	 * 
	 * @param comment 
	 * 					The {@link Comment} to be upvoted
	 * @param votingUser 
	 * 					The {@link User} that votes
	 */
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
			} else if (point.getUser().equals(votingUser) && point.getTextComponent().equals(comment)
					&& !point.isUpVote()) {
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

			pointFactory.create(point, votingUser, comment);
			commentFactory.update(comment);
			userFactory.update(comment.getUser());

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "You already upvoted this comment.", ""));
		}
	}

	
	/**
	 * Downvoting of a comment. 
	 * It is important that one user can only vote once on a TextComponent.
	 * This functions checks, if the user already downvoted the comment and discards the operation in this case.
	 * In the second case a user already upvoted a comment. A downvote is allowed in this case. Therefore the {@link Point} record in the database is updated from upvote = 1 to upvote = 0
	 * If a user neither up- or downvoted a comment a new Point will be created.
	 * 
	 * @param comment 
	 * 					The {@link Comment} to be upvoted
	 * @param votingUser 
	 * 					The {@link User} that votes
	 */
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

			pointFactory.create(point, votingUser, comment);
			commentFactory.update(comment);
			userFactory.update(comment.getUser());

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "You already downvoted this comment.", ""));
		}
	}

	/**
	 * Deletion of a comment. All corresponding objects ({@link Report}s, {@link Point}s) will be deleted too.
	 * 
	 * @param comment 
	 * 					The {@link Comment} that will be deleted.
	 * @param loggedInUser 
	 * 					The {@link User} that is currently logged in. Only the creator of the comment, Administrators and Moderators are allowed to delete a comment.
	 */
	@Transactional
	public void deleteComment(Comment comment, User loggedInUser) {
		loggedInUser = userFactory.get(loggedInUser);
		User commentUser = userFactory.get(comment.getUser());

		List<Report> reports = reportFactory.getAll();

		//A user should be able to delete his own post, that's why the annotation @RolesAllowed is not used here
		if (commentUser.equals(loggedInUser) || loggedInUser.getRole().equals(Role.Administrator)
				|| loggedInUser.getRole().equals(Role.Moderator)) {

			for (int i = 0; i < reports.size(); i++) {
				if (reports.get(i).getTextComponent().getTextType() == TextType.Comment
						&& reports.get(i).getTextComponent().getId() == comment.getId()) {
					reportFactory.delete(reports.get(i));
				}
			}
			
			//delete all comment points
			List<Point> commentPointList = comment.getPointList();
			for (int j = 0; j < commentPointList.size(); j++) {
				pointFactory.delete(commentPointList.get(j));
			}
			
			commentFactory.delete(comment);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"You do not have permission to delete this comment.", ""));
		}
	}

	/**
	 * 
	 * @param post
	 * 				The {@link Post}
	 * @return 
	 * 				A list of all comments that have been submitted to a {@link Post}.
	 */
	public List<Comment> getComments(Post post) {
		post = postFactory.get(post);
		List<Comment> commentList = post.getCommentList();

		commentList.sort(new Comparator<Comment>() {

			@Override
			public int compare(Comment comment1, Comment comment2) {
				return (comment1.getTime() > comment2.getTime()) ? 1
						: (comment1.getTime() < comment2.getTime()) ? -1 : 0;
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

	/**
	 * 
	 * @param comment {@link Comment}
	 * @return Human readable String of the time difference between now and the creation time of the Comment, e.g. "3 hours ago"
	 */
	public String getTimeDiff(Comment comment) {
		PrettyTime prettyTime = new PrettyTime(
				FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
		return prettyTime.format(new Date(comment.getTime()));
	}
}
