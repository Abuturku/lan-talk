package dhbw.lan.lantalk.persistence.factory;

import dhbw.lan.lantalk.persistence.objects.Article;

public class ArticleFactory extends AFactory<Article> {

	private static final long serialVersionUID = 6301412543332910585L;

	private ArticleFactory() {
		super(Article.class);
	}

	private static ArticleFactory articleFactory;

	public static synchronized ArticleFactory getInstance() {
		if (articleFactory == null) {
			articleFactory = new ArticleFactory();
		}
		return articleFactory;
	}

	@Override
	protected void setParameter(Article toSet, Article orginal) {
		toSet.setCommentList(orginal.getCommentList());
		toSet.setPoints(orginal.getPoints());
		toSet.setText(orginal.getText());
		toSet.setUser(orginal.getUser());
	}

}
