package web.ajax.json;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import persistence.Persistence;
import persistence.table.entity.Part;
import persistence.table.entity.Ship;
import persistence.table.entity.User;
import spring.Spring;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

public class JsonifySession implements JSONMarshaller<HttpSession>
{
	protected JSONMarshaller<User> userMarshaller;
	protected JSONMarshaller<Part> partMarshaller;
	protected JSONMarshaller<Ship> shipMarshaller;
	public JsonifySession (JSONMarshaller<User> userMarshaller,JSONMarshaller<Part> partMarshaller, JSONMarshaller<Ship> shipMarshaller)
	{
		this.userMarshaller = userMarshaller;
		this.partMarshaller = partMarshaller;
		this.shipMarshaller = shipMarshaller;
	}
	@Override
	public JSONObject marshal(HttpSession session)
	{
		// TODO Auto-generated method stub
		JSONObject wrapperObject = new JSONObject();
		
		
		int userId = -1;
		userId =  (int) session.getAttribute("user");
		
		if(userId != -1)
		{
			
			
			Persistence persistence = (Persistence) Spring.getPersistence().getBean("Persistence");
			Session dbsession = persistence.getSessionFactory().openSession();
			try
			{	
				///TODO add layer 
				User user = (User) dbsession.createQuery("from User u where u.id = '" + userId + "'").getSingleResult();
				
				JSONObject userJSON =  userMarshaller.marshal(user);
				wrapperObject.put("user", userJSON);
				
				
				List<Part> partList = dbsession.createQuery("from Part ").getResultList();
				JSONObject partObject = new JSONObject();
				wrapperObject.put("parts", partObject);
				
				JSONArray partKeys = new JSONArray();
				partObject.put("keys", partKeys);
				
				Iterator<Part> partIterator = partList.iterator();
				while(partIterator.hasNext())
				{
					Part part = partIterator.next();
					partObject.put(part.getName(), partMarshaller.marshal(part));
					partKeys.add(part.getName());
				}
				
				Collection<Ship> shipList = user.getShips();
				JSONObject shipObjectt = new JSONObject();
				wrapperObject.put("ships", shipObjectt);
				
				JSONArray shipKeys = new JSONArray();
				shipObjectt.put("keys", shipKeys);
				Iterator<Ship> shipIterator = shipList.iterator();
				while(shipIterator.hasNext())
				{
					Ship ship = shipIterator.next();
					shipObjectt.put(ship.getName(), shipMarshaller.marshal(ship));
					shipKeys.add(ship.getName());
				}
				
				dbsession.close();
			}
			catch(HibernateException exception)
			{
				exception.printStackTrace();
			}
			finally
			{
				dbsession.close();
			}
		}
		
		return wrapperObject;
	}

	
}
