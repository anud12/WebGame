package web.ajax.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import persistence.table.entity.Part;
import persistence.table.entity.PartEngine;
import persistence.table.entity.PartGenerator;
import persistence.table.entity.PartStorage;
import persistence.table.entity.Ship;

public class JsonifyPart implements JSONMarshaller<Part>
{
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject marshal(Part object)
	{
		JSONObject part = new JSONObject();
		
		part.put("id", object.getId());
		part.put("name", object.getName());
		part.put("type", object.getType());
		
		
		JSONArray keyValues = new JSONArray();
		part.put("keyValues", keyValues);
		keyValues.add("id");
		keyValues.add("name");
		keyValues.add("type");
		
		return part;
	}

}
