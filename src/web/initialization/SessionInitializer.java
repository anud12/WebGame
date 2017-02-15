package web.initialization;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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
