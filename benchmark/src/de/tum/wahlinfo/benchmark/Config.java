package de.tum.wahlinfo.benchmark;

import java.util.Random;

public class Config {

	private int terminalCount = -1;
	private int waitTime = -1;
	private int reportTime = -1;
	private int seed = 846513456;

	private WorkDeck workdeck = new WorkDeck();
	private Random ranny;

	public Config(String[] args) {
		// Parse arguments
		for (int i = 0; i < args.length; i++) {
			// Terminals
			if (args[i].contentEquals("--terminals") || args[i].contentEquals("--terminal") || args[i].contentEquals("-t")) {
				terminalCount = Integer.parseInt(args[++i]);
				continue;
			}
			// Wait time
			if (args[i].contentEquals("--waitTime") || args[i].contentEquals("--wait") || args[i].contentEquals("-w")) {
				waitTime = Integer.parseInt(args[++i]);
				continue;
			}
			// Report time
			if (args[i].contentEquals("--reportTime") || args[i].contentEquals("--report") || args[i].contentEquals("-r")) {
				reportTime = Integer.parseInt(args[++i]);
				continue;
			}
			// Seed
			if (args[i].contentEquals("--seed") || args[i].contentEquals("-s")) {
				seed = Integer.parseInt(args[++i]);
				continue;
			}
			// Workload
			String uri = args[i];
			float probability = Float.parseFloat(args[++i]);
			if (!(0.0f <= probability && probability <= 1.0f))
				throw new RuntimeException("transaction prbability has to be between 0.0 and 1.0");
			workdeck.add(new WorkItem(uri, probability));
		}

		// Check if everything is specified
		if (terminalCount <= 0)
			throw new RuntimeException("--terminals: terminal count is unspecified or an invalid value");
		if (reportTime <= 0)
			throw new RuntimeException("--report: report time is unspecified or an invalid value");
		if (waitTime <= 0)
			throw new RuntimeException("--wait: wait time is unspecified or an invalid value");

		// Use random to produce different waiting times for each terminal
		ranny = new Random(seed);

		// Normalize workload
		workdeck.normalize();
		workdeck.setSeed(ranny.nextInt());
	}

	public int getTerminalCount() {
		return terminalCount;
	}

	public Timing createTiming() {
		return new Timing(waitTime, ranny.nextInt());
	}

	public int getReportTime() {
		return reportTime;
	}

	public WorkDeck getWorkDeck() {
		return workdeck;
	}
}
