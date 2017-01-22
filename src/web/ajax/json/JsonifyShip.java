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
		statusValue.put("value", ship.getEnergy()/1000.0f);
		statusArray.add(statusValue);
		
		statusCategory.put("values", statusArray);
		shipValuesArray.add(statusCategory);
		
		
		//Specifications Category
		JSONObject specificationCategory = new JSONObject();
		specificationCategory.put("name", "Specifications");
		
		//Category Array
		JSONArray specificationArray = new JSONArray();

		//Values
		ship.calculateProperties();
		
		JSONObject specificationValue = new JSONObject();
		specificationValue.put("name", "Area");
		specificationValue.put("value", ship.getArea()/1000.0f);
		specificationArray.add(specificationValue);
		
		specificationValue = new JSONObject();
		specificationValue.put("name", "Rate");
		specificationValue.put("value", ship.getRate());
		specificationArray.add(specificationValue);
		
		
		//Composing
		specificationCategory.put("values", specificationArray);
		shipValuesArray.add(specificationCategory);
		/*
		shipObject.put("name", ship.getName());
		shipObject.put("value", ship.getValue());
		shipObject.put("area", ship.getArea());
		shipObject.put("rate", ship.getRate());
		*/
		
		
		//Parts
		
		JSONObject partsCategory = new JSONObject();
		partsCategory.put("name", "Parts");
		
		//Part array
		JSONArray partsArray = new JSONArray();
		
		//Parts
		Iterator<Part> partIterator = ship.getPartList().iterator();
		while(partIterator.hasNext())
		{
			Part part = partIterator.next();
			JSONObject partJSON = new JSONObject();
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
