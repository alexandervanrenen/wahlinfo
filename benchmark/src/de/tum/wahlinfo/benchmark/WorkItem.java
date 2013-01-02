package de.tum.wahlinfo.benchmark;

/**
 * Encapsulates one item in the workload mix
 */
public class WorkItem {
	private String url;
	private float count;

	public WorkItem(String url, float probability) {
		super();
		this.url = url;
		this.count = probability;
	}

	public String getUrl() {
		return url;
	}

	public float getCount() {
		return count;
	}

	public void increaseCount() {
		count *= 10.0f;
	}
}