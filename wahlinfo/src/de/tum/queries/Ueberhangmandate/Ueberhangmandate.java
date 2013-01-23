package de.tum.queries.Ueberhangmandate;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ueberhangmandate {
	
	private String partei;
	private String bundesland;
	private int ueberhangmandate;

	public String getPartei() {
		return partei;
	}
	public void setPartei(String partei) {
		this.partei = partei;
	}
	public String getBundesland() {
		return bundesland;
	}
	public void setBundesland(String bundesland) {
		this.bundesland = bundesland;
	}
	public int getUeberhangmandate() {
		return ueberhangmandate;
	}
	public void setUeberhangmandate(int ueberhangmandate) {
		this.ueberhangmandate = ueberhangmandate;
	}
}
