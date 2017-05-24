package dhbw.lan.lantalk.persistence.factory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.annotation.security.PermitAll;
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

	@PermitAll
	public void changePassword(User user, String password) {
		String encodePassword;
		try {
			encodePassword = new String(
					Base64.getEncoder().encode(MessageDigest.getInstance("SHA-256").digest(password.getBytes())),
					"UTF-8");
			user.setPassword(encodePassword);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}
}
