package de.tum.queries.ParteiData;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/parteien")
public class ParteiDataResource {

	ParteiDataDAO dao = new ParteiDataDAO();

	@GET
	@Path("all")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ParteiExtended> findAllParteien() {
		System.out.println("ParteiDataResource::findAllParteien()");
		return dao.findAllParteien();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ParteiExtended findParteiById(@PathParam("id") String parteiId) {
		System.out.println("ParteiDataResource::findParteienById: " + parteiId);
		return dao.findParteiById(Integer.parseInt(parteiId));
	}

	@GET
	@Path("search/{parteiName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ParteiExtended> findParteiByName(@PathParam("parteiName") String parteiName) {
		System.out.println("ParteiDataResource::findParteienByName: " + parteiName);
		return dao.findParteiByName(parteiName);
	}
}
