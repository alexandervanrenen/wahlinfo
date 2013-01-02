package de.tum.wahlinfo.benchmark;

import java.util.ArrayList;
import java.util.List;

public class Controller {

	private List<Terminal> terminals = new ArrayList<Terminal>();
	private Performance performance;

	public Controller(String[] args) {
		// Parse
		Config config = new Config(args);

		// Count performance
		performance = new Performance(config.getReportTime());

		// Set up terminals
		for (int i = 0; i < config.getTerminalCount(); i++)
			terminals.add(new Terminal(config.getWorkDeck(), config.createTiming(), performance));
	}

	public void run() {
		// Print performance periodically
		performance.start();

		// Run terminals and wait till they are done
		for (Terminal iter : terminals)
			iter.start();
	}
}
