package persistence.table.entity;

import java.util.HashMap;

public class Tables
{
	protected static HashMap<Integer , User> users = new HashMap<>();
	protected static HashMap<Integer , Ship> ships = new HashMap<>();
	protected static HashMap<Integer , Part> parts = new HashMap<>();
	protected static HashMap<Integer , PartGenerator> partGenerators = new HashMap<>();
	protected static HashMap<Integer , PartStorage> partStorages = new HashMap<>();

	public static HashMap<Integer, User> getUsers()
	{
		return users;
	}

	public static void setUsers(HashMap<Integer, User> users)
	{
		Tables.users = users;
	}

	public static HashMap<Integer, Ship> getShips()
	{
		return ships;
	}

	public static void setShips(HashMap<Integer, Ship> ships)
	{
		Tables.ships = ships;
	}

	
	
	
}
