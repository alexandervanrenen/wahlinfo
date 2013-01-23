package de.tum.queries.StimmAbgabe;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/stimmabgabe")
public class StimmAbgabeResource {

	StimmAbgabeDAO dao = new StimmAbgabeDAO();

	@GET
	@Path("{wahlkreisid}/{kandidatid}/{parteiid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public StimmAbgabe addVote(@PathParam("wahlkreisid") String wahlkreisid, @PathParam("kandidatid") String kandidatid, @PathParam("parteiid") String parteiid) {
		System.out.println("StimmAbgabeResource::addVote(): wahlkreisid: " + wahlkreisid + "kandidatid: " + kandidatid + "parteiid: " + parteiid);
		return dao.addVote(wahlkreisid, kandidatid, parteiid);
	}
}
