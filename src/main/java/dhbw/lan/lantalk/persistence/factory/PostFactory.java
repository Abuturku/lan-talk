package dhbw.lan.lantalk.persistence.factory;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.objects.Post;
import dhbw.lan.lantalk.persistence.objects.TextType;
import dhbw.lan.lantalk.persistence.objects.User;

@Named("postFactory")
@Dependent
public class PostFactory extends AFactory<Post> {

	private static final long serialVersionUID = 6301412543332910585L;

	public PostFactory() {
		super(Post.class);
	}

	public Post create(Post post, User user) {
		post.setTextType(TextType.Post);
		post.setUser(user);
		user.addPost(post);
		return super.create(post);
	}
}
