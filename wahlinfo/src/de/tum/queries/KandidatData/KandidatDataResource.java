package de.tum.queries.KandidatData;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/kandidaten")
public class KandidatDataResource {

	KandidatDataDAO dao = new KandidatDataDAO();

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<KandidatExtended> findAllKandidaten() {
		System.out.println("KandidatExtended::findAllKandidaten()");
		return dao.findAllKandidaten();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public KandidatExtended findKandidatById(@PathParam("id") String kandidatId) {
		System.out.println("KandidatDataResource::findKandidatById: " + kandidatId);
		return dao.findKandidatById(Integer.parseInt(kandidatId));
	}

	@GET
	@Path("search/{kandidatName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<KandidatExtended> findBundeslandByName(@PathParam("kandidatName") String kandidatName) {
		System.out.println("KandidatDataResource::findBundeslandByName: " + kandidatName);
		return dao.findKandidatByName(kandidatName);
	}
}
