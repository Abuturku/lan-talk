package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.User;

@Named
@RequestScoped
public class UserManagerBean implements Serializable {
	private static final long serialVersionUID = 3413209755795108052L;

	private User loggedInUser;

	@Inject
	private UserFactory userFactory;
	
	@PostConstruct
	public void init() {

		this.loggedInUser = getUser();
	}

	
	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	private Optional<Principal> getPrincipal() {
		return Optional.ofNullable(getRequest().getUserPrincipal());
	}

	public String getLoginName() {
		return getPrincipal().map(p -> p.getName()).orElse("");
	}

	public boolean isAuthenticated() {
		return getPrincipal().isPresent();
	}

	public boolean isUserInRole(String role) {
		return getRequest().isUserInRole(role);
	}
	
	
	public User getUser(){
		final Optional<Principal> principal = getPrincipal();
		if (principal.isPresent())
		{
			if ((loggedInUser == null) || !principal.get().getName().equals(loggedInUser.getName()))
			{
				
				loggedInUser = userFactory.getByName(principal.get().getName());
				return loggedInUser;
			}
		}
		return null;
	}
	
	public String logout() {
		final HttpServletRequest request = getRequest();

		loggedInUser = null;

		try {
			request.logout();
			request.getSession().invalidate();
		} catch (final ServletException e) {
			e.printStackTrace();
		}

		return "login";
	}
	
	public User getLoggedInUser() {
		return loggedInUser;
	}

	public String getUserName() {
		return userFactory.get(loggedInUser).getName();
	}
	
	public String getUserRank(){
		//TODO calculate user rank
		//Beginner, Advanced, Professional, Godlike
		return "Beginner";
	}
	
	public String getUserRole(){
		return userFactory.get(loggedInUser).getRank().toString();
	}
	
	public String getUserRegTime(){
		Date date = new Date(userFactory.get(loggedInUser).getRegTime());
		String pattern = "dd.MM.yyyy";
	    DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public int getAmountOfPosts(){
		return userFactory.get(loggedInUser).getPostList().size();
	}
	
	public int getAmountOfComments(){
		return userFactory.get(loggedInUser).getCommentList().size();
	}
	
	public int getAmountOfVotes(){
		User user = userFactory.get(loggedInUser);
		return user.getPoints().size();
	}
}
