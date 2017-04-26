package dhbw.lan.lantalk.persistence.factory;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.objects.Post;

@Named("postFactory")
@Dependent
public class PostFactory extends AFactory<Post> {

	private static final long serialVersionUID = 6301412543332910585L;

	private PostFactory() {
		super(Post.class);
	}

 

}
