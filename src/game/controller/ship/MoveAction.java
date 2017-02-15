package game.controller.ship;

import java.util.Map;

import org.json.simple.JSONObject;

import game.controller.IAction;
import persistence.table.entity.Ship;

public class MoveAction implements IAction<ShipWrapper>
{
	protected ShipWrapper ship;
	
	protected long xDestination;
	protected long yDestination;
	protected int deltaTimeMS;
	
	public MoveAction()
	{
	}
	
	@Override
	public IAction<ShipWrapper> call() throws Exception
	{
		long x = ship.getX();
		long y = ship.getY();
		
		if(xDestination == x && yDestination == y)
		{
			return this;
		}
		double thrust = ship.getThrust();
		
		System.out.println(this + " : Initial (" + x + " , " + y + ") thrust (" + thrust + ")");
		System.out.println(this + " : Destination (" + xDestination + " , " + yDestination + ")");
		
		long xMoved = xDestination - x; 
		long yMoved = yDestination - y;
		
		System.out.println(this + " : Moved (" + xMoved + " , " + yMoved + ")");
		
		double distance = Math.sqrt(Math.pow(xMoved, 2) + Math.pow(yMoved, 2));
		System.out.println(this + " : Distance " + distance);
		
		double xNormalized = xMoved / (distance * 1.0);
		double yNormalized = yMoved / (distance * 1.0);
		
		System.out.println(this + " : Normalized (" + xNormalized + " , " + yNormalized + ")");
		
		double xDistance = thrust * xNormalized * deltaTimeMS / 10;
		double yDistance = thrust * yNormalized * deltaTimeMS / 10;
		
		System.out.println(this + " : Normalized Distance (" + xDistance + " , " + yDistance + ")");
		
		long xNew = (long) xDistance + x;
		long yNew = (long) yDistance + y;
		System.out.println(this + " : Result (" + xNew + "," + yNew +")");
		
		System.out.println(this + " : Distance "+ Math.abs(xDistance) + " > " + Math.abs(xMoved));
		
		if( Math.abs(xDistance) > Math.abs(xMoved))
		{
			System.out.println(this + " : Set X to destination (" + xDestination + ")");
			ship.setX(xDestination);
		}
		else
		{
			System.out.println(this + " : Set X (" + xNew + ")");
			ship.setX(xNew);
		}
		
		if( Math.abs(yDistance) > Math.abs(yMoved))
		{
			System.out.println(this + " : Set Y to destination (" + yDestination + ")");
			ship.setY(yDestination);
		}
		else
		{
			System.out.println(this + " : Set Y (" + yNew + ")");
			ship.setY(yNew);
		}
		
		return this;
	}

	@Override
	public void subscriptionNotice(ShipWrapper object)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addArguments(Map<String, Object> arguments)
	{
		xDestination = Long.parseLong((String) arguments.get("x")) * 100;
		yDestination = Long.parseLong((String) arguments.get("y")) * 100;
	}

	@Override
	public void setTarget(ShipWrapper object)
	{
		this.ship = object;
		
	}

	@Override
	public ShipWrapper getTarget()
	{
		// TODO Auto-generated method stub
		return null;
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

	

}
