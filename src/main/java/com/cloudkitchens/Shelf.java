package com.cloudkitchens;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Shelf {

	final private Temp _temp;
	final private BlockingDeque<Order> _queue;
	
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		_queue.stream().forEach(order->sb.append("\n\t").append(order.getName()));
		return _temp.toString() + sb.toString() + "\n";
	}
	public Shelf(Temp temp, int capacity) {
		_temp = temp;
		_queue = new LinkedBlockingDeque<>(capacity);
	}

	public Temp getTemp() {
		return _temp;
	}

	public BlockingDeque<Order> getQueue() {
		return _queue;
	}
	
}
