package de.tum.queries.StimmZettel;

import de.tum.domain.Kandidat;
import de.tum.domain.Partei;

public class StimmzettelEntry {
	
	private Partei partei;
	private Kandidat kandidat;

	public Partei getPartei() {
		return partei;
	}

	public void setPartei(Partei partei) {
		this.partei = partei;
	}

	public Kandidat getKandidat() {
		return kandidat;
	}

	public void setKandidat(Kandidat kandidat) {
		this.kandidat = kandidat;
	}
}
