package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import dhbw.lan.lantalk.persistence.factory.CommentFactory;
import dhbw.lan.lantalk.persistence.factory.PointFactory;
import dhbw.lan.lantalk.persistence.factory.PostFactory;
import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Point;
import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.User;

@Named
@SessionScoped
public class PostManagerBean implements Serializable{
	private static final long serialVersionUID = -8086231512451529155L;

	private List<Post> allPosts;

	private String newPostText;
	
	@Inject
	private PostFactory postFactory;
	
	@Inject
	private CommentFactory commentFactory;
	
	@Inject
	private UserFactory userFactory;
	
	@Inject
	private PointFactory pointFactory;
	
	
	@PostConstruct
	public void init(){	
		refreshAllPosts();
	}
	
	@Transactional
	public void createPost(){
		int userId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId"));
		User user = userFactory.get(userId);
		Post newPost = new Post();
		newPost.setText(newPostText);
		newPost.setUser(user);
		newPost.setTime(System.nanoTime());
		newPost.setCommentList(new ArrayList<>());
		newPost.setPointList(new ArrayList<>());
//		user.addPost(newPost);
		allPosts.add(newPost);
		postFactory.create(newPost, user);
		refreshAllPosts();
	}
	
	public void refreshAllPosts(){
		allPosts = postFactory.getAll();
		allPosts.sort(new Comparator<Post>() {

			@Override
			public int compare(Post post1, Post post2) {
				return (post1.getTime() < post2.getTime()) ? 1 : (post1.getTime() > post2.getTime()) ? -1 : 0 ;
			}
			
		});
	}
	
	@Transactional
	public void upvotePost(){
		int postId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("postId"));
		Post post = postFactory.get(postId);
		
		Point point = new Point();
		point.setVote(true);
		point.setTime(System.nanoTime());
		
		pointFactory.create(point, post.getUser(), post);
		postFactory.update(post);
		userFactory.update(post.getUser());
		
		refreshAllPosts();
		
	}
	
	@Transactional
	public void downvotePost(){
		int postId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("postId"));
		Post post = postFactory.get(postId);
		
		Point point = new Point();
		point.setVote(false);
		point.setTime(System.nanoTime());
		
		pointFactory.create(point, post.getUser(), post);
		postFactory.update(post);
		userFactory.update(post.getUser());
		refreshAllPosts();
	}
	
	public List<Post> getAllPosts() {
		return allPosts;
	}

	public String getNewPostText() {
		return newPostText;
	}

	public void setNewPostText(String newPostText) {
		this.newPostText = newPostText;
	}

}