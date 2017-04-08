package dhbw.lan.lantalk.persistence.factory;

import dhbw.lan.lantalk.persistence.objects.User;

public class UserFactory extends AFactory<User> {

	private UserFactory() {
		super(User.class);
	}

	private static UserFactory userFactory;

	public static synchronized UserFactory getInstance() {
		if (userFactory == null) {
			userFactory = new UserFactory();
		}
		return userFactory;
	}

	@Override
	protected void setParameter(User toSet, User orginal) {
		toSet.setArticleList(orginal.getArticleList());
		toSet.setCommentList(orginal.getCommentList());
		toSet.setName(orginal.getName());
		toSet.setPoints(orginal.getPoints());
	}

}
