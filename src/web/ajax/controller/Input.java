package web.ajax.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import game.collection.GameDataContainer;
import game.collection.ShipCollection;
import spring.Spring;

public class Input implements RequestController
{

	@Override
	public JSONObject action(HttpSession session, Map<String, Object> object)
	{
		JSONObject returnObject = new JSONObject();
		
		returnObject.put("success", true);
		System.out.println(this + " : Object :" + object);
		
		GameDataContainer dataContainer = (GameDataContainer) Spring.getGame().getBean("DataContainer");
		
		ShipCollection collection = (ShipCollection) dataContainer.get("Ship");
		
		String name = (String) object.get("name");
		int target = Integer.parseInt((String) object.get("target"));
		Map<String, Object> arguments = (Map<String, Object>) object.get("arguments");
		
		
		collection.getControllers().get(target).add(name, arguments);
		return returnObject;
	}

}
