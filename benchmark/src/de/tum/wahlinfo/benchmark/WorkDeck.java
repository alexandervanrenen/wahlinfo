package de.tum.wahlinfo.benchmark;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class WorkDeck {

	private List<WorkItem> workItems = new ArrayList<WorkItem>();
	private Random ranny;

	public void add(WorkItem workItem) {
		workItems.add(workItem);
	}

	public void normalize() {
		// Increase the count of all work items until they are all integers
		boolean isNormalized = false;
		while (!isNormalized) {
			isNormalized = true;
			for (WorkItem iter : workItems) {
				iter.increaseCount();
				if (iter.getCount() != (int) iter.getCount())
					isNormalized = false;
			}
		}
	}

	public Queue<String> createWorkQueue() {
		// Get seed for this list
		int seed;
		synchronized (ranny) {
			seed = ranny.nextInt();
		}

		// Fill list
		Random localRanny = new Random(seed);
		LinkedList<String> result = new LinkedList<String>();
		for (WorkItem item : workItems)
			for (int i = 0; i < item.getCount(); i++)
				result.add(localRanny.nextInt(result.size() + 1), item.getUrl());
		return result;
	}

	public void setSeed(int nextInt) {
		ranny = new Random(nextInt);
	}
}
