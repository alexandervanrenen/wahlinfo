package de.tum.wahlinfo.benchmark;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Performance extends Thread {

	class Pair {
		private long count;

		public long getCount() {
			return count;
		}

		private long time;

		public long getTime() {
			return time;
		}

		public Pair(long count, long time) {
			super();
			this.count = count;
			this.time = time;
		}

		public void addTime(long neededTime) {
			count++;
			time += neededTime;
		}
	}

	private long reportTime;
	private Map<String, Pair> data = new TreeMap<String, Pair>();
	private long countInLastPeriod = 0;

	public Performance(int reportTime) {
		super();
		this.reportTime = reportTime;
	}

	public synchronized void add(String uri, long neededTime) {
		// Update specific transaction
		Pair p = data.get(uri);
		if (p == null)
			data.put(uri, new Pair(1, neededTime));
		else
			p.addTime(neededTime);

		// Increase global counters
		countInLastPeriod++;
	}

	@Override
	public void run() {
		super.run();

		while (true) {
			// Wait
			try {
				Thread.sleep(reportTime);
			} catch (InterruptedException e) {
				throw new RuntimeException(e.toString());
			}

			// Report
			synchronized (this) {
				System.out.println("---------------------");
				System.out.println("Reporting performance");
				System.out.println("executed: " + countInLastPeriod);
				countInLastPeriod = 0;
				for (Entry<String, Pair> iter : data.entrySet())
					System.out.println(iter.getKey() + " " + (iter.getValue().getTime() / 1000.0f) / iter.getValue().getCount());
				data.clear();
			}
		}
	}
}
