package de.tum.queries.KnappsteSieger;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class KnappsteSieger {

	private List<KnappsteSiegerTeilnehmer> gewinner = new ArrayList<KnappsteSiegerTeilnehmer>();
	private List<KnappsteSiegerTeilnehmer> verlierer = new ArrayList<KnappsteSiegerTeilnehmer>();

	public List<KnappsteSiegerTeilnehmer> getGewinner() {
		return gewinner;
	}

	public void setGewinner(List<KnappsteSiegerTeilnehmer> gewinner) {
		this.gewinner = gewinner;
	}

	public List<KnappsteSiegerTeilnehmer> getVerlierer() {
		return verlierer;
	}

	public void setVerlierer(List<KnappsteSiegerTeilnehmer> verlierer) {
		this.verlierer = verlierer;
	}
}
