package indhu.com.test;

import org.junit.BeforeClass;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import indhu.com.dao.BlogLikeDislikeDAO;

public class BlogLikeDislikeDAOTestCase {

	static BlogLikeDislikeDAO blogLikeDislikeDAO;

	@BeforeClass
	public static void executeFirst() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("indhu.com");
		context.refresh();
		blogLikeDislikeDAO = (BlogLikeDislikeDAO) context.getBean("blogLikeDislikeDAO");
	}
}