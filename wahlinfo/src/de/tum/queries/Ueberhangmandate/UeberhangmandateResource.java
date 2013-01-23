package de.tum.queries.Ueberhangmandate;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/ueberhangmandate")
public class UeberhangmandateResource {
	
	UeberhangmandateDAO dao = new UeberhangmandateDAO();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Ueberhangmandate> findAll() {
		System.out.println("UeberhangmandateResource::findAll()");
		return dao.findAll();
	}
}
