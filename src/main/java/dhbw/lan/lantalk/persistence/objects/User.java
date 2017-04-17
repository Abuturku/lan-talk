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
@Table(name = "User")
public class User implements IPrimKey{

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	/**
	 * is the name of the user
	 */
	@Column(name = "NAME")
	private String name;
	/**
	 * The points a user have made with his articles and comments
	 */
	@Column(name = "POINTS")
	private int points;
	/**
	 * All the articles the user have written.
	 */
	@OneToMany//(targetEntity = Article.class)
	@JoinColumn(name="USER_ID", referencedColumnName="ID")
	private List<Article> articleList;
	/**
	 * Represents a list of comments the user have written
	 */
	@OneToMany//(targetEntity = Comment.class)
	@JoinColumn(name="USER_ID", referencedColumnName="ID")
	private List<Comment> commentList;

	/**
	 * 
	 * @param articleList
	 *            set the {@link User#articleList} of the User
	 */
	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	/**
	 * 
	 * @return the {@link User#articleList} of the user
	 */
	public List<Article> getArticleList() {
		return this.articleList;
	}

	/**
	 * 
	 * @param commentList
	 *            set the commends {@link User#commentList} the user write
	 */
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	/**
	 * 
	 * @return all commands {@link User#commentList} of the User
	 */
	public List<Comment> getCommentList() {
		return this.commentList;
	}

	/**
	 *
	 * @param id
	 *            set the {@link User#id} of the user
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the {@link User#id} of the user
	 */
	@Override
	public int getID() {
		return this.id;
	}

	/**
	 *
	 * @param name
	 *            set the {@link User#name} of the user
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the {@link User#name} of the user
	 */
	public String getName() {
		return this.name;
	}

	/**
	 *
	 * @param points
	 *            set the {@link User#points} of the user
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the {@link User#points} of the user
	 */
	public int getPoints() {
		return this.points;
	}
}
