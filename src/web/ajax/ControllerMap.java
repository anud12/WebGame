package web.ajax;

import java.util.HashMap;

import web.ajax.controller.Controller;
import web.ajax.controller.Login;

public class ControllerMap extends HashMap<String, Controller>
{
	private static final long serialVersionUID = 1L;

	public ControllerMap()
	{
		super();
		
		this.put("Login", new Login());
	}
}
