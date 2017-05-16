package game.controller.ship;

import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import game.collection.GameDataContainer;
import game.controller.AQueuedAction;

public class MoveActionQueue extends AQueuedAction<ShipWrapper>
{
	protected double xDifference;
	protected double yDifference;
	
	@Override
	public void subscriptionNotice(ShipWrapper object)
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

	@Override
	protected void executeArgument(ShipWrapper ship, int deltaTime, LinkedList<Map<String, Object>> argumentList)
	{
		if(argumentList.isEmpty())
		{
			return;
		}
		
		Map<String, Object> arguments = argumentList.getFirst();
		
		Long xDestination = Long.parseLong((String) arguments.get("x")) * 100;
		Long yDestination = Long.parseLong((String) arguments.get("y")) * 100;
		
		Logger logger = Logger.getLogger("Ship " + ship.getId());
		
		long x = ship.getX();
		long y = ship.getY();
		
		if(xDestination == x && yDestination == y)
		{
			argumentList.remove();
			return;
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
		
		double xDistance = thrust * xNormalized * deltaTime / 10 + xDifference;
		double yDistance = thrust * yNormalized * deltaTime / 10 + yDifference;
		
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
		
		return;
	}

}
