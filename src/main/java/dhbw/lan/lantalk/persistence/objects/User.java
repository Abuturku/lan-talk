package dhbw.lan.lantalk.persistence.objects;

import java.util.List;

import javax.persistence.*;

/**
 * Represents a user from the database
 * 
 * @author Niklas Nikisch
 *
 */
@Entity
@Table(name = "Users")
public class User implements IPrimKey {

	/**
	 * Represents the primary key in the database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	/**
	 * The name of the user
	 */
	@Column(nullable = false, unique = true, length = 32)
	private String name;

	/**
	 * The password of the user
	 */
	@Column
	private String password;

	/**
	 * The role of the user
	 */
	@Column
	private String role;

	/**
	 * Represents the registrationtime
	 */
	@Column
	private long regTime;

	/**
	 * The points a user has made with his articles and comments
	 */
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@OrderColumn(name = "orderIndexPointList")
	private List<Point> points;

	/**
	 * All the articles the user has written.
	 */
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, targetEntity = TextComponent.class)
	private List<Post> postList;

	/**
	 * Represents a list of comments the user has written
	 */
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, targetEntity = TextComponent.class)
	private List<Comment> commentList;

	/**
	 * 
	 * @param postList
	 *            Sets the {@link User#postList} of the User
	 */
	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}

	/**
	 * 
	 * @param post
	 *            add to {@link User#postList} of the User
	 */
	public void addPost(Post post) {
		this.postList.add(post);
	}

	/**
	 * 
	 * @return {@link User#postList} of the user
	 */
	public List<Post> getPostList() {
		return this.postList;
	}

	/**
	 * 
	 * @param commentList
	 *            Sets the comments {@link User#commentList} the user wrote
	 */
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	/**
	 * 
	 * @param comment
	 *            Add the comment to {@link User#commentList} of the user
	 */
	public void addComment(Comment comment) {
		this.commentList.add(comment);
	}

	/**
	 * 
	 * @return All comments {@link User#commentList} of the User
	 */
	public List<Comment> getCommentList() {
		return this.commentList;
	}

	/**
	 *
	 * @param id
	 *            Sets the {@link User#id} of the user
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return The {@link User#id} of the user
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 *
	 * @param name
	 *            Sets the {@link User#name} of the user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The {@link User#password} of the user
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 *
	 * @param name
	 *            Sets the {@link User#password} of the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return The {@link User#name} of the user
	 */
	public String getName() {
		return this.name;
	}

	/**
	 *
	 * @param points
	 *            Sets the {@link User#points} of the user
	 */
	public void setPoints(List<Point> points) {
		this.points = points;
	}

	/**
	 *
	 * @param points
	 *            Add to the {@link User#points} of the user
	 */
	public void addPoints(Point point) {
		this.points.add(point);
	}

	/**
	 * @return The {@link User#points} of the user
	 */
	public List<Point> getPoints() {
		return this.points;
	}

	/**
	 * 
	 * @param rank
	 *            Set the {@link User#role} of the user
	 */
	public void setRole(String rank) {
		this.role = rank;
	}

	/**
	 * @return The {@link User#role} of the user
	 */
	public String getRole() {
		return this.role;
	}

	/**
	 *
	 * @param time
	 *            Sets the {@link User#regTime} of the user
	 */
	public void setRegTime(long time) {
		this.regTime = time;
	}

	/**
	 * @return The {@link User#regTime} of the user
	 */
	public long getRegTime() {
		return this.regTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Post)) {
			return false;
		}
		User user = (User) obj;
		if (id != user.id) {
			return false;
		}
		return true;
	}
}
