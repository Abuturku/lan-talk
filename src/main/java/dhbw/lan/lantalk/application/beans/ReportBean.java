package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.ocpsoft.prettytime.PrettyTime;

import dhbw.lan.lantalk.persistence.factory.CommentFactory;
import dhbw.lan.lantalk.persistence.factory.PostFactory;
import dhbw.lan.lantalk.persistence.factory.ReportFactory;
import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Comment;
import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.Report;
import dhbw.lan.lantalk.persistence.objects.ReportType;
import dhbw.lan.lantalk.persistence.objects.Role;
import dhbw.lan.lantalk.persistence.objects.TextComponent;
import dhbw.lan.lantalk.persistence.objects.TextType;
import dhbw.lan.lantalk.persistence.objects.User;

@Named
@SessionScoped
public class ReportBean implements Serializable {
	private static final long serialVersionUID = -6770456571006529147L;

	@Inject
	private CommentManagerBean commentManagerBean;
	
	@Inject
	private PostManagerBean postManagerBean;
	
	@Inject
	private ReportFactory reportFactory;

	@Inject
	private PostFactory postFactory;

	@Inject
	private CommentFactory commentFactory;

	@Inject
	private UserFactory userFactory;
	
	private TextComponent selectedTextComponent;

	/**
	 * Add a report to a Comment.
	 * 
	 * @param comment
	 *            The {@link Comment} of the comment
	 * 
	 * @param reporter
	 *            The {@link User}, that submitted the report
	 */
	@Transactional
	public void reportComment(Comment comment, User reporter, String reason) {
		reporter = userFactory.get(reporter);
		comment = commentFactory.get(comment);

		// TODO add report reason
		Report report = new Report();
		report.setReportType(ReportType.fromString(reason));
		report.setTextComponent(comment);
		report.setReporter(reporter);
		report.setTime(System.currentTimeMillis());
		reportFactory.create(report);
	}

	/**
	 * Add a report to a Post.
	 * 
	 * @param post
	 *            The {@link Post} of the comment
	 * 
	 * @param reporter
	 *            The {@link User}, that submitted the report
	 */
	@Transactional
	public void reportPost(Post post, User reporter, String reason) {
		System.out.println("Reported post: " + post.getText() + " by user: " + reporter.getName() + " for reason: " + reason);
		reporter = userFactory.get(reporter);
		post = postFactory.get(post);

		Report report = new Report();
		report.setTextComponent(post);
		report.setReporter(reporter);
		report.setReportType(ReportType.fromString(reason));
		report.setTime(System.currentTimeMillis());
		reportFactory.create(report);
	}

	/**
	 * Deletes a report.
	 * 
	 * @param report
	 *            The {@link Report} that will be deleted
	 */
	@Transactional
	public void removeReport(Report report) {
		reportFactory.delete(report);
	}

	
	/**
	 * If a report is justified, this function will be called and the {@link Report} + {@link TextComponent} and everything attached to it will be deleted.
	 * 
	 * @param report The report that
	 * @param loggedInUser
	 */
	@Transactional
	@RolesAllowed(value = {Role.Administrator, Role.Moderator})
	public void acceptReport(Report report, User loggedInUser) {
		report = reportFactory.get(report);
		loggedInUser = userFactory.get(loggedInUser);
		
		if (report.getTextComponent().getTextType() == TextType.Comment) {
			commentManagerBean.deleteComment((Comment)report.getTextComponent(), loggedInUser);
		}else{
			postManagerBean.deletePost((Post) report.getTextComponent(), loggedInUser);
		}
		
//		reportFactory.delete(report);
	}

	
	/**
	 * 
	 * @param post  
	 * @return Human readable String of the time difference between now and the creation time of the Report, e.g. "3 hours ago"
	 */
	public String getTimeDiff(TextComponent textComponent) {
		if (textComponent.getTextType() == TextType.Post) {
			postFactory.get((Post)textComponent);
		}else{
			commentFactory.get((Comment)textComponent);
		}
		PrettyTime prettyTime = new PrettyTime(
				FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
		return prettyTime.format(new Date(textComponent.getTime()));
	}

	/**
	 * 
	 * 
	 * @return All reports that are stored in the database
	 */
	public List<Report> getAllReports() {
		return reportFactory.getAll();
	}

	
	/**
	 * 
	 * 
	 * @param user {@link User}
	 * @return All reports that have been submitted by {@link User}
	 */
	public List<Report> getAllReportsSubmittedByUser(User user) {
		user = userFactory.get(user);
		List<Report> reports = reportFactory.getAll();

		for (int i = 0; i < reports.size(); i++) {
			if (!reports.get(i).getReporter().equals(user)) {
				reports.remove(reports.get(i));
				i--;
			}
		}

		return reports;
	}

	/**
	 * 
	 * @param component The {@link TextComponent}
	 * @return All reports that are linked to the {@link TextComponent}
	 */
	public List<Report> getAllReportsOfTextComponent(TextComponent component) {
		List<Report> reports = reportFactory.getAll();

		if (component.getTextType() == TextType.Post) {
			component = postFactory.get((Post) component);
		} else {
			component = commentFactory.get((Comment) component);
		}

		for (int i = 0; i < reports.size(); i++) {
			if (!reports.get(i).getTextComponent().equals(component)) {
				reports.remove(reports.get(i));
				i--;
			}
		}

		return reports;
	}
	
	public List<TextComponent> getAllDistinctTextComponents(){
		List<Report> reports = reportFactory.getAll();
		List<TextComponent> textComponents = new ArrayList<>();
		
		for (int i = 0; i < reports.size(); i++) {
			TextComponent componentToAdd = reports.get(i).getTextComponent();
			
			if (!textComponents.contains(componentToAdd)) {
				textComponents.add(componentToAdd);
			}
		}
		
		return textComponents;
	}
	
	public TextComponent getSelectedTextComponent() {
		return selectedTextComponent;
	}

	public void setSelectedTextComponent(TextComponent selectedTextComponent) {
		this.selectedTextComponent = selectedTextComponent;
	}

}
