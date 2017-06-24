package dhbw.lan.lantalk.persistence.objects;

import java.util.List;

import javax.persistence.*;

/**
 * Represents a text from a comment or post
 * 
 * @author Niklas Nikisch
 *
 */
@Entity(name = "TextComponent")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TextComponent implements IPrimKey {

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	/**
	 * Represents the user who created the text
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private User user;

	/**
	 * Represents the creation time of the text
	 */
	@Column
	private long time;

	/**
	 * The text of the textcomponent
	 */
	@Column
	private String text;

	/**
	 * The type of the textcomponent (Post or Comment)
	 */
	@Enumerated(EnumType.STRING)
	private TextType textType;

	/**
	 * The Points for the Text
	 */
	@OneToMany(mappedBy = "textComponent", fetch = FetchType.EAGER)
	@OrderColumn(name = "orderIndex")
	private List<Point> pointList;

	/**
	 * All Points added together
	 */
	private int votes;

	/**
	 * 
	 * @param commentList
	 *            Sets the comments in {@link TextComponent#pointList} of the
	 *            text-component
	 */
	/**
	 * 
	 * @param pointList
	 *            Sets the points of the text in {@link TextComponent#pointList}
	 */
	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
		updateVotes();
	}

	/**
	 * 
	 * @return all Points {@link TextComponent#pointList} of the text-component
	 */
	public List<Point> getPointList() {
		return this.pointList;
	}

	/**
	 *
	 * @param id
	 *            set the {@link TextComponent#id} of the text-component
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the {@link TextComponent#id} of the text-component
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 *
	 * @param user
	 *            set the {@link TextComponent#user} who created the
	 *            text-component
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
	 *            set the {@link TextComponent#text} of the text-component
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the {@link TextComponent#text} of the text-component
	 */
	public String getText() {
		return this.text;
	}

	/**
	 *
	 * @param text
	 *            set the {@link TextComponent#textType} of the text-component
	 */
	public void setTextType(TextType textType) {
		this.textType = textType;
	}

	/**
	 * @return the {@link TextComponent#textType} of the text-component
	 */
	public TextType getTextType() {
		return this.textType;
	}

	/**
	 *
	 * @param points
	 *            add a Point to {@link TextComponent#pointList} of the
	 *            text-component
	 */
	public void addPoint(Point point) {
		int amount = 2;

		if (!pointList.contains(point)) {
			amount = 1;
			this.pointList.add(point);
		}

		if (point.isUpVote()) {
			this.votes += amount;
		} else {
			this.votes -= amount;
		}

	}

	/**
	 *
	 * @param time
	 *            set the {@link TextComponent#time} of the text-component
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the {@link TextComponent#time} of the text-component
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 * 
	 * @return {@link TextComponent#votes}
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Update {@link TextComponent#votes} to all set Points in {@link TextComponent#pointList} 
	 */
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

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof TextComponent)) {
			return false;
		}
		TextComponent textComponent = (TextComponent) obj;
		if (id != textComponent.id) {
			return false;
		}
		return true;
	}
}
