package de.tum.wahlinfo;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sitzverteilung")
public class SitzverteilungResource {

	SitzverteilungDAO dao = new SitzverteilungDAO();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Sitzverteilung> getSitzverteilung() {
		System.out.println("SitzverteilungResource::getSitzverteilung");
		// return dao.findAll();
		Sitzverteilung sitzverteilung = new Sitzverteilung();
		sitzverteilung.setName("CDU");
		sitzverteilung.setSitze(239);
		sitzverteilung.setFarbe("000000");
		List<Sitzverteilung> sitzverteilungsListe = new ArrayList<Sitzverteilung>();
		sitzverteilungsListe.add(sitzverteilung);
		return sitzverteilungsListe;
	}
}
