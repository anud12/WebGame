package web.ajax;

import java.util.HashMap;

import web.ajax.controller.Input;
import web.ajax.controller.Login;
import web.ajax.controller.RequestController;

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
