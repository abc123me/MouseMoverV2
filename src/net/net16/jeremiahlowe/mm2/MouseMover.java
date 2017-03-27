package net.net16.jeremiahlowe.mm2;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

public class MouseMover {
	private MouseMoverThread thread;
	private Robot robit;
	public MouseMover(){
		try{robit = new Robot();}
		catch(AWTException e){Utility.logFatal("Couldn't instantiate robot", e);}
		thread = new MouseMoverThread(robit);
	}
	public void start(){
		if(thread != null && thread.isAlive()) thread.cleanlyKill();
		thread = new MouseMoverThread(robit);
		thread.start();
	}
	public boolean setWaitTime(long waitTimeMS){
		return setWaitTime(waitTimeMS, true);
	}
	public boolean setWaitTime(long waitTimeMS, boolean recursion){
		if(thread != null && thread.isAlive()){
			thread.setWaitTime(waitTimeMS);
			Utility.logInfo("Set wait time to " + thread.getWaitTime() + " milliseconds");
			return true;
		}
		else{
			Utility.logError("Couldn't set wait time, Restarting thread and recalling method");
			restart();
			if(recursion) setWaitTime(waitTimeMS, false);
			return false;
		}
	}
	public boolean setAlternate(boolean alternate){
		return setAlternate(alternate, true);
	}
	public boolean setAlternate(boolean alternate, boolean recursion){
		if(thread != null && thread.isAlive()){
			thread.setAlternate(alternate);
			Utility.logInfo((thread.alternating() ? "En" : "Dis") + "abled alternation");
			return true;
		}
		else{
			Utility.logError("Couldn't enable alternation, Restarting thread and recalling method");
			restart();
			if(recursion) setAlternate(alternate, false);
			return false;
		}
	}
	public long getWaitTime() throws Exception{
		if(thread != null && thread.isAlive()) return thread.getWaitTime();
		throw new Exception("thread not running");
	}
	public boolean alternating() throws Exception{
		if(thread != null && thread.isAlive()) return thread.alternating();
		throw new Exception("thread not running");
	}
	public boolean isThreadOK(){
		if(thread != null && thread.isAlive()) return true;
		else return false;
	}
	public void restart() {
		Utility.logInfo("Restarting MouseMoverThread");
		start();
	}
	public void stop(){
		thread.notify();
		thread.interrupt();
	}
}
class MouseMoverThread extends Thread{
	private Object lock;
	private Robot robit;
	private long waitTimeMS = 1000;
	private int amount = 1;
	private boolean alternate = true;
	private boolean altStep = true;
	private boolean skipMove = false;
	private boolean stopThreadOnInterrupt = false;
	public MouseMoverThread(Robot robit){
		super();
		lock = new Object();
		setName("MouseMoverThread-" + getId());
		this.robit = robit;
	}
	public void skip(){
		skipMove = true;
		synchronized(lock){lock.notify();}
	}
	public void cleanlyKill(){
		stopThreadOnInterrupt = true;
		interrupt();
		while(isAlive()){}
		stopThreadOnInterrupt = false;
	}
	public void setWaitTime(long waitTimeMS){
		this.waitTimeMS = waitTimeMS;
		skip();
	}
	public void setAlternate(boolean alternate){
		this.alternate = alternate;
		skip();
	}
	public long getWaitTime(){return waitTimeMS;}
	public boolean alternating(){return alternate;}
	@Override
	public void run(){
		Utility.logDebug("MouseMoverThread started!");
		while(!interrupted()){
			skipMove = false;
			int mx = 0, my = 0, mxl = 0, myl = 0;
			Point mp = MouseInfo.getPointerInfo().getLocation();
			mxl = mp.x;
			myl = mp.y;
			try{synchronized(lock){lock.wait(waitTimeMS);}}
			catch(InterruptedException e){
				Utility.logDebug("MouseMoverThread wait interrupted" + (stopThreadOnInterrupt ? ", stopping aswell" : ""));
				if(stopThreadOnInterrupt) break;
			}
			if(!skipMove){
				mp = MouseInfo.getPointerInfo().getLocation();
				mx = mp.x;
				my = mp.y;
				if(mx == mxl && my == myl){
					int am = amount;
					if(alternate){
						am *= altStep ? 1 : -1;
						altStep = !altStep;
					}
					robit.mouseMove(mx + am, my + am);
					Utility.logDebug("Mouse moved " + am + " pixels");
				}
			}
			else Utility.logDebug("Skip move called");
		}
		Utility.logDebug("MouseMoverThread was cleanly killed");
	}
}