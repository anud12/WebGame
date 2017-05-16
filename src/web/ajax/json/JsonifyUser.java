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
		wrapperObject = new JSONObject();
		
		JSONObject userObject = new JSONObject();
		
		userObject.put("increment", user.getIncrement());
				
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
}
