package web.ajax.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

public interface RequestController 
{
	public JSONObject action(HttpSession session, Map<String, Object> object);
}
