package dhbw.lan.lantalk.jpa;

import javax.persistence.*;

/**
 * Represents a comment from the database
 * 
 * @author Niklas Nikisch
 *
 */
@Entity
@Table(name = "comment")
public class Comment {

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	/**
	 * Represents the article of the comment
	 */
	@ManyToOne(targetEntity = Article.class)
	private Article article;
	/**
	 * The text of the comment
	 */
	@Column(name = "text")
	private String text;
	/**
	 * The points a comment have get
	 */
	@Column(name = "points")
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
	public int getID() {
		return this.id;
	}

	/**
	 *
	 * @param user
	 *            set the {@link Comment#article} of the comment
	 */
	public void setArticle(Article article) {
		this.article = article;
	}

	/**
	 * @return the {@link Comment#article} of the comment
	 */
	public Article getArticle() {
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
	public String getText(String text) {
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
}
