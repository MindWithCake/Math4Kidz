package com.altervista.lemaialone.math4kidz;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.TreeSet;

/**
 * Thread to manage the timeout bar during the game.
 * Created by Ilario Sanseverino on 22/07/15.
 */
public class TimerThread extends Thread {
	private final Handler handler;
	private TreeSet<TimeoutObject> timers;

	public TimerThread(){
		handler = new Handler(Looper.getMainLooper());
		timers = new TreeSet<>();
	}

	@Override
	public void run(){
		long lastTime = System.nanoTime();
		while(true){
			synchronized(this){
				if(timers.isEmpty()){
					try{
						wait();
						lastTime = System.nanoTime();
					}
					catch(InterruptedException e){ break; }
				}
			}
			try{ sleep(timers.first().duration / 1000000); }
			catch(InterruptedException e){ break; }

			long elapsed = (System.nanoTime() - lastTime);
			for(TimeoutObject to: timers){
				to.duration -= elapsed;
				if(to.duration < 0)
					timeout(to.timeoutCallback);
			}
			synchronized(this){
				while(!timers.isEmpty() && timers.first().duration <= 0)
					timers.pollFirst();
			}

			lastTime += elapsed;
		}
	}

	public synchronized TimeoutObject addItem(long duration, OnTimeout callback){
		TimeoutObject toAdd = new TimeoutObject(duration * 1000000, callback);
		timers.add(toAdd);
		notifyAll();
		return toAdd;
	}

	public synchronized void cancelTimeout(TimeoutObject toRemove){
		timers.remove(toRemove);
	}

	private void timeout(final OnTimeout callback){
		handler.post(new Runnable() {
			@Override
			public void run(){
				callback.onTimeout();
			}
		});
	}

	public static class TimeoutObject implements Comparable<TimeoutObject> {
		private long duration;
		private OnTimeout timeoutCallback;

		private TimeoutObject(long duration, @NonNull OnTimeout callback){
			this.duration = duration;
			this.timeoutCallback = callback;
		}

		@Override
		public int compareTo(@NonNull TimeoutObject timeoutObject){
			if(duration < timeoutObject.duration)
				return -1;
			return duration > timeoutObject.duration? 1 : 0;
		}
	}

	public interface OnTimeout {
		void onTimeout();
	}
}
