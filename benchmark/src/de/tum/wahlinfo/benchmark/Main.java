package de.tum.wahlinfo.benchmark;

public class Main {

	public static void main(String[] args) {
		// Parses the arguments and sets up everything
		Controller controller = new Controller(args);
		// Starts the terminal threads, which will fire requests to the server
		controller.run();
	}
}
