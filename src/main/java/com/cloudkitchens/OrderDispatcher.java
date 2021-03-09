package com.cloudkitchens;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * dispatches orders every 500 milleseconds
 *
 */
public class OrderDispatcher implements Callable<String> {

	final Queue<Order> _orders = new ConcurrentLinkedQueue<>();
	final Shelves _shelves;
	
	public OrderDispatcher(Shelves shelves) throws IOException {
		final String jsonStr = Files.readString(Paths.get("src/main/resources/com/cloudkitchens/orders.json"));
		final JSONArray arr = new JSONArray(jsonStr);
		for (int ii=0; ii<arr.length(); ++ii) {
			final JSONObject obj = arr.getJSONObject(ii);
			final Order order = new Order();
			order.setId(obj.getString("id"));
			order.setName(obj.getString("name"));
			order.setShelfLife(obj.getInt("shelfLife"));
			order.setDecayRate(obj.getFloat("decayRate"));

			switch (obj.getString("temp")) {
			case "hot": {
				order.setTemp(Temp.HOT);
				break;
			}
			case "cold": {
				order.setTemp(Temp.COLD);
				break;
			}
			case "frozen": {
				order.setTemp(Temp.FROZEN);
				break;
			}
			default:
			{
				order.setTemp(Temp.ANY);
				break;
			}
			}
			_orders.add(order);
		}
		_shelves = shelves;
	}
	
	public Order dispatch() {
		final Order order = _orders.poll();
		Logger.getAnonymousLogger().fine(order.toString());
		return order;
	}

	@Override
	public String call() throws Exception {
		
		while (! _orders.isEmpty()) {
			
			final Order order = dispatch();
			Logger.getAnonymousLogger().fine(order.toString());
			_shelves.place(order);
			
			final Delivery delivery = new Delivery(order, _shelves);
			Main.__es.submit(delivery);
			Thread.sleep(500L);
		}
		return null;
	}

}
