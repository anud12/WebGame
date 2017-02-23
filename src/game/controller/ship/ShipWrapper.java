package game.controller.ship;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import game.controller.IAction;
import persistence.table.entity.Part;
import persistence.table.entity.Ship;

public class ShipWrapper
{
	protected Ship ship;
	protected HashMap<String, HashSet<IAction<ShipWrapper>>> dependency;
	protected boolean energyChanged;
	
	public ShipWrapper(Ship ship)
	{
		this.ship = ship;
		dependency = new HashMap<>();
		dependency.put("energy", new HashSet<>());
		dependency.put("value", new HashSet<>());
		dependency.put("location", new HashSet<>());
	}
	
	public int getId()
	{
		return ship.getId();
	}
	
	public long getEnergy()
	{
		return ship.getEnergy();
	}

	public void setEnergy(long energy)
	{
		ship.setEnergy(energy);
		energyChanged = true;
	}
	
	public void subscribeEnergy(IAction<ShipWrapper> action)
	{
		dependency.get("energy").add(action);
	}
	public void unSubscribeEnergy(IAction<ShipWrapper> action)
	{
		dependency.get("energy").remove(action);
	}
	
	public List<Part> getPartList()
	{
		return ship.getPartList();
	}
	
	public void setPartList(List<Part> partList)
	{
		ship.setPartList(partList);
	}

	public long getX()
	{
		return ship.getX();
	}
	
	public void setX(long x)
	{
		ship.setX(x);
	}

	public long getY()
	{
		return ship.getY();
	}

	public void setY(long y)
	{
		ship.setY(y);
	}

	public void subscribeLocation(IAction<ShipWrapper> action)
	{
		dependency.get("location").add(action);
	}
	public void unSubscribeLocation(IAction<ShipWrapper> action)
	{
		dependency.get("location").remove(action);
	}
	
	public double getRate()
	{
		return ship.getRate();
	}

	public void setRate(double rate)
	{
		ship.setRate(rate);
	}

	public long getArea()
	{
		return ship.getArea();
	}

	public void setArea(long area)
	{
		ship.setArea(area);
	}
	
	public double getThrust()
	{
		return ship.getThrust();
	}

	public void setThrust(double thrust)
	{
		ship.setThrust(thrust);
	}
	
	public void notifySubscribers()
	{
		if(energyChanged)
		{
			energyChanged = false;
			HashSet<IAction<ShipWrapper>> energyDependencyClone = new HashSet<>();
			energyDependencyClone.addAll(dependency.get("energy"));
			dependency.get("energy").clear();
			
			Iterator<IAction<ShipWrapper>> iterator = energyDependencyClone.iterator();
			
			while(iterator.hasNext())
			{
				IAction<ShipWrapper> action = iterator.next();
				action.subscriptionNotice(this);
			}
		}
	}
}
