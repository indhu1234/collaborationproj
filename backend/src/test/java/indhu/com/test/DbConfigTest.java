package indhu.com.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbConfigTest {
	public static void main(String arg[]) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("indhu.com");
		context.refresh();
		
	}
}