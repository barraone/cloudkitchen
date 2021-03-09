package com.cloudkitchens;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

/**
 * 
 * Simulates courier picking orders randomly 2-6 seconds
 *
 */
public class Delivery implements Callable<Order> {

	final private Shelves _shelves;
	final private Order _order;
	
	public Delivery(Order Order) {
		this(Order, Shelves.shelves);
	}
	
	public Delivery(Order order, Shelves shelves) {
		_order = order;
		_shelves = shelves;
	}

	@Override
	public Order call() throws Exception {
		
		// wait randomly for 2-6 seconds
		final double wait = 2000L + 4000 * Math.random();
		Thread.sleep((long)wait);
		
		final Order order = _shelves.deliver(_order);
		Logger.getAnonymousLogger().fine("Delivered " + order + " after " + ((long)wait) + " wait");
		return order;
	}

}
