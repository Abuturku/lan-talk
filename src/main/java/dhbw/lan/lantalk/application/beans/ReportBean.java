package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import dhbw.lan.lantalk.persistence.factory.CommentFactory;
import dhbw.lan.lantalk.persistence.factory.PostFactory;
import dhbw.lan.lantalk.persistence.factory.ReportFactory;
import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Comment;
import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.Report;
import dhbw.lan.lantalk.persistence.objects.TextComponent;
import dhbw.lan.lantalk.persistence.objects.TextType;
import dhbw.lan.lantalk.persistence.objects.User;

@Named
@RequestScoped
public class ReportBean implements Serializable{
	private static final long serialVersionUID = -6770456571006529147L;

	@Inject
	private ReportFactory reportFactory;

	@Inject
	private PostFactory postFactory;

	@Inject
	private CommentFactory commentFactory;

	@Inject
	private UserFactory userFactory;

	@Transactional
	public void reportComment(Comment comment, User reporter) {
		reporter = userFactory.get(reporter);
		comment = commentFactory.get(comment);

		// TODO add report reason
		Report report = new Report();

		report.setTextComponent(comment);
		report.setReporter(reporter);
		report.setTime(System.currentTimeMillis());
		reportFactory.create(report);
	}

	@Transactional
	public void reportPost(Post post, User reporter) {
		reporter = userFactory.get(reporter);
		post = postFactory.get(post);

		// TODO add report reason
		Report report = new Report();
		report.setTextComponent(post);
		report.setReporter(reporter);
		report.setTime(System.currentTimeMillis());
		reportFactory.create(report);
	}
	
	public List<Report> getAllReportsSubmittedByUser(User user){
		user = userFactory.get(user);
		List<Report> reports = reportFactory.getAll();
		
		for (int i = 0; i < reports.size(); i++) {
			if (!reports.get(i).getReporter().equals(user)) {
				reports.remove(reports.get(i));
			}
		}
		
		return reports;
	}
	
	public List<Report> getAllReportsOfTextComponent(TextComponent component){
		List<Report> reports = reportFactory.getAll(); 
		
		if (component.getTextType() == TextType.Post) {
			component = postFactory.get((Post)component);
		}else{
			component = commentFactory.get((Comment)component);
		}
		
		for (int i = 0; i < reports.size(); i++) {
			if (reports.get(i).getTextComponent().equals(component)) {
				reports.remove(reports.get(i));
			}
		}
		
		return reports;
	}
	
	
}
