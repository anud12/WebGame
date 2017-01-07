package game;

import java.util.concurrent.Callable;


public interface FrameIteration extends Callable
{
	public void setDeltaTime(double deltaTime);
}
