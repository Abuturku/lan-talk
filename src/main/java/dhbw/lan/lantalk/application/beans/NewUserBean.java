package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.enterprise.context.RequestScoped;
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
	
	
	private String userName;
	private String password1;
	private String password2;
	
	public String register(){
		
		if (password1.equals(password2)) {
			User user = new User();
			user.setName(userName);
			user.setPoints(new ArrayList<>());
			user.setRole(Role.User);
			user.setRegTime(System.currentTimeMillis());
			user.setPostList(new ArrayList<Post>());
			user.setCommentList(new ArrayList<Comment>());
			userFactory.changePassword(user, password1);
			userFactory.create(user);
			return "login";
		}else{
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
