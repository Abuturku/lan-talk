package dhbw.lan.lantalk.application.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import dhbw.lan.lantalk.persistence.factory.CommentFactory;
import dhbw.lan.lantalk.persistence.factory.PointFactory;
import dhbw.lan.lantalk.persistence.factory.PostFactory;
import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Point;
import dhbw.lan.lantalk.persistence.objects.Post;

@ManagedBean
@SessionScoped
public class PostManagedBean implements Serializable{
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
		allPosts = postFactory.getAll();		
	}
	
	public void createPost(){
		int userId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("userId"));
		
		Post newPost = new Post();
		newPost.setText(newPostText);
		newPost.setUser(userFactory.get(userId));
		newPost.setTime(System.nanoTime());
		newPost.setCommentList(new ArrayList<>());
		newPost.setPointList(new ArrayList<>());
		allPosts.add(newPost);
		postFactory.create(newPost);
	}
	
	public void upvotePost(){
		int postId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("postId"));
		Post post = postFactory.get(postId);
		
		Point point = new Point();
		point.setVote(true);
		point.setUser(post.getUser());
		point.setTime(System.nanoTime());
		
		post.addPoint(point);
		
		pointFactory.create(point);
		postFactory.update(post);
		
		allPosts = postFactory.getAll();
		
	}
	
	public void downvotePost(){
		int postId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("postId"));
		Post post = postFactory.get(postId);
		
		Point point = new Point();
		point.setVote(false);
		point.setUser(post.getUser());
		point.setTime(System.nanoTime());
		
		post.addPoint(point);
		
		pointFactory.create(point);
		postFactory.update(post);
		allPosts = postFactory.getAll();
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
