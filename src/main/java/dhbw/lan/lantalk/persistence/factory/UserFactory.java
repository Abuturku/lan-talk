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

}
