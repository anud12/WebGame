package web.ajax;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import game.Loop;
import persistence.Persistence;
import persistence.table.entity.Tables;
import persistence.table.entity.User;
import spring.Spring;

public class Login implements AjaxAction
{
	public JSONObject action(HttpSession session, JSONObject object)
	{
		JSONObject loginObject = (JSONObject) object.values().iterator().next();
		String username = (String) loginObject.get("username");
		String password = (String) loginObject.get("password");
		
		System.out.println(username + " | " + password);
		
		JSONObject returnObject = new JSONObject();
		
		Persistence persistence = (Persistence) Spring.getPersistence().getBean("Persistence");
				
		Collection<User> results = Tables.getUsers().values();
		
		Iterator<User> iterator = results.iterator();
		
		boolean found = false;
		while(iterator.hasNext())
		{
			User user = iterator.next();
			
			if(user.getName().equals(username))
			{
				if(user.getPassword().equals(password))
				{
					session.setAttribute("user", user.getId());				
					found = true;
					
					returnObject.put("username", username);
					returnObject.put("increment", user.getIncrement());
					System.out.println("Logged in as" + username);
					break;
				}
			}
		}
		return returnObject;
	}
}
