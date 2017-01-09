package web.ajax;

import java.util.HashMap;

import web.ajax.controller.RequestController;
import web.ajax.controller.Input;
import web.ajax.controller.Login;

public class ControllerMap extends HashMap<String, RequestController>
{
	private static final long serialVersionUID = 1L;

	public ControllerMap()
	{
		super();
		
		this.put("Login", new Login());
		this.put("Input", new Input());
	}
}
