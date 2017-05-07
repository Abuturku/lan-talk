package dhbw.lan.lantalk.persistence.factory;

import javax.enterprise.context.Dependent;
import javax.inject.Named;


import dhbw.lan.lantalk.persistence.objects.User;

@Named("userFactory")
@Dependent
public class UserFactory extends AFactory<User> {

	private static final long serialVersionUID = -928517708638283242L;

	public UserFactory() {
		super(User.class);
	}

	@Override
	protected void setParameter(User toSet, User orginal) {
		toSet.setPostList(orginal.getPostList());
		toSet.setCommentList(orginal.getCommentList());
		toSet.setName(orginal.getName());
		toSet.setPoints(orginal.getPoints());
		toSet.setRank(orginal.getRank());
		toSet.setRegTime(orginal.getRegTime());
	}
}
