package web.ajax.json;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import persistence.table.entity.Part;
import persistence.table.entity.Ship;

public class JsonifyShip implements JSONMarshaller<Ship>
{

	@Override
	public JSONObject marshal(Ship ship)
	{
		ship.calculateProperties();
		JSONObject shipObject = new JSONObject();
		
		//ID
		JSONObject shipIDObject = new JSONObject();
		shipIDObject.put("name", ship.getName());
		shipIDObject.put("id", ship.getId());
		shipObject.put("identity", shipIDObject);
		
		//Location
		JSONObject locationObject = new JSONObject();
		shipObject.put("location", locationObject);
		locationObject.put("x", ship.getX());
		locationObject.put("y", ship.getY());
		
		//Values
		JSONArray shipValuesArray = new JSONArray();
		shipObject.put("statistics", shipValuesArray);
		
		//Status Category
		
		JSONObject statusCategory = new JSONObject();
		
		statusCategory.put("name", "Status");
		
		//Status Array
		JSONArray statusArray = new JSONArray();
		
		//Values
		JSONObject statusValue = new JSONObject();
		
		statusValue.put("name", "Energy");
		statusValue.put("value", String.format("%1$.2f", ship.getEnergy()/100.0f));
		statusArray.add(statusValue);
		
		statusValue = new JSONObject();
		statusValue.put("name", "X");
		statusValue.put("value", String.format("%1$.2f", ship.getX()/100.0f));
		statusArray.add(statusValue);
		
		statusValue = new JSONObject();
		statusValue.put("name", "Y");
		statusValue.put("value", String.format("%1$.2f", ship.getY()/100.0f));
		statusArray.add(statusValue);
		
		statusValue = new JSONObject();
		statusValue.put("name", "Area");
		statusValue.put("value", String.format("%1$.2f", ship.getArea()/100.0f));
		statusArray.add(statusValue);
		
		statusValue = new JSONObject();
		statusValue.put("name", "Rate");
		statusValue.put("value", ship.getRate());
		statusArray.add(statusValue);
		
		statusValue = new JSONObject();
		statusValue.put("name", "Thrust");
		statusValue.put("value", ship.getThrust());
		statusArray.add(statusValue);
		
		statusCategory.put("values", statusArray);
		shipValuesArray.add(statusCategory);
		
		//Parts
		
		JSONObject partsCategory = new JSONObject();
		partsCategory.put("name", "Parts");
		
		//Part array
		JSONArray partsArray = new JSONArray();
		
		//Parts
		Iterator<Part> partIterator = ship.getPartList().iterator();
		int partNumber = 0;
		while(partIterator.hasNext())
		{
			Part part = partIterator.next();
			JSONObject partJSON = new JSONObject();
			partJSON.put("id", partNumber++);
			partJSON.put("name", part.getName());
			partJSON.put("value", "");
			
			partsArray.add(partJSON);
		}
		
		//Composing
		partsCategory.put("values", partsArray);
		shipValuesArray.add(partsCategory);
		
		return shipObject;
	}
	
}
