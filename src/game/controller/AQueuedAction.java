package game.controller;

import java.util.LinkedList;
import java.util.Map;

import org.json.simple.JSONObject;

import game.collection.GameDataContainer;

public abstract class AQueuedAction<T> implements IAction<T>
{
	protected LinkedList<Map<String, Object>> argumentList;
	protected int deltaTime;
	protected T target;
	
	public AQueuedAction()
	{
		argumentList = new LinkedList<>();
	}
	
	@Override
	public abstract void subscriptionNotice(T object);

	@Override
	public abstract JSONObject getJSONDefinition();

	@Override
	public abstract void setDataContainer(GameDataContainer container);
	
	protected abstract void executeArgument(T target, int deltaTime, LinkedList<Map<String, Object>> argumentList);
	
	@Override
	public IAction<T> call() throws Exception
	{
		this.executeArgument(target, deltaTime, argumentList);
		return this;
	}
	
	@Override
	public void addArguments(Map<String, Object> arguments)
	{
		argumentList.add(arguments);
	}
	
	@Override
	public void setDeltaTimeMS(int deltaTime)
	{
		this.deltaTime = deltaTime;
	}
	
	@Override
	public void setTarget(T object)
	{
		this.target = object;
	}

	@Override
	public T getTarget()
	{
		return target;
	}
}
