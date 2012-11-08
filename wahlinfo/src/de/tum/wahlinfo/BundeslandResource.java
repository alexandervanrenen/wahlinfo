package de.tum.wahlinfo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/bundeslaender")
public class BundeslandResource {
	
	BundeslandDAO dao = new BundeslandDAO();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Bundesland> findAll() {
		System.out.println("findAll");
		return dao.findAll();
//		Bundesland bundesland = new Bundesland();
//		bundesland.setId(1);
//		bundesland.setName("ü");
//		List<Bundesland> bundeslaender = new ArrayList<Bundesland>();
//		bundeslaender.add(bundesland);
//		return bundeslaender;
	}
	
	@GET @Path("search/{query}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Bundesland> findByName(@PathParam("query") String query) {
		// TODO throw a WebApplicationException for invalid parameters
		System.out.println("findByName: " + query);
		return dao.findbyName(query);
	}

	@GET @Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Bundesland findById(@PathParam("id") String id) {
		System.out.println("findById: " + id);
		return dao.findbyId(Integer.parseInt(id));
	}
	
}
