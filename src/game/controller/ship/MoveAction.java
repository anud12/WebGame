package game.controller.ship;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import game.collection.GameDataContainer;
import game.controller.IAction;
import persistence.table.entity.Ship;

public class MoveAction implements IAction<ShipWrapper>
{
	protected ShipWrapper ship;
	
	protected long xDestination;
	protected long yDestination;
	protected int deltaTimeMS;
	
	protected double xDifference;
	protected double yDifference;
	
	public MoveAction()
	{
	}
	
	@Override
	public IAction<ShipWrapper> call() throws Exception
	{
		Logger logger = Logger.getLogger("Ship " + ship.getId());
		
		long x = ship.getX();
		long y = ship.getY();
		
		if(xDestination == x && yDestination == y)
		{
			return this;
		}
		double thrust = ship.getThrust();
		
		
		logger.log(Level.FINEST, "Initial (" + x + " , " + y + ") thrust (" + thrust + ")");
		logger.log(Level.FINEST, "Initial (" + x + " , " + y + ") thrust (" + thrust + ")");
		
		long xMoved = xDestination - x; 
		long yMoved = yDestination - y;
		
		logger.log(Level.FINEST, " Moved (" + xMoved + " , " + yMoved + ")");
		
		double distance = Math.sqrt(Math.pow(xMoved, 2) + Math.pow(yMoved, 2));
		logger.log(Level.FINEST, "Distance " + distance);
		
		double xNormalized = xMoved / (distance * 1.0);
		double yNormalized = yMoved / (distance * 1.0);
		
		logger.log(Level.FINEST, "Normalized (" + xNormalized + " , " + yNormalized + ")");
		
		double xDistance = thrust * xNormalized * deltaTimeMS / 10 + xDifference;
		double yDistance = thrust * yNormalized * deltaTimeMS / 10 + yDifference;
		
		logger.log(Level.FINEST, "Normalized Distance (" + xDistance + " , " + yDistance + ")");
		
		xDifference = xDistance - (long) xDistance;
		yDifference = yDistance - (long) yDistance;
		
		logger.log(Level.FINEST, "Difference (" + xDifference + " , " + yDifference + ")");
		
		long xNew = (long) xDistance + x;
		long yNew = (long) yDistance + y;
		logger.log(Level.FINEST, "New (" + xNew + "," + yNew +")");
		
		logger.log(Level.FINEST, "Distance "+ Math.abs(xDistance) + " > " + Math.abs(xMoved));
		logger.log(Level.FINEST, "Distance "+ Math.abs(yDistance) + " > " + Math.abs(yMoved));
		
		if( Math.abs(xDistance) > Math.abs(xMoved))
		{
			logger.log(Level.FINEST, "Set X to destination (" + xDestination + ")");
			xDifference = 0;
			ship.setX(xDestination);
		}
		else
		{
			logger.log(Level.FINEST, "Set X (" + xNew + ")");
			ship.setX(xNew);
		}
		
		if( Math.abs(yDistance) > Math.abs(yMoved))
		{
			logger.log(Level.FINEST, "Set Y to destination (" + yDestination + ")");
			yDifference = 0;
			ship.setY(yDestination);
		}
		else
		{
			logger.log(Level.FINEST, "Set Y (" + yNew + ")");
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

	@Override
	public void setDataContainer(GameDataContainer container)
	{
		// TODO Auto-generated method stub
	}

	

}
