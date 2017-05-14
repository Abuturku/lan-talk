package dhbw.lan.lantalk.application.managedbeans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Comment;
import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.Rank;
import dhbw.lan.lantalk.persistence.objects.User;

@ManagedBean
@SessionScoped
public class UserManagedBean implements Serializable {
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
