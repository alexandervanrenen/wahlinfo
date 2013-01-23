package de.tum.queries.KnappsteSieger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/knappstesieger")
public class KnappsteSiegerResource {

	KnappsteSiegerDAO dao = new KnappsteSiegerDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public KnappsteSieger get() {
		System.out.println("WahlkreisUebersichtResource::get()");
		return dao.get();
	}
}
