package dhbw.lan.lantalk.persistence.objects;

import javax.persistence.*;

/**
 * Represents a comment from the database
 * 
 * @author Niklas Nikisch
 *
 */
@Entity
@Table(name = "Comments")
public class Comment implements IPrimKey {

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	/**
	 * Represents the user who create the comment
	 */
	@ManyToOne // (targetEntity = User.class)
	@JoinColumn(name = "USER_ID")
	private User user;
	/**
	 * Represents the article of the comment
	 */
	@ManyToOne // (targetEntity = Article.class)
	@JoinColumn(name = "ARTICLE_ID")
	private Post article;
	/**
	 * The text of the comment
	 */
	@Column(name = "TEXT")
	private String text;
	/**
	 * Represents the create time of the comment
	 */
	@Column(name = "TIME")
	private long time;
	/**
	 * The points a comment have get
	 */
	@Column(name = "POINTS")
	private int points;

	/**
	 *
	 * @param id
	 *            set the {@link Comment#id} of the comment
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the {@link Comment#id} of the comment
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 *
	 * @param user
	 *            set the {@link Comment#user} who created the comment
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the {@link Comment#user} who created the comment
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 *
	 * @param user
	 *            set the {@link Comment#article} of the comment
	 */
	public void setArticle(Post article) {
		this.article = article;
	}

	/**
	 * @return the {@link Comment#article} of the comment
	 */
	public Post getArticle() {
		return this.article;
	}

	/**
	 *
	 * @param text
	 *            set the {@link Comment#text} of the comment
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the {@link Comment#text} of the comment
	 */
	public String getText() {
		return this.text;
	}

	/**
	 *
	 * @param points
	 *            set the {@link Comment#points} of the comment
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the {@link Comment#points} of the comment
	 */
	public int getPoints() {
		return this.points;
	}

	/**
	 *
	 * @param time
	 *            set the {@link Comment#time} of the comment
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the {@link Comment#time} of the comment
	 */
	public long getTime() {
		return this.time;
	}
}
