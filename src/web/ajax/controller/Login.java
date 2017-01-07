package web.ajax.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import persistence.Persistence;
import persistence.table.entity.Tables;
import persistence.table.entity.User;
import spring.Spring;

public class Login implements Controller
{
	@SuppressWarnings("unchecked")
	public JSONObject action(HttpSession session, Map<String, Object> object)
	{
		String username = (String) object.get("username");
		String password = (String) object.get("password");
				
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
					break;
				}
			}
		}
		return returnObject;
	}
}
