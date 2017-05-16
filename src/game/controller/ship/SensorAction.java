package game.controller.ship;

import java.util.Map;

import org.json.simple.JSONObject;

import game.collection.GameDataContainer;
import game.controller.IAction;
import persistence.table.entity.Ship;

public class SensorAction implements IAction<Ship>
{
	protected Ship target;
	@Override
	public IAction call() throws Exception
	{
		double sensorRadius = target.getSensorRadius();
		
		return this;
	}

	@Override
	public void subscriptionNotice(Ship object)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addArguments(Map<String, Object> arguments)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTarget(Ship object)
	{
		this.target = object;
	}

	@Override
	public Ship getTarget()
	{
		return target;
	}

	@Override
	public void setDeltaTimeMS(int deltaTime)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject getJSONDefinition()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataContainer(GameDataContainer container)
	{
		// TODO Auto-generated method stub
		
	}

	
}
