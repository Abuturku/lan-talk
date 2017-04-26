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
@Table(name = "Article")
public class Post implements IPrimKey {

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	/**
	 * Represents the user who create the article
	 */
	@ManyToOne // (targetEntity = User.class)
	@JoinColumn(name = "USER_ID")
	private User user;
	/**
	 * Represents the create time of the article
	 */
	@Column(name = "TIME")
	private long time;
	/**
	 * Represents a list of comments from the article
	 */
	@OneToMany // (targetEntity = Comment.class)
	@JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ID")
	private List<Comment> commentList;
	/**
	 * The text of the article
	 */
	@Column(name = "TEXT")
	private String text;
	/**
	 * The points a article have get
	 */
	@Column(name = "POINTS")
	private int points;

	/**
	 * 
	 * @param commentList
	 *            set the commends in {@link Post#commentList} of the article
	 */
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	/**
	 * 
	 * @return the all commands {@link Post#commentList} of the article
	 */
	public List<Comment> getCommentList() {
		return this.commentList;
	}

	/**
	 *
	 * @param id
	 *            set the {@link Post#id} of the article
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the {@link Post#id} of the article
	 */
	@Override
	public int getID() {
		return this.id;
	}

	/**
	 *
	 * @param user
	 *            set the {@link Post#user} who created the article
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the {@link Post#user} who created the article
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 *
	 * @param text
	 *            set the {@link Post#text} of the article
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the {@link Post#text} of the article
	 */
	public String getText() {
		return this.text;
	}

	/**
	 *
	 * @param points
	 *            set the {@link Post#points} of the article
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the {@link Post#points} of the article
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 *
	 * @param time
	 *            set the {@link Post#time} of the article
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the {@link Post#time} of the article
	 */
	public long getTime() {
		return this.time;
	}
}
