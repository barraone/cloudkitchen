package com.cloudkitchens;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * core logic how to manage order inventory
 *
 */
public class Shelves {

	static final public int __capacity = 2;
	static final public Shelves shelves = new Shelves();

	final ConcurrentMap<Temp,Shelf> _shelves = new ConcurrentHashMap<>();
	
	public Shelves() {
		_shelves.put(Temp.HOT, new Shelf(Temp.HOT, __capacity));
		_shelves.put(Temp.COLD, new Shelf(Temp.COLD, __capacity));
		_shelves.put(Temp.FROZEN, new Shelf(Temp.FROZEN, __capacity));
		_shelves.put(Temp.ANY, new Shelf(Temp.ANY, 2));
	}
	
	@Override
	public String toString() {
		return _shelves.get(Temp.HOT).toString() + _shelves.get(Temp.COLD).toString() + _shelves.get(Temp.FROZEN).toString() + _shelves.get(Temp.ANY);
	}
	
	public void place(Order order) throws InterruptedException {
	
		final Temp orderTemp = order.getTemp();
		final BlockingQueue<Order> queue = _shelves.get(orderTemp).getQueue();
		final BlockingQueue<Order> anyQueue = _shelves.get(Temp.ANY).getQueue();
		if (queue.offer(order)) {
			Logger.getAnonymousLogger().fine("Order placed " + order + " to " + orderTemp + " shelf");
		} else if (anyQueue.offer(order)) {
			Logger.getAnonymousLogger().fine("Order placed " + order + " to ANY shelf");
		} else {
			move(orderTemp);
			// remove randomly or throws an exception if queue was empty
			synchronized(_shelves) {
				final Order waste = anyQueue.remove();
				Logger.getAnonymousLogger().warning("Wasted " + waste);
				
				anyQueue.put(order);
			}
		}
		Logger.getAnonymousLogger().info("Order placed " + order.toString() + "\n" + shelves.toString());
	}
	
	public Order deliver(Order order) throws InterruptedException {
		
		final BlockingQueue<Order> queue = _shelves.get(order.getTemp()).getQueue();
		final BlockingQueue<Order> anyQueue = _shelves.get(Temp.ANY).getQueue();
		if (anyQueue.remove(order)) {
			Logger.getAnonymousLogger().info("Order delivered " + order.toString() + "\n" + shelves);
			return order;
		} else if (queue.remove(order)) {
			Logger.getAnonymousLogger().info("Order delivered " + order.toString() + "\n" + shelves);
			this.move(order.getTemp());
			return order;
		} else {
			Logger.getAnonymousLogger().warning("This order has already been wasted " + order);
			return null;
		}
	}
	
	private void move(Temp temp) {
		final BlockingQueue<Order> queue = _shelves.get(temp).getQueue();
		final BlockingQueue<Order> anyQueue = _shelves.get(Temp.ANY).getQueue();
		
		final Consumer<Order> consumer = new Consumer<Order>() {
			@Override
			public void accept(Order tt) {
				synchronized (_shelves) {
					if (queue.offer(tt)) {
						anyQueue.remove(tt);
						Logger.getAnonymousLogger().warning("Moved " + tt + " to " + tt.getTemp());
					}
				}
			}
		};
		anyQueue.stream().filter(xx -> temp == xx.getTemp()).forEach(consumer);
	}

}
