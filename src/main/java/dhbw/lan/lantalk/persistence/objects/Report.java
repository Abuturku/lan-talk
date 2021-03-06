package dhbw.lan.lantalk.persistence.objects;

import javax.persistence.*;

/**
 * Represents a report to a text-component
 * 
 * @author Niklas Nikisch
 *
 */
@Entity
@Table(name = "Reports")
public class Report implements IPrimKey {

	/**
	 * Represents the primary key in database
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	/**
	 * Represents the user who reported the text-component
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private User reporter;

	/**
	 * Represents the creation time of the report
	 */
	@Column
	private long time;

	/**
	 * Reference to the text-component from the report
	 */
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn
	private TextComponent textComponent;

	/**
	 * The type of the report
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private ReportType reportType;

	/**
	 *
	 * @param id
	 *            set the {@link Report#id} of the post
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return the {@link Report#id} of the post
	 */
	@Override
	public int getId() {
		return this.id;
	}

	/**
	 *
	 * @param user
	 *            set the {@link Report#reporter} who reported the text-component
	 */
	public void setReporter(User reporter) {
		this.reporter = reporter;
	}

	/**
	 * @return the {@link Report#reporter} who reported the text-component
	 */
	public User getReporter() {
		return this.reporter;
	}

	/**
	 *
	 * @param time
	 *            set the {@link Report#time} of the report
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the {@link Report#time} of the report
	 */
	public long getTime() {
		return this.time;
	}

	/**
	 *
	 * @param textComponent
	 *            set the {@link Report#textComponent} of the report
	 */
	public void setTextComponent(TextComponent textComponent) {
		this.textComponent = textComponent;
	}

	/**
	 * @return the {@link Report#textComponent} of the report
	 */
	public TextComponent getTextComponent() {
		return this.textComponent;
	}

	/**
	 *
	 * @param reportType
	 *            set the {@link Report#reportType} of the report
	 */
	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return the {@link Report#reportType} of the report
	 */
	public ReportType getReportType() {
		return this.reportType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Report)) {
			return false;
		}
		Report report = (Report) obj;
		if (id != report.id) {
			return false;
		}
		return true;
	}
}
