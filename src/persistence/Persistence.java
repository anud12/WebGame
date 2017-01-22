package persistence;

import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import game.collection.GameDataContainer;
import persistence.table.entity.Ship;
import persistence.table.entity.Tables;
import persistence.table.entity.User;
import persistence.table.entity.World;
import spring.Spring;
import persistence.table.entity.Part;
import persistence.table.entity.PartGenerator;
import persistence.table.entity.PartStorage;

public class Persistence
{
	protected SessionFactory sessionFactory;
	protected GameDataContainer dataContainer;
	
	
	public SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public GameDataContainer getDataContainer()
	{
		return dataContainer;
	}
	public void setDataContainer(GameDataContainer dataContainer)
	{
		this.dataContainer = dataContainer;
		
		System.out.println("Persistence :" + dataContainer);
		Session session = sessionFactory.openSession();
		Iterator<Ship> shipIterator = session.createQuery("from Ship").getResultList().iterator();
		while(shipIterator.hasNext())
		{
			Ship ship = shipIterator.next();
			dataContainer.get("Ship").put(ship.getId(), ship);
		}
		session.close();
		
		dataContainer.get("Ship").swap();
		
		session = sessionFactory.openSession();
		shipIterator = session.createQuery("from Ship").getResultList().iterator();
		while(shipIterator.hasNext())
		{
			Ship ship = shipIterator.next();
			dataContainer.get("Ship").put(ship.getId(), ship);
		}
		session.close();
	}
	
	public Persistence()
	{
		StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
		sessionFactory = null;
		
		try
		{
			sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Hibernate Initialization fail");
			StandardServiceRegistryBuilder.destroy(registry);
		}
		
		Session session = sessionFactory.openSession();
		
		Iterator<User> userIterator = session.createQuery("from User").getResultList().iterator();
		while(userIterator.hasNext())
		{
			User user = userIterator.next();
			Tables.getUsers().put(user.getId(), user);
		}
		
		Iterator<Ship> shipIterator = session.createQuery("from Ship").getResultList().iterator();
		while(shipIterator.hasNext())
		{
			Ship ship = shipIterator.next();
			Tables.getShips().put(ship.getId(), ship);
			
		}
		
		
		User user = (User) session.createQuery("from User U where U.id = '0'").getResultList().get(0);
		
		float increment = user.getIncrement();
		
		System.out.println(increment);
		
		Iterator<World> worldIterator = session.createQuery("from World").getResultList().iterator();
		
		while(worldIterator.hasNext())
		{
			World world = worldIterator.next();
			
			System.out.println("World");
			System.out.println("id :" + world.getId());
			
			Iterator<Ship> shipIiterator = world.getShipList().iterator();

			while(shipIiterator.hasNext())
			{
				Ship ship = shipIiterator.next();
				System.out.println("      ---Ship---");
				System.out.println("      Id :" + ship.getId());
				System.out.println("      Name :" + ship.getName());
				System.out.println("      Value :" + ship.getEnergy());
				System.out.println("      UserId :" + ship.getUserId());
				Iterator<Part> shipPartIterator =  ship.getPartList().iterator();
				while(shipPartIterator.hasNext())
				{
					Part part = shipPartIterator.next();
					System.out.println("            -Id :" + part.getId());
					System.out.println("            -Name :" + part.getName());
					System.out.println("            -Type :" + part.getType());
				}
				System.out.println("      ----------");
			}
		}
		
		
		Iterator<Part> partIterator = session.createQuery("from Part").getResultList().iterator();
		while(partIterator.hasNext())
		{
			Part part = partIterator.next();
			
			System.out.println("Part");
			System.out.println("Id :" + part.getId());
			System.out.println("Name :" + part.getName());
			System.out.println("Type :" + part.getType());
		}
		
		Iterator<PartGenerator> partGeneratorIterator = session.createQuery("from PartGenerator").getResultList().iterator();
		while(partGeneratorIterator.hasNext())
		{
			PartGenerator part = partGeneratorIterator.next();
			
			System.out.println("Part");
			System.out.println("Id :" + part.getId());
			System.out.println("Rate :" + part.getRate());
		}
		
		Iterator<PartStorage> partStorageIterator = session.createQuery("from PartStorage").getResultList().iterator();
		while(partStorageIterator.hasNext())
		{
			PartStorage part = partStorageIterator.next();
			
			System.out.println("Part");
			System.out.println("Id :" + part.getId());
			System.out.println("Area :" + part.getArea());
		}
		
		session.close();
		user.setIncrement(++increment);
		session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(user);
		
		session.close();
		System.out.println(this.toString() + " constructor finished");
	}
}
