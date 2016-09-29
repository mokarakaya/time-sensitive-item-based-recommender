package com.recommender.similarity;

import com.google.common.util.concurrent.Striped;
import com.recommender.data.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

abstract class AbstractSimilarity implements Similarity {
	
	volatile Map<Integer,Map<Integer,Double>> similarityMatrix;
	private final Striped<Lock> STRIPPED_LOCK;

	AbstractSimilarity(){
		this.similarityMatrix= new ConcurrentHashMap<>();
		this.STRIPPED_LOCK= Striped.lock(64);
	}


	/**
	 * similarity matrix should be symmetric.
	 * how to get similarity is generic for all types of similarites.
	 */
	public double getSimilarity(int firstItem, int secondItem, Data data) {
		int i = Math.min(firstItem, secondItem);
		int j =Math.max(firstItem, secondItem);
		int pair=i * 29 + j * 31;
		try {
			//if value does not exist in similarityMatrix calculate and put
			// just synchronize by input not whole object
			STRIPPED_LOCK.get(pair).lock();
			return similarityMatrix.get(i) != null && similarityMatrix.get(i).containsKey(j) ?
					similarityMatrix.get(i).get(j)
					: calculateSimilarity(i, j, data);
		}finally {
			STRIPPED_LOCK.get(pair).unlock();
		}
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
