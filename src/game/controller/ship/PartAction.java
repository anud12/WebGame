package game.controller.ship;

import java.util.Map;

import org.json.simple.JSONObject;

import game.controller.IAction;
import persistence.table.entity.Ship;

public class PartAction implements IAction<ShipWrapper>
{
	protected ShipWrapper ship;
	protected int deltaTimeMS;
	
	@Override
	public IAction<ShipWrapper> call() throws Exception
	{		
		long increment = ship.getEnergy();
		long newIncrement = (long) (increment + (ship.getRate() * deltaTimeMS / 10));
		
		System.out.println(this + " : DeltaTime " + deltaTimeMS + " newIncrement " + newIncrement + " rate " + ship.getRate());
		
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

	
}
