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
	@Column(name = "ID")
	private int id;

	/**
	 * The name of the user
	 */
	@Column(name = "NAME")
	private String name;

	/**
	 * The rank of the user
	 */
	@Column(name = "RANK")
	@Enumerated(EnumType.STRING)
	private Rank rank;

	/**
	 * Represents the registrationtime
	 */
	@Column(name = "REGTIME")
	private long regTime;

	/**
	 * The points a user has made with his articles and comments
	 */
	@Column(name = "POINTS")
	private int points;

	/**
	 * All the articles the user has written.
	 */
	@OneToMany
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private List<Post> postList;

	/**
	 * Represents a list of comments the user has written
	 */
	@OneToMany
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
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
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return The {@link User#points} of the user
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 *
	 * @param rank
	 *            Set the {@link User#rank} of the user
	 */
	public void setRank(Rank rank) {
		this.rank = rank;
	}

	/**
	 * @return The {@link User#rank} of the user
	 */
	public Rank getRank() {
		return this.rank;
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
}
