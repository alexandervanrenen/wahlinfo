package de.tum.queries.GeographicData;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



@Path("/germany")
public class StaticDataResource {

	StaticDataDAO dao = new StaticDataDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Deutschland getGermany() {
		System.out.println("StaticDataResource::getGermany()");
		return dao.getDeutschland();
	}

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<BundeslandExtended> findAllBundeslaender() {
		System.out.println("StaticDataResource::findAllBundeslaender()");
		return dao.findAllBundeslaender();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public BundeslandExtended findBundeslandById(@PathParam("id") String id) {
		System.out.println("StaticDataResource::findBundeslandById: " + id);
		return dao.findBundeslandById(Integer.parseInt(id));
	}

	@GET
	@Path("search/{bundeslandName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<BundeslandExtended> findBundeslandByName(@PathParam("bundeslandName") String bundeslandName) {
		System.out.println("StaticDataResource::findBundeslandByName: " + bundeslandName);
		return dao.findBundeslandByName(bundeslandName);
	}

	@GET
	@Path("all/all")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<WahlkreisExtended> findAllWahlkreise() {
		System.out.println("StaticDataResource::findAllWahlkreise");
		return dao.findAllWahlkreise();
	}

	@GET
	@Path("{bundeslandId}/all")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<WahlkreisExtended> findAllWahlkreiseInBundesland(@PathParam("bundeslandId") String bundeslandId) {
		System.out.println("StaticDataResource::findAllWahlkreise: " + bundeslandId);
		return dao.findAllWahlkreiseOfBundesland(Integer.parseInt(bundeslandId));
	}

	@GET
	@Path("all/{wahlkreisId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public WahlkreisExtended findWahlkreisById(@PathParam("wahlkreisId") String wahlkreisId) {
		System.out.println("StaticDataResource::findWahlkreisById: " + wahlkreisId);
		return dao.findWahlkreisById(Integer.parseInt(wahlkreisId));
	}

	@GET
	@Path("all/search/{wahlkreisName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<WahlkreisExtended> findWahlkreisByName(@PathParam("wahlkreisName") String wahlkreisName) {
		System.out.println("StaticDataResource::findWahlkreisByName: " + wahlkreisName);
		return dao.findWahlkreisByName(wahlkreisName);
	}
}
