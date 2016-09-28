package com.recommender.similarity;

import com.recommender.data.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

abstract class AbstractSimilarity implements Similarity {
	
	volatile Map<Integer,Map<Integer,Double>> similarityMatrix;
	AbstractSimilarity(){
		this.similarityMatrix= new ConcurrentHashMap<>();
	}


	/**
	 * similarity matrix should be symmetric.
	 * how to get similarity is generic to all types of similarites.
	 */
	public double getSimilarity(int firstItem, int secondItem, Data data) {
		int i = Math.min(firstItem, secondItem);
		int j =Math.max(firstItem, secondItem);
		//if value does not exist in similarityMatrix calculate and put
		return similarityMatrix.get(i) !=null && similarityMatrix.get(i).containsKey(j)?
				similarityMatrix.get(i).get(j)
				:calculateSimilarity(i,j,data) ;
	}

	/**
	 * every similarity metric (cosine, manhattan etc.) has its own way to calculate similarity.
	 * @param i
	 * @param j
	 * @param data
	 * @return
	 */
	abstract Double calculateSimilarity(int i, int j, Data data);
}
