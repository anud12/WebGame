package web.ajax;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

public interface AjaxAction 
{
	public JSONObject action(HttpSession session, JSONObject object);
}
