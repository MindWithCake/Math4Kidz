package com.altervista.lemaialone.math4kidz;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

/**
 * Thread to manage the timeout bar during the game.
 * Created by Ilario Sanseverino on 22/07/15.
 */
public class TimerThread extends Thread {

	private final ProgressBar bar;
	private final OnTimeout timeoutCallback;
	private int speed;
	private final Handler handler;

	public TimerThread(@NonNull ProgressBar bar, @NonNull OnTimeout timeoutCallback, int speed){
		this.bar = bar;
		this.timeoutCallback = timeoutCallback;
		this.speed = speed;
		handler = new Handler(Looper.getMainLooper());
	}

	@Override
	public void run(){
		bar.setMax(100 * 100);
		final int progressMax = bar.getMax();
		int progress = progressMax;
		updateBar(progress);
		long startTime = System.nanoTime();
		long lastUpdate = startTime;

		while(progress > 0){
			try{
				sleep(100);
				int elapsed = (int)((lastUpdate - startTime) / 10000000); // centiseconds
				lastUpdate = System.nanoTime();
				progress = progressMax - (speed * elapsed);
				updateBar(progress);
			}
			catch(InterruptedException ex){
				return;
			}
		}
		timeout();
	}

	public interface OnTimeout {
		void onTimeout();
	}

	private void updateBar(final int progress){
		handler.post(new Runnable() {
			@Override
			public void run(){
				bar.setProgress(progress);
			}
		});
	}

	private void timeout(){
		handler.post(new Runnable() {
			@Override
			public void run(){
				timeoutCallback.onTimeout();
			}
		});
	}
}
