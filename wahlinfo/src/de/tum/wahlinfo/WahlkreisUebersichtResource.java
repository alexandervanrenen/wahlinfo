package de.tum.wahlinfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/wahlkreisuebersicht")
public class WahlkreisUebersichtResource {
	
	WahlkreisUebersichtDAO dao = new WahlkreisUebersichtDAO();
	
	@GET @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public WahlkreisUebersicht findById(@PathParam("id") String id) {
		System.out.println("WahlkreisUebersichtResource::findById(): " + id);
		return dao.findById(Integer.parseInt(id));
	}
}
