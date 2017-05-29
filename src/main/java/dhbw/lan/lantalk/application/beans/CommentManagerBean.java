package dhbw.lan.lantalk.application.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import dhbw.lan.lantalk.persistence.factory.CommentFactory;
import dhbw.lan.lantalk.persistence.factory.PointFactory;
import dhbw.lan.lantalk.persistence.factory.PostFactory;
import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Comment;
import dhbw.lan.lantalk.persistence.objects.Point;
import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.User;

import java.io.Serializable;
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
	
	private String commentText;
	
	@Transactional
	public void createComment(User user, Post post){
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
	public void upvoteComment(Comment comment){
		comment = commentFactory.get(comment);
		Point point = new Point();
		point.setVote(true);
		point.setTime(System.currentTimeMillis());
		
		pointFactory.create(point, comment.getUser(), comment);
		commentFactory.update(comment);
		userFactory.update(comment.getUser());
	}
	
	@Transactional
	public void downvoteComment(Comment comment){
		comment = commentFactory.get(comment);
		Point point = new Point();
		point.setVote(false);
		point.setTime(System.currentTimeMillis());
		
		pointFactory.create(point, comment.getUser(), comment);
		commentFactory.update(comment);
		userFactory.update(comment.getUser());
	}
	
	public List<Comment> getComments(Post post){
		return post.getCommentList();
	}
	
	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}	
}
