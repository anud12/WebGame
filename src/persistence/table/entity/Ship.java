package persistence.table.entity;

import java.util.Iterator;
import java.util.List;

public class Ship
{
	//Persisted data
	protected int id;
	protected String name;
	protected int userId;
	protected double value;
	protected List<Part> partList;
	
	protected double x;
	protected double y;
	
	//Calculated data
	protected double rate;
	protected double area;
	
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

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}

	public List<Part> getPartList()
	{
		return partList;
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

	public void setPartList(List<Part> partList)
	{
		this.partList = partList;
		
		Iterator<Part> iterator = this.partList.iterator();
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

	public double getRate()
	{
		return rate;
	}

	public void setRate(double rate)
	{
		this.rate = rate;
	}

	public double getArea()
	{
		return area;
	}

	public void setArea(double area)
	{
		this.area = area;
	}
	
	

	
	
}
