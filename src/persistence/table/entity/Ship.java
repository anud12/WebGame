package persistence.table.entity;

import java.util.Iterator;
import java.util.List;

public class Ship extends GameObject
{
	//Persisted data
	protected String name;
	protected int userId;
	protected long energy;
	protected List<Part> partList;
		
	//Calculated data
	protected double rate;
	protected long area;
	protected double thrust;
	protected double sensorRadius;
	
	public void calculateProperties()
	{
		rate = 0;
		area = 0;
		thrust = 0;
		sensorRadius = 0;
		Iterator<Part> iterator = partList.iterator();
		while(iterator.hasNext())
		{
			Part part = iterator.next();
			
			if(part.getClass() == PartGenerator.class)
			{
				this.rate += ((PartGenerator) part).getRate();
			}
			if(part.getClass() == PartStorage.class)
			{
				this.area += ((PartStorage) part).area;
			}
			if(part.getClass() == PartEngine.class)
			{
				this.thrust += ((PartEngine) part).thrust;
			}
			if(part.getClass() == PartSensor.class)
			{
				this.sensorRadius += ((PartSensor) part).radius;
			}
		}
		
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public long getEnergy()
	{
		return energy;
	}

	public void setEnergy(long energy)
	{
		this.energy = energy;
	}

	public List<Part> getPartList()
	{
		return partList;
	}
	
	public void setPartList(List<Part> partList)
	{
		this.partList = partList;
	}

	public double getRate()
	{
		return rate;
	}

	public void setRate(double rate)
	{
		this.rate = rate;
	}

	public long getArea()
	{
		return area;
	}

	public void setArea(long area)
	{
		this.area = area;
	}

	public double getThrust()
	{
		return thrust;
	}

	public void setThrust(double thrust)
	{
		this.thrust = thrust;
	}

	public double getSensorRadius()
	{
		return sensorRadius;
	}

	public void setSensorRadius(double sensorRadius)
	{
		this.sensorRadius = sensorRadius;
	}
	
	
}
