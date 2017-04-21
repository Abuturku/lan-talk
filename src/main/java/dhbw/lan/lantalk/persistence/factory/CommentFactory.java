package dhbw.lan.lantalk.persistence.factory;

import dhbw.lan.lantalk.persistence.objects.Comment;

public class CommentFactory extends AFactory<Comment> {

	private static final long serialVersionUID = -7117587465443453557L;

	private CommentFactory() {
		super(Comment.class);
	}

	private static CommentFactory commentFactory;

	public static synchronized CommentFactory getInstance() {
		if (commentFactory == null) {
			commentFactory = new CommentFactory();
		}
		return commentFactory;
	}

	@Override
	protected void setParameter(Comment toSet, Comment orginal) {
		toSet.setArticle(orginal.getArticle());
		toSet.setPoints(orginal.getPoints());
		toSet.setText(orginal.getText());
		toSet.setUser(orginal.getUser());
	}
}
