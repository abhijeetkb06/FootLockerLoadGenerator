package main.multi.threaded.data.load.generator;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;

import com.couchbase.client.java.kv.GetResult;

public class Consumer extends Thread {

	// Read data to consume once data is loaded in queue
	private BlockingQueue<String> tasksQueue;

	public Consumer(BlockingQueue<String> tasksQueue) {
		super("TASKS CONSUMER");
		this.tasksQueue = tasksQueue;
	}

	public void run() {
		try {
			while (true) {
				
				System.out.println("***************QUEUE SIZE************** "+ tasksQueue.size());

				// Remove the user from shared queue and process
				String user = tasksQueue.take();
				GetResult getResult =  CouchbaseConfiguration.usersColl.get(user);

				String name = getResult.contentAsObject().getString("name");
				System.out.println(name);

				System.out.println("TASK CONSUMED \n");
				System.out.println(" Thread Name: " + Thread.currentThread().getName());
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
