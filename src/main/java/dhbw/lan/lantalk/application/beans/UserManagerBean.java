package dhbw.lan.lantalk.application.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.factory.PointFactory;
import dhbw.lan.lantalk.persistence.factory.UserFactory;
import dhbw.lan.lantalk.persistence.objects.Comment;
import dhbw.lan.lantalk.persistence.objects.Point;
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
	
	@Inject
	private PointFactory pointFactory;

	@PostConstruct
	public void init() {
		User user = new User();
		user.setName("BreakableBratwurst");
		user.setPoints(new ArrayList<>());
		user.setRank(Rank.User);
		user.setRegTime(System.currentTimeMillis());
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
		user.setRegTime(System.currentTimeMillis());
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
	
	public String getUserRank(){
		//TODO calculate user rank
		//Beginner, Advanced, Professional, Godlike
		return "Beginner";
	}
	
	public String getUserRole(){
		return userFactory.get(userId).getRank().toString();
	}
	
	public String getUserRegTime(){
		Date date = new Date(userFactory.get(userId).getRegTime());
		String pattern = "dd.MM.yyyy";
	    DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public int getAmountOfPosts(){
		return userFactory.get(userId).getPostList().size();
	}
	
	public int getAmountOfComments(){
		return userFactory.get(userId).getCommentList().size();
	}
	
	public int getAmountOfVotes(){
		//TODO: get user point list
		int amount = 0;
		List<Point> pointList = pointFactory.getAll();
		
		for(int i = 0; i < pointList.size(); i++){
			Point point = pointList.get(i);
			if (point.getUser().getId() == userId) {
				amount++;
			}
		}
		
		return amount;
	}
}
