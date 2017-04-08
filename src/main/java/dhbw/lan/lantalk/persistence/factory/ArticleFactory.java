package dhbw.lan.lantalk.persistence.factory;

import dhbw.lan.lantalk.persistence.objects.Article;

public class ArticleFactory extends AFactory<Article> {

	private static ArticleFactory articleFactory;

	public static synchronized ArticleFactory getInstance() {
		if (articleFactory == null) {
			articleFactory = new ArticleFactory();
		}
		return articleFactory;
	}

}
