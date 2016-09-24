package com.recommender.similarity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.recommender.data.Data;

public class BooleanPrefSimilarity implements Similarity {
	
	private final Map<Integer,Map<Integer,Double>> similarityMatrix;
	public BooleanPrefSimilarity(){
		this.similarityMatrix= new ConcurrentHashMap<>();
	}


	/**
	 * similarity matrix should be symmetric.
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
	 * calculates similarity between item i and item j
	 * formula: coPurchased/MaxItemSize(size of item i, size of item j)
	 * @param i
	 * @param j
	 * @param data
	 * @return
	 */
	public Double calculateSimilarity(int i, int j,Data data) {
		List<Integer> itemi = data.getItem(i);
		List<Integer> itemj = data.getItem(j);
		double coPurchased=0;
		for(int userId:itemi){
			if(itemj.contains(userId)){
				coPurchased++;
			}
		}
		similarityMatrix.putIfAbsent(i,new HashMap<>());
		double similarity = coPurchased / Math.max(itemi.size(), itemj.size());
		similarityMatrix.get(i).put(j,similarity);
		return similarity;
	}
}
