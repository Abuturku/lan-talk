package dhbw.lan.lantalk.persistence.objects;

import javax.persistence.*;

/**
 * Represents a comment from the database
 * 
 * @author Niklas Nikisch
 *
 */
@Entity
@Table(name = "Points")
public class Point implements IPrimKey {

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	/**
	 * Represents the user who created the point
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private User user;

	/**
	 * Represents the user who created the point
	 */
	@Column
	private boolean upvote;

	/**
	 * Represents the creation time of the post
	 */
	@Column
	private long time;

	/**
	 * Reference to the text from the vote
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn
	private TextComponent textComponent;

	/**
	 *
	 * @param id
	 *            set the {@link Point#id} of the post
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the {@link Point#id} of the post
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 *
	 * @param user
	 *            set the {@link Point#user} who created the post
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the {@link Point#user} who created the post
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 *
	 * @param time
	 *            set the {@link Point#time} of the post
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the {@link Point#time} of the point
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 *
	 * @param upVote
	 *            set the {@link Point#upvote} of the point
	 */
	public void setVote(boolean upVote) {
		this.upvote = upVote;
	}

	/**
	 * @return if it is a {@link Point#upvote} of the post
	 */
	public boolean isUpVote() {
		return this.upvote;
	}

	/**
	 *
	 * @param textComponent
	 *            set the {@link Point#textComponent} of the post
	 */
	public void setTextComponent(TextComponent textComponent) {
		this.textComponent = textComponent;
	}

	/**
	 * @return the {@link Point#textComponent} of the post
	 */
	public TextComponent getTextComponent() {
		return this.textComponent;
	}
}
