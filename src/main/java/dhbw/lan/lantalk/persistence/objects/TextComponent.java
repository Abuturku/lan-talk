package dhbw.lan.lantalk.persistence.objects;

import java.util.List;

import javax.persistence.*;

/**
 * Represents a comment from the database
 * 
 * @author Niklas Nikisch
 *
 */
@MappedSuperclass
public class TextComponent implements IPrimKey {

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	/**
	 * Represents the user who created the post
	 */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	/**
	 * Represents the creation time of the post
	 */
	@Column(name = "TIME")
	private long time;

	/**
	 * The points of the textcomponent
	 */
	@Column(name = "TEXT")
	private String text;

	@OneToMany  
	@JoinColumn(name = "POINT_ID", referencedColumnName = "ID")
	private List<Point> pointList;

	/**
	 * 
	 * @param commentList
	 *            Sets the comments in {@link TextComponent#pointList} of the
	 *            text-component
	 */
	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
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
	public void addPoint(Point points) {
		this.pointList.add(points);
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
}
