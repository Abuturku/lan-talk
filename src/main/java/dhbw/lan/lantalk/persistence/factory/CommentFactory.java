package dhbw.lan.lantalk.persistence.factory;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

import dhbw.lan.lantalk.persistence.objects.Comment;

@Named("commentFactory")
@Dependent
public class CommentFactory extends AFactory<Comment> {

	private static final long serialVersionUID = -7117587465443453557L;

	public CommentFactory() {
		super(Comment.class);
	}

	@Override
	protected void setParameter(Comment toSet, Comment orginal) {
		toSet.setArticle(orginal.getArticle());
		toSet.setPoints(orginal.getPoints());
		toSet.setText(orginal.getText());
		toSet.setUser(orginal.getUser());
		toSet.setTime(orginal.getTime());
	}
}
