package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Comment;
import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.Rank;
import dhbw.lan.lantalk.persistence.objects.User;

@Named
@SessionScoped
public class UserManagerBean implements Serializable {
	private static final long serialVersionUID = 3413209755795108052L;

	private int userId;
	private String userName;

	@Inject
	private UserFactory userFactory;

	@PostConstruct
	public void init() {
		User user = new User();
		user.setName("BreakableBratwurst");
		user.setPoints(new ArrayList<>());
		user.setRank(Rank.User);
		user.setRegTime(System.nanoTime());
		user.setPostList(new ArrayList<Post>());
		user.setCommentList(new ArrayList<Comment>());
		userFactory.create(user);
		this.userId = user.getId();
		this.userName = user.getName();
	}

	public void createUser() {
		User user = new User();
		user.setName(userName);
		user.setPoints(new ArrayList<>());
		user.setRank(Rank.User);
		user.setRegTime(System.nanoTime());
		user.setPostList(new ArrayList<Post>());
		user.setCommentList(new ArrayList<Comment>());
		userFactory.create(user);
	}

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
