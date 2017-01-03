package web.initialization;

import javax.servlet.http.HttpSessionListener;

import persistence.table.entity.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

@WebListener
public class SessionInitializer implements HttpSessionListener
{

	@Override
	public void sessionCreated(HttpSessionEvent arg0)
	{
		// TODO Auto-generated method stub
		
		HttpSession session = arg0.getSession();
		
		session.setAttribute("user", -1);	
		System.out.println("Session : " + session.getId() + " created");
		
		session.setMaxInactiveInterval(20);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

}
