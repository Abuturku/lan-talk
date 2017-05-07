package dhbw.lan.lantalk.persistence.objects;

import java.util.List;

import javax.persistence.*;

/**
 * Represents a comment from the database
 * 
 * @author Niklas Nikisch
 *
 */
@Entity
@Table(name = "Posts")
public class Post implements IPrimKey {

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	
	
	/**
	 * Represents the user who created the post
	 */
	@ManyToOne // (targetEntity = User.class)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	
	/**
	 * Represents the creation time of the post
	 */
	@Column(name = "TIME")
	private long time;
	
	
	/**
	 * Represents a list of comments of the post
	 */
	@OneToMany // (targetEntity = Comment.class)
	@JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ID")
	private List<Comment> commentList;
	
	
	/**
	 * The text of the post
	 */
	@Column(name = "TEXT")
	private String text;
	
	
	/**
	 * The points a post has gotten
	 */
	@Column(name = "POINTS")
	private int points;
	

	/**
	 * 
	 * @param commentList
	 *            Sets the comments in {@link Post#commentList} of the post
	 */
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	

	/**
	 * 
	 * @return the all commands {@link Post#commentList} of the post
	 */
	public List<Comment> getCommentList() {
		return this.commentList;
	}
	

	/**
	 *
	 * @param id
	 *            set the {@link Post#id} of the post
	 */
	public void setID(int id) {
		this.id = id;
	}
	

	/**
	 * @return the {@link Post#id} of the post
	 */
	@Override
	public int getId() {
		return this.id;
	}
	

	/**
	 *
	 * @param user
	 *            set the {@link Post#user} who created the post
	 */
	public void setUser(User user) {
		this.user = user;
	}
	

	/**
	 * @return the {@link Post#user} who created the post
	 */
	public User getUser() {
		return this.user;
	}
	

	/**
	 *
	 * @param text
	 *            set the {@link Post#text} of the post
	 */
	public void setText(String text) {
		this.text = text;
	}

	
	/**
	 * @return the {@link Post#text} of the post
	 */
	public String getText() {
		return this.text;
	}
	

	/**
	 *
	 * @param points
	 *            set the {@link Post#points} of the post
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	

	/**
	 * @return the {@link Post#points} of the post
	 */
	public int getPoints() {
		return this.points;
	}
	

	/**
	 *
	 * @param time
	 *            set the {@link Post#time} of the post
	 */
	public void setTime(long time) {
		this.time = time;
	}
	

	/**
	 * @return the {@link Post#time} of the post
	 */
	public long getTime() {
		return this.time;
	}
}
