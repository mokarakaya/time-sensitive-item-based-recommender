package com.recommender.model;

public final class Purchase {
	
	private long timestamp;
	
	public long getTimestamp() {
		return timestamp;
	}

	public Purchase(long timestamp){
		this.timestamp=timestamp;
	} 
}
