package persistence.table.entity;

import java.util.List;

public class World
{
	protected int id;
	protected List <Ship> shipList;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public List<Ship> getShipList()
	{
		return shipList;
	}
	public void setShipList(List<Ship> shipList)
	{
		this.shipList = shipList;
	}
	
	
	
}
