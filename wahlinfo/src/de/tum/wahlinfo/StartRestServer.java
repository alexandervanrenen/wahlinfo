package de.tum.wahlinfo;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

public class StartRestServer {
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws IllegalArgumentException, IOException {

		/*
		 * If eclipse displays an error in the following line, adjust the project preferences:
		 * Project Properties -> Java Compiler -> Errors/Warnings -> Deprecated and restricted API
		 * -> Forbidden reference -> Ignore
		 */
		HttpServer server = HttpServerFactory.create("http://localhost:8080/wahlinfo");
		server.start();
		JOptionPane.showMessageDialog(null, "Press OK to stop the REST server.");
		server.stop(0);
		
	}

}
