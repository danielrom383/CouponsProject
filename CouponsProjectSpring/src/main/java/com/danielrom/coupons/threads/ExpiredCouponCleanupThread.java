package com.danielrom.coupons.threads;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.danielrom.coupons.logic.CouponController;

public class ExpiredCouponCleanupThread {
	
	// 86,400 is the amount of seconds in a single day, multiplied by 1000 because the system works in milliseconds
	final static int SINGLE_DAY_IN_MILLISECONDS = 86400 * 1000;

	public ExpiredCouponCleanupThread () {
		
		
		Timer timer = new Timer();

		//Creates a new TimerTask to be executed regularly
		TimerTask regularCouponCleanup = new TimerTask() {
			
			CouponController couponController = new CouponController();

			@Override
			public void run () {
				
				try {
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		};

		// Getting the hour that the thread should run at in date format
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 00);
		today.set(Calendar.MINUTE, 00);
		today.set(Calendar.SECOND, 00);

		// How long should the thread wait until it executes the cleanup again
		long period = SINGLE_DAY_IN_MILLISECONDS;

		timer.schedule(regularCouponCleanup, today.getTime(), period);
	}


}
