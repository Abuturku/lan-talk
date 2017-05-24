package dhbw.lan.lantalk.application.beans;

import java.security.Principal;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.User;

@Named
@RequestScoped
public class SecurityBean {
	private User loggedInUser;
	
	@Inject
	private UserFactory userFactory;

	private HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
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
		System.out.println("Attempting to login");
		if (principal.isPresent())
		{
			System.out.println("principal is present");
			if ((loggedInUser == null) || !principal.get().getName().equals(loggedInUser.getName()))
			{
				
				loggedInUser = userFactory.getByName(principal.get().getName());
				System.out.println(loggedInUser.getName());
				return loggedInUser;
			}
		}
		return null;
	}

	public String login() {
		loggedInUser = null;		
		return "/pages/lan-talk.xhtml?faces-redirect=true";
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

}
