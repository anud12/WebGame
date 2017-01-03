package web;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import game.Loop;
import persistence.Persistence;
import persistence.table.entity.User;
import spring.Spring;
import web.ajax.AjaxAction;
import web.ajax.json.JsonifyUser;

@WebServlet(urlPatterns = "/Ajax")
public class Ajax extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ajax() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONArray array = (JSONArray) parser.parse(request.getReader());
			
			JSONObject returnObject = new JSONObject();
			
			System.out.println(array);
			
			Iterator<JSONObject> iterator = array.iterator();
			while(iterator.hasNext())
			{
				JSONObject object = iterator.next();
				
				String actionName = (String) object.keySet().iterator().next(); 
				System.out.println(actionName);
				if(Spring.getAjax().containsBean(actionName))
				{
					System.out.println("TRUE");
					AjaxAction action = (AjaxAction) Spring.getAjax().getBean(actionName);
					
					JSONObject actionMessage = action.action(request.getSession(), object);
					JSONObject actionObject = new JSONObject();
					
					actionObject.put(actionName, actionMessage);
					
					returnObject.put("actionName" ,actionObject);
				}
			}
			
			JsonifyUser jsonify = (JsonifyUser) Spring.getAjax().getBean("JsonifyUser");
			
			returnObject.put("user", jsonify.marshal(request.getSession()));
			
			response.getWriter().write(returnObject.toString());
			
		}
		catch (IOException | ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}