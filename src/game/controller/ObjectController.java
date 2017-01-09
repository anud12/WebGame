package game.controller;

import java.util.Map;

public interface ObjectController
{
	public void add(String actionName, Map<String, Object> arguments);
	public void clear();
	public void queue(String actionName, Map<String, Object> arguments);
}
