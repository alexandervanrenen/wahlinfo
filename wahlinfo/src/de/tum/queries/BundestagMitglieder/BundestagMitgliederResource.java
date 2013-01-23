package de.tum.queries.BundestagMitglieder;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/bundestagmitglieder")
public class BundestagMitgliederResource {

	BundestagMitgliederDAO dao = new BundestagMitgliederDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<BundestagMitglied> findAll() {
		System.out.println("BundestagMitgliederResource::findAll()");
		return dao.findAll();
	}
}
