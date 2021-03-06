package dhbw.lan.lantalk.persistence.objects;

import javax.persistence.*;

/**
 * Represents a point from the database
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
	@ManyToOne
	@JoinColumn
	private User user;

	/**
	 * Shows if it is an upvote (true) or downvote (false)
	 */
	@Column
	private boolean upvote;

	/**
	 * Represents the creation time of the vote
	 */
	@Column
	private long time;

	/**
	 * Reference to the text-component from the vote
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn
	private TextComponent textComponent;

	/**
	 *
	 * @param id
	 *            set the {@link Point#id} of the point
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the {@link Point#id} of the point
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
		Point point = (Point) obj;
		if (id != point.id) {
			return false;
		}
		return true;
	}
}
