package persistence.table.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class User
{
	
	protected int id;
	protected String name;
	protected String password;
	protected float increment;
	protected Set<Ship> ships;
	
	public User()
	{
		
	}
	
	public User(String name)
	{
		this.name = name;
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public float getIncrement()
	{
		return increment;
	}

	public void setIncrement(float increment)
	{
		this.increment = increment;
	}

	public Set<Ship> getShips()
	{
		return ships;
	}

	public void setShips(Set<Ship> ships)
	{
		this.ships = ships;
	}
}
