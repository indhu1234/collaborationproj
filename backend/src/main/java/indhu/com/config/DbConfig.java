package indhu.com.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import indhu.com.model.Blog;
import indhu.com.model.BlogComment;
import indhu.com.model.BlogDislike;
import indhu.com.model.BlogLike;
import indhu.com.model.Forum;
import indhu.com.model.ForumComment;
import indhu.com.model.Friend;
import indhu.com.model.Job;
import indhu.com.model.ProfilePic;

import indhu.com.model.UserDetail;

@Configuration
@ComponentScan("indhu.com")
@EnableTransactionManagement
public class DbConfig {

	@Bean(name="dataSource")
	public DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		/*dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1522:orcl");
		dataSource.setUsername("hr");
		dataSource.setPassword("Admin12345");*/
		
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:tcp://localhost/~/forum");
		dataSource.setUsername("sa");
        dataSource.setPassword("sa");
		
		System.out.println("---Data Source created---");
		return dataSource;
	}

	//@Bean(name = "sessionFactory")
	@Bean
	public SessionFactory getSessionFactory() {
		Properties hibernateProp = new Properties();
		hibernateProp.put("hibernate.hbm2ddl.auto", "update");
		
		hibernateProp.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");
		hibernateProp.put("hibernate.show_sql", true);
		hibernateProp.put("hibernate.format_sql", true);
				
		LocalSessionFactoryBuilder factoryBuilder = new LocalSessionFactoryBuilder(getDataSource());
		factoryBuilder.addProperties(hibernateProp);

		factoryBuilder.addAnnotatedClass(Blog.class);
		factoryBuilder.addAnnotatedClass(UserDetail.class);
		factoryBuilder.addAnnotatedClass(BlogComment.class);
		factoryBuilder.addAnnotatedClass(Job.class);
		factoryBuilder.addAnnotatedClass(Forum.class);
		factoryBuilder.addAnnotatedClass(ForumComment.class);
		factoryBuilder.addAnnotatedClass(BlogLike.class);
		factoryBuilder.addAnnotatedClass(BlogDislike.class);
		factoryBuilder.addAnnotatedClass(ProfilePic.class);
		factoryBuilder.addAnnotatedClass(Friend.class);

	



		SessionFactory sessionFactory = factoryBuilder.buildSessionFactory();
		System.out.println("---SessionFactory created---");
		return sessionFactory;
	}

	@Bean
	public HibernateTransactionManager getHibernateTransactionManager(SessionFactory sessionFactory) {
		System.out.println("---TransactionManager created---");
		return new HibernateTransactionManager(sessionFactory);
	}
}