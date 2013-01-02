package de.tum.wahlinfo.benchmark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;

public class Terminal extends Thread {

	private WorkDeck workDeck;
	private Timing timing;
	private Performance performance;
	private Queue<String> workQueue;

	public Terminal(WorkDeck workDeck, Timing timing, Performance performance) {
		this.workDeck = workDeck;
		this.timing = timing;
		this.performance = performance;
		this.workQueue = workDeck.createWorkQueue();
	}

	@Override
	public void run() {
		super.run();

		// Just run forever
		while (true) {
			// Check if deck is empty
			if (workQueue.isEmpty())
				workQueue = workDeck.createWorkQueue();

			// Wait
			try {
				Thread.sleep(timing.getWaitInterval());
			} catch (InterruptedException e) {
				throw new RuntimeException(e.toString());
			}

			// Run request
			String uri = workQueue.poll();
			long start = System.currentTimeMillis();
			try {
				URL url = new URL(uri);
				InputStream response = url.openStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(response));
				while ((reader.readLine()) != null) {
				}
				reader.close();
			} catch (MalformedURLException e) {
				throw new RuntimeException(e.toString());
			} catch (IOException e) {
				throw new RuntimeException(e.toString());
			}

			// Report performance
			long neededTime = System.currentTimeMillis() - start;
			performance.add(uri, neededTime);
		}
	}
}
