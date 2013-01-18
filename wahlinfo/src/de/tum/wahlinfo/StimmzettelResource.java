package de.tum.wahlinfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/stimmzettel")
public class StimmzettelResource {

	StimmzettelDAO dao = new StimmzettelDAO();

	@GET
	@Path("{wahlkreisId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Stimmzettel findAll(@PathParam("wahlkreisId") String wahlkreisId) {
		System.out.println("StimmzettelResource::findAll() id: " + wahlkreisId);
		return dao.create(Integer.parseInt(wahlkreisId));
	}
}
