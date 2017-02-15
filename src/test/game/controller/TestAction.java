package test.game.controller;

import java.util.LinkedList;
import java.util.Map;

import org.json.simple.JSONObject;

import game.controller.IAction;
import game.controller.ship.ShipWrapper;
import persistence.table.entity.Ship;

public class TestAction implements IAction<ShipWrapper>
{
	protected LinkedList<Map<String, Object>> argumentList;
	protected ShipWrapper ship;
	
	protected int deltaTimeMS;
	
	protected int remainingTimeMS;
	
	public TestAction()
	{
		argumentList = new LinkedList<>();
	}
	
	@Override
	public IAction call() throws Exception
	{
		remainingTimeMS = 0;
		//Check if there are any actions to do
		if(argumentList.size() < 1)
		{
			System.out.println(this + " : Returned, no actions to do");
			return this;
		}
		
		
		Map<String, Object> argument = this.argumentList.getFirst();
		int duration = Integer.parseInt((String) argument.get("duration"));
		long value = Long.parseLong((String) argument.get("amount"));
		
		int durationLeft = duration - deltaTimeMS;
		long updateValue = 0;
		
		System.out.println(this + " : Duration = " + duration);
		if(duration < 0)
		{
			this.argumentList.removeFirst();
			this.call();
			
			System.out.println(this + " : Returned, called next action");
			
			return this;
		}
		
		
		argument.put("duration", (durationLeft + ""));
		ship.setEnergy(ship.getEnergy() + updateValue);
		
		if(deltaTimeMS > duration)
		{
			updateValue = value * duration;
			System.out.println(this + " : Sub delta time " + duration);
			
			remainingTimeMS = -durationLeft;
			deltaTimeMS = remainingTimeMS;	
			this.argumentList.removeFirst();
			
			this.call();
		}
		else
		{
			updateValue = value * deltaTimeMS;
		}
		
		
		ship.setEnergy(ship.getEnergy() + updateValue);
		
		System.out.println(this + " : Returned, updated " + updateValue + " time remaining " + durationLeft);
		
		return this;
	}
	
	@Override
	public void setDeltaTimeMS(int deltaTimeMS)
	{
		this.deltaTimeMS = deltaTimeMS;
	}

	@Override
	public void setTarget(ShipWrapper object)
	{
		ship = object;
	}

	@Override
	public ShipWrapper getTarget()
	{
		return ship;
	}

	@Override
	public void addArguments(Map arguments)
	{
		this.argumentList.add(arguments);
	}

	@Override
	public JSONObject getJSONDefinition()
	{
		JSONObject returnObject = new JSONObject();
		return returnObject;
	}

	@Override
	public void subscriptionNotice(ShipWrapper object)
	{
		// TODO Auto-generated method stub
		
	}
}
