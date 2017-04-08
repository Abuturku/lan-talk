package dhbw.lan.lantalk.persistence.factory;

import dhbw.lan.lantalk.persistence.objects.Comment;

public class CommentFactory extends AFactory<Comment> {
	private static CommentFactory commentFactory;

	public static synchronized CommentFactory getInstance() {
		if (commentFactory == null) {
			commentFactory = new CommentFactory();
		}
		return commentFactory;
	}
}
