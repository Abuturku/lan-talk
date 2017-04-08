package dhbw.lan.lantalk.persistence.factory;

import dhbw.lan.lantalk.persistence.objects.User;

public class UserFactory extends AFactory<User> {

	private static UserFactory userFactory;

	public static synchronized UserFactory getInstance() {
		if (userFactory == null) {
			userFactory = new UserFactory();
		}
		return userFactory;
	}

}
