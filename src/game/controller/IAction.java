package game.controller;

import java.util.Map;
import java.util.concurrent.Callable;

import org.json.simple.JSONObject;


public interface IAction<T> extends Callable<IAction>
{
	public void subscriptionNotice(T object);
	public void addArguments(Map<String,Object> arguments);
	public void setTarget(T object);
	public T getTarget();
	public void setDeltaTimeMS(int deltaTime);
	public JSONObject getJSONDefinition();
	
}
