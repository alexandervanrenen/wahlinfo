package de.tum.queries.WahlkreisSieger;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/wahlkreissieger")
public class WahlkreisSiegerResource {

	WahlkreissiegerDAO dao = new WahlkreissiegerDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<WahlkreisSieger> findAll() {
		System.out.println("WahlkreisSiegerResource::findAll()");
		return dao.get();
	}
}
