package de.tum.wahlinfo.benchmark;

import java.util.Random;

/**
 * Supplies functionality to wait during the queries
 */
public class Timing {

	private float minWaitTime;
	private float maxWaitTime;
	private Random ranny;

	public Timing(int timeFactor, int seed) {
		minWaitTime = 0.8f * timeFactor;
		maxWaitTime = 1.2f * timeFactor;
		ranny = new Random(seed);
	}

	/**
	 * in milliseconds
	 */
	public int getWaitInterval() {
		return (int) (ranny.nextFloat() * (maxWaitTime - minWaitTime) + minWaitTime); 
	}
}
