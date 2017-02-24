package web;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import persistence.table.entity.User;
import spring.Spring;
import web.ajax.ControllerMap;
import web.ajax.controller.RequestController;
import web.ajax.json.JSONMarshaller;
import web.ajax.json.JsonifyUser;

@WebServlet(urlPatterns = "/Ajax")
public class Ajax extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private ControllerMap controllerMap;       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ajax() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init()
    {
    	controllerMap = (ControllerMap) Spring.getAjax().getBean("ControllerMap"); 
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
	@SuppressWarnings({ "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONArray recievedJSON = (JSONArray) parser.parse(request.getReader());
			
			JSONObject returnObject = new JSONObject();
			
			//System.out.println(this + " Recieved IP " + request.getRemoteAddr() + " json : " + recievedJSON);
			
			Iterator<JSONObject> iterator = recievedJSON.iterator();
			
			while(iterator.hasNext())
			{
				JSONObject JSONParameters = iterator.next();
				
				JSONObject controllerResponse = new JSONObject();
				
				Map<String, Object> parametersMap = (Map<String, Object>)JSONParameters;
				
				String controllerName = (String) parametersMap.keySet().iterator().next(); 
				
				if(controllerMap.containsKey(controllerName))
				{
					RequestController controller = controllerMap.get(controllerName);
					Map<String,Object> parameters = (Map<String, Object>) parametersMap.get(controllerName);
					
					JSONObject actionReturn = controller.action(request.getSession(), parameters);
					
					controllerResponse.put(controllerName, actionReturn);
					returnObject.put("actionName" ,controllerResponse);
				}
			}
			
			JSONMarshaller<HttpSession> jsonify = (JSONMarshaller<HttpSession>) Spring.getAjax().getBean("JsonifySession");
			
			returnObject.put("user", jsonify.marshal(request.getSession()));
			returnObject.put("time", (new Date().toGMTString()) + " " + new Date().getTime() );
			
			response.getWriter().write(returnObject.toString());
			
		}
		catch (IOException | ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}