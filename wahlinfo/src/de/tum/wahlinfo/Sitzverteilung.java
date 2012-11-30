package de.tum.wahlinfo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sitzverteilung {
	private String name;
	private int sitze;
	private String farbe;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSitze() {
		return sitze;
	}
	public void setSitze(int sitze) {
		this.sitze = sitze;
	}
	public String getFarbe() {
		return farbe;
	}
	public void setFarbe(String farbe) {
		this.farbe = farbe;
	}
}