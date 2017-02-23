package game.controller.ship;

import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import game.collection.GameDataContainer;
import game.controller.IAction;
import persistence.table.entity.Ship;

public class PartAction implements IAction<ShipWrapper>
{
	protected ShipWrapper ship;
	protected int deltaTimeMS;
	protected double difference;
	protected Logger logger;
	
	
	@Override
	public IAction<ShipWrapper> call() throws Exception
	{		
		logger = Logger.getLogger("Ship " + ship.getId());
		
		
		logger.log(Level.FINEST, "Initial difference " + difference);
		
		long increment = ship.getEnergy();
		
		logger.log(Level.FINEST, "" + ship.getRate() + "*" + deltaTimeMS + "/" + 10 + "+" + difference);
		
		difference = ship.getRate() * deltaTimeMS / 10 + difference;
		
		logger.log(Level.FINEST, "rate difference " + difference);
		
		long newIncrement = (long) difference + increment;
				
		difference = difference - (long) difference;
		
		logger.log(Level.FINEST, "Lost " + difference);
		
		logger.log(Level.FINEST, "DeltaTime " + deltaTimeMS + " newIncrement " + newIncrement + " rate " + ship.getRate());
		
		if(newIncrement < ship.getArea())
		{
			increment = newIncrement;
		}
		else
		{
			increment = ship.getArea();
		}
		
		ship.setEnergy(increment);
		return this;
	}
	
	@Override
	public void subscriptionNotice(ShipWrapper object)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addArguments(Map arguments)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDeltaTimeMS(int deltaTime)
	{
		this.deltaTimeMS = deltaTime;
	}

	@Override
	public JSONObject getJSONDefinition()
	{
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void setTarget(ShipWrapper object)
	{
		this.ship = object;
	}

	@Override
	public ShipWrapper getTarget()
	{
		
		return ship;
	}

	@Override
	public void setDataContainer(GameDataContainer container)
	{
		// TODO Auto-generated method stub
	}

	
}
