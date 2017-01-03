package test.web;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSONTest
{
	public static void test(HttpServletRequest request)
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONArray array = (JSONArray) parser.parse(request.getReader());
			
			Iterator<JSONObject> iterator = array.iterator();
			while(iterator.hasNext())
			{
				JSONObject object = iterator.next();
				System.out.println(object.toJSONString());
				
				if(object.containsKey("userPanel"))
				{
					object.get("userPanel");
					System.out.println("USERPANEL!");
				}
			}
		}
		catch (IOException | ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
