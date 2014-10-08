package com.recommender.similarity;

import com.recommender.data.Data;

public interface Similarity {
	/**
	 * returns the similarity of two items
	 * @param firstItem
	 * @param secondItem
	 * @return
	 */
	double getSimilarity(int firstItem,int secondItem);
	
	Double calculateSimilarity(int i, int j,Data data);
	
}
