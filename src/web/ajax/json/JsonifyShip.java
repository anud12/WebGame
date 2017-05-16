package web.ajax.json;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import persistence.table.entity.Part;
import persistence.table.entity.Ship;

public class JsonifyShip implements JSONMarshaller<Ship>
{
	protected JSONMarshaller<Part> partMarshaller;
	
	public JsonifyShip(JSONMarshaller<Part> partMarshaller)
	{
		this.partMarshaller = partMarshaller;
	}
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject marshal(Ship object)
	{
		object.calculateProperties();
		JSONObject ship = new JSONObject();
		
		ship.put("id", object.getId());
		ship.put("name", object.getName());
		ship.put("energy",  String.format("%1$.2f", object.getEnergy()/100.0f));
		ship.put("area", String.format("%1$.2f", object.getArea() / 100f));
		ship.put("rate", String.format("%1$.2f", object.getRate() / 100f));
		ship.put("sensorRadius", String.format("%1$.2f", object.getSensorRadius() / 100f));
		ship.put("thrust", object.getThrust());
		ship.put("x", object.getX());
		ship.put("y", object.getY());
		
		JSONArray keyValues = new JSONArray();
		ship.put("keyValues", keyValues);
		
		keyValues.add("id");
		keyValues.add("name");
		keyValues.add("energy");
		keyValues.add("area");
		keyValues.add("rate");
		keyValues.add("sensorRadius");
		keyValues.add("thrust");
		keyValues.add("x");
		keyValues.add("y");
		
		JSONArray partArray = new JSONArray();
		ship.put("parts", partArray);
		Iterator<Part> iterator = object.getPartList().iterator();
		while(iterator.hasNext())
		{
			
			Part part = iterator.next();
			
			JSONObject partObject = new JSONObject();
			partObject.put("name", part.getName());
			partArray.add(partObject);			
		}
		
		return ship;
	}

}
