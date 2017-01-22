package persistence.table.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import game.controller.action.IAction;

public class Ship
{
	//Persisted data
	protected int id;
	protected String name;
	protected int userId;
	protected long energy;
	protected List<Part> partList;
	
	protected double x;
	protected double y;
	
	//Calculated data
	protected double rate;
	protected long area;
	
	
	public void calculateProperties()
	{
		rate = 0;
		area = 0;
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

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
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
	
}
