package dhbw.lan.lantalk.persistence.objects;

import java.util.List;

import javax.persistence.*;

/**
 * Represents a comment from the database
 * 
 * @author Niklas Nikisch
 *
 */
@Entity(name = "TextComponent")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TextComponent implements IPrimKey {

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	/**
	 * Represents the user who created the post
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private User user;

	/**
	 * Represents the creation time of the post
	 */
	@Column
	private long time;

	/**
	 * The points of the textcomponent
	 */
	@Column
	private String text;

	@OneToMany(mappedBy = "textComponent", fetch = FetchType.EAGER)
	@OrderColumn(name = "orderIndex")
	private List<Point> pointList;

	private int votes;

	/**
	 * 
	 * @param commentList
	 *            Sets the comments in {@link TextComponent#pointList} of the
	 *            text-component
	 */
	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
		updateVotes();
	}

	/**
	 * 
	 * @return the all commands {@link TextComponent#pointList} of the
	 *         text-component
	 */
	public List<Point> getPointList() {
		return this.pointList;
	}

	/**
	 *
	 * @param id
	 *            set the {@link TextComponent#id} of the post
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the {@link TextComponent#id} of the post
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 *
	 * @param user
	 *            set the {@link TextComponent#user} who created the post
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the {@link TextComponent#user} who created the post
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 *
	 * @param text
	 *            set the {@link TextComponent#text} of the post
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the {@link TextComponent#text} of the post
	 */
	public String getText() {
		return this.text;
	}

	/**
	 *
	 * @param points
	 *            add a Point to {@link TextComponent#pointList} of the
	 *            text-component
	 */
	public void addPoint(Point point) {
		if (point.isUpVote()) {
			this.votes++;
		} else {
			this.votes--;
		}

		this.pointList.add(point);
	}

	/**
	 *
	 * @param time
	 *            set the {@link TextComponent#time} of the post
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the {@link TextComponent#time} of the post
	 */
	public long getTime() {
		return this.time;
	}

	public int getVotes() {
		return votes;
	}

	private void updateVotes() {
		this.votes = 0;

		for (int i = 0; i < pointList.size(); i++) {
			if (pointList.get(i).isUpVote()) {
				votes++;
			} else {
				votes--;
			}
		}
	}

}
