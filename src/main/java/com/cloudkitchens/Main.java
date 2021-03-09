package com.cloudkitchens;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
	
	static final public ExecutorService __es = Executors.newCachedThreadPool();
	
	static public void main(String[] args) throws Exception {
		
		final OrderDispatcher od = new OrderDispatcher(Shelves.shelves);
		final Future<String> future = __es.submit(od);
		future.get();
		__es.shutdown();
	}

}
