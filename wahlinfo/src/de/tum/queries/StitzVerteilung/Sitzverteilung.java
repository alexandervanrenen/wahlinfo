package de.tum.queries.StitzVerteilung;

import javax.xml.bind.annotation.XmlRootElement;

import de.tum.domain.Partei;

@XmlRootElement
public class Sitzverteilung {

	private Partei partei;
	private int sitze;

	public Partei getPartei() {
		return partei;
	}

	public void setPartei(Partei partei) {
		this.partei = partei;
	}

	public int getSitze() {
		return sitze;
	}

	public void setSitze(int sitze) {
		this.sitze = sitze;
	}
}
