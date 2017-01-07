package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import persistence.Persistence;
import test.spring.TestBean;

public class Spring
{
	protected static ApplicationContext persistence;
	protected static ApplicationContext ajax;
	protected static ApplicationContext game;
	public static void initialize()
	{
		persistence = new ClassPathXmlApplicationContext("persistence.spring.xml");
		ajax = new ClassPathXmlApplicationContext("ajax.spring.xml");
		
		game = new ClassPathXmlApplicationContext("ajax.spring.xml");
		//game = new ClassPathXmlApplicationContext("game.spring.xml");
	}

	public static ApplicationContext getPersistence()
	{
		return persistence;
	}

	public static void setPersistence(ApplicationContext context)
	{
		Spring.persistence = context;
	}

	public static ApplicationContext getAjax()
	{
		return ajax;
	}

	public static void setAjax(ApplicationContext ajax)
	{
		Spring.ajax = ajax;
	}

	public static ApplicationContext getGame()
	{
		return game;
	}

	public static void setGame(ApplicationContext game)
	{
		Spring.game = game;
	}
	
	
}
