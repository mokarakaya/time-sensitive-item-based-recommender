package com.recommender.model;

public final class Purchase {

	final private int rating;
	final private double timestamp;
	public Purchase(int rating, double timestamp){
		this.rating=rating;
		this.timestamp=timestamp;
	}
	public int getRating() {return rating;}
	public double getTimestamp() {
		return timestamp;
	}

}
