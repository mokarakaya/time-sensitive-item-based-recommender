package com.recommender.similarity;

import com.recommender.data.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CosineSimilarity implements Similarity {
	
	private volatile Map<Integer,Map<Integer,Double>> similarityMatrix;
	public CosineSimilarity(){
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
	public  Double calculateSimilarity(int i, int j,Data data) {
		List<Integer> itemi = data.getItem(i);
		List<Integer> itemj = data.getItem(j);
		double coPurchased=0;
		for(int userId:itemi){
			if(itemj.contains(userId)){
				coPurchased++;
			}
		}
		double similarity = coPurchased / Math.max(itemi.size(), itemj.size());
		similarityMatrix.putIfAbsent(i,new HashMap<>());
		similarityMatrix.get(i).put(j,similarity);
		return similarity;
	}
}
