package de.tum.wahlinfo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Stimmzettel {

	private List<Kandidat> kandidatenList;

	private List<Partei> parteienList;
	
	private int wahlkreisId;

	public List<Kandidat> getKandidatenList() {
		return kandidatenList;
	}

	public void setKandidatenList(List<Kandidat> kandidatenList) {
		this.kandidatenList = kandidatenList;
	}

	public List<Partei> getParteienList() {
		return parteienList;
	}

	public void setParteienList(List<Partei> list) {
		this.parteienList = list;
	}

	public int getWahlkreisId() {
		return wahlkreisId;
	}

	public void setWahlkreisId(int wahlkreisId) {
		this.wahlkreisId = wahlkreisId;
	}
}
