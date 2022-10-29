package main;

import main.multi.threaded.data.load.generator.Consumer;
import main.multi.threaded.data.load.generator.Producer;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This initiates the process of data load generation in Couchbase.
 * 
 * @author abhijeetbehera
 */
public class LaunchLoadGenerator {

	public static void main(String[] args) {

		BlockingQueue<String> sharedTasksQueue = new LinkedBlockingQueue<String>();

		// Create number of task producer threads
		Thread[] taskProducer = new Thread[32];
		Arrays.stream(taskProducer).forEach(p -> {
			p = new Thread(new Producer(sharedTasksQueue));
			p.setName("TASK PRODUCER THREAD " + p);
			p.start();
        });

		// Create number of task consumer threads
		Thread[] taskConsumer = new Thread[32];// amount of threads
		Arrays.stream(taskConsumer).forEach(c -> {
			c = new Thread(new Consumer(sharedTasksQueue));
			c.setName("TASK CONSUMER THREAD " + c);
			c.start();
        });
	}
}
