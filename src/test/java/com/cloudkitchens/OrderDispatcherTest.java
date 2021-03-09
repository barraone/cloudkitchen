package com.cloudkitchens;

import java.util.logging.Logger;

import org.junit.Test;

public class OrderDispatcherTest {
	
	@Test
	public void test1() throws Exception {
		
		final OrderDispatcher od = new OrderDispatcher(Shelves.shelves);
		Logger.getAnonymousLogger().info(od.dispatch().toString());
		Logger.getAnonymousLogger().info(od.dispatch().toString());
		Logger.getAnonymousLogger().info(od.dispatch().toString());
		Logger.getAnonymousLogger().info(od.dispatch().toString());
	}
}
