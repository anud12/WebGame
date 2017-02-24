package web.ajax.json;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import persistence.Persistence;
import persistence.table.entity.Part;
import persistence.table.entity.Ship;
import persistence.table.entity.User;
import spring.Spring;

public class JsonifyUser implements JSONMarshaller<User>
{
	protected JSONMarshaller<Ship> jsonifyShip;
	public JsonifyUser(JSONMarshaller<Ship> jsonifyShip)
	{
		this.jsonifyShip = jsonifyShip;
	}
	@SuppressWarnings("unchecked")
	public JSONObject marshal (User user)
	{		
		JSONObject wrapperObject = null;
		
		JSONObject userObject = new JSONObject();
		
		userObject.put("increment", user.getIncrement());
						
		wrapperObject = new JSONObject();
		
		wrapperObject.put("data", userObject);
		
		Iterator<Ship> shipsIterator = user.getShips().iterator();
		JSONArray array = new JSONArray();
		while(shipsIterator.hasNext())
		{			
			Ship ship = shipsIterator.next();
			array.add(jsonifyShip.marshal(ship));
			
		}
		wrapperObject.put("ship", array);
		
		
		return wrapperObject;
	}
	@SuppressWarnings("unchecked")
	public JSONObject marshal(HttpSession session)
	{
		// TODO Auto-generated method stub
		JSONObject wrapperObject = null;
		
		JSONObject userObject = new JSONObject();
		
		int userId = -1;
		userId =  (int) session.getAttribute("user");
		
		if(userId != -1)
		{
			Persistence persistence = (Persistence) Spring.getPersistence().getBean("Persistence");
			
			Session dbsession = persistence.getSessionFactory().openSession();
			
			User user = (User) dbsession.createQuery("from User u where u.id = '" + userId + "'").getSingleResult();
			
			wrapperObject = this.marshal(user);
						
			dbsession.close();
		}
		return wrapperObject;
	}
}
