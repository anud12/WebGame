package test.game.controller;

import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import game.collection.GameDataContainer;
import game.controller.IAction;
import game.controller.ship.ShipWrapper;
import persistence.table.entity.Ship;

public class TestAction implements IAction<ShipWrapper>
{
	protected LinkedList<Map<String, Object>> argumentList;
	protected ShipWrapper ship;
	
	protected int deltaTimeMS;
	
	protected int remainingTimeMS;
	
	protected double difference;
	
	public TestAction()
	{
		argumentList = new LinkedList<>();
	}
	
	@Override
	public IAction call() throws Exception
	{
		Logger logger = Logger.getLogger("Ship " + ship.getId());
		remainingTimeMS = 0;
		//Check if there are any actions to do
		if(argumentList.size() < 1)
		{
			logger.log(Level.FINEST, "Returned, no actions to do");
			return this;
		}
		
		
		Map<String, Object> argument = this.argumentList.getFirst();
		int duration = Integer.parseInt((String) argument.get("duration"));
		long value = Long.parseLong((String) argument.get("amount"));
		
		int durationLeft = duration - deltaTimeMS;
		long updateValue = 0;
		
		logger.log(Level.FINEST, "Duration = " + duration);
		if(duration < 0)
		{
			this.argumentList.removeFirst();
			this.call();
			
			logger.log(Level.FINEST, "Returned, called next action");			
			return this;
		}
		
		
		argument.put("duration", (durationLeft + ""));
		ship.setEnergy(ship.getEnergy() + updateValue);
		
		if(deltaTimeMS > duration)
		{
			difference = value * duration + difference;
			updateValue = (long) difference;
			difference = difference - (long) difference;
			logger.log(Level.FINEST, "Sub delta time " + duration);
			
			remainingTimeMS = -durationLeft;
			deltaTimeMS = remainingTimeMS;	
			this.argumentList.removeFirst();
			
			this.call();
		}
		else
		{
			difference = value * deltaTimeMS + difference;
			updateValue = (long) difference;
			difference = difference - (long) difference;
			
		}
		
		
		ship.setEnergy(ship.getEnergy() + updateValue);
		
		logger.log(Level.FINEST, "Returned, updated " + updateValue + " time remaining " + durationLeft);
		
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

	@Override
	public void setDataContainer(GameDataContainer container)
	{
		// TODO Auto-generated method stub
	}
}
