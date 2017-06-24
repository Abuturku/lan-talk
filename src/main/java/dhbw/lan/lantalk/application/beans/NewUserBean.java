package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Comment;
import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.Role;
import dhbw.lan.lantalk.persistence.objects.User;

@Named
@RequestScoped
public class NewUserBean implements Serializable{

	private static final long serialVersionUID = 353280638872143294L;
	
	@Inject
	private UserFactory userFactory;
	
	//The attributes used by the inputs in the GUI
	private String userName;
	private String password1;
	private String password2;
	
	/**
	 * Registers a new {@link User}. Checks if the given passwords match. If they do not or the username already exists, a FacesMessage will be drawn to users screen.
	 * A username is unique (defined in the {@link User} class).
	 * 
	 * @return
	 * 				The navigation rule as String
	 */
	public String register(){
		
		if (password1.equals(password2)) {
			try {
				User user = new User();
				user.setName(userName);
				user.setPoints(new ArrayList<>());
				user.setRole(Role.User);
				user.setRegTime(System.currentTimeMillis());
				user.setPostList(new ArrayList<Post>());
				user.setCommentList(new ArrayList<Comment>());
				userFactory.changePassword(user, password1);
				userFactory.create(user);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Username already assigned. Please choose another Username.", ""));
			}
			
			return "login";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Passwords do not match.", ""));
			return null;
		}
	}
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	

	
}
