package game;


public class Loop implements Runnable
{
	//Time measurements
    protected long time;
    protected double deltaTime;
    protected double minimumDeltaTime;
    
    //Context
    
	private GameContext context;
    //Options
  	private boolean isPause;
  	
	public Loop()
	{
		
	}
	
	
	
	public synchronized long getTime()
	{
		return time;
	}



	public synchronized void setTime(long time)
	{
		this.time = time;
	}



	public synchronized double getDeltaTime()
	{
		return deltaTime;
	}



	public synchronized void setDeltaTime(double deltaTime)
	{
		this.deltaTime = deltaTime;
	}



	public synchronized double getMinimumDeltaTime()
	{
		return minimumDeltaTime;
	}



	public synchronized void setMinimumDeltaTime(double minimumDeltaTime)
	{
		this.minimumDeltaTime = minimumDeltaTime;
	}



	public synchronized boolean isPause()
	{
		return isPause;
	}



	public synchronized void setPause(boolean isPause)
	{
		this.isPause = isPause;
	}
	
	
	
	public GameContext getContext()
	{
		return context;
	}



	public void setContext(GameContext context)
	{
		this.context = context;
	}



	public void init()
	{
		Thread thread = new Thread(this);
		thread.start();
	}
	
	protected synchronized void update()
	{
		//Update time to current
    	time = System.nanoTime();
    	
    	//Get the time between frames
    	deltaTime = ( System.nanoTime() - time ) / 1000000.0f;
    	System.out.println(deltaTime);
    	
    	//Check the frequency of the calls
    	if(deltaTime < minimumDeltaTime)
    	{
    		try
    		{
    			boolean useNano = false;
    			
    			int sleepTime =  (int) (minimumDeltaTime - deltaTime);
    			deltaTime = minimumDeltaTime;
    			
    			if(sleepTime < 0)
    			{
    				useNano = true;
    				sleepTime = (int) (minimumDeltaTime * 1000000 - deltaTime * 1000000);
    			}
    			System.out.println("Sleep time " + sleepTime);
    			//Wait until the minimum time
    			if(useNano)
    				Thread.sleep(0, sleepTime);
    			Thread.sleep((long) (sleepTime));
    		}
    		catch(Exception e)
    		{
    		}
    	}
    	
    	if(isPause)
    	{
    		deltaTime = 0;
    	}
    	else
    	{
    		deltaTime = ( System.nanoTime() - time ) / 1000000.0f;
    	}
    	
    	context.update(deltaTime);
	}



	@Override
	public void run()
	{
		while(true)
		{
			update();
		}
	}
	
	
}
