package com.recommender.similarity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.recommender.data.Data;

public class BooleanPrefSimilarity extends AbstractSimilarity{
	
	public BooleanPrefSimilarity(Data data,String path,boolean fileExists) throws IOException, InterruptedException{
		similarityMatrix=new HashMap<Integer, Map<Integer,Double>>();
		buildMatrix(data,fileExists,path);
	}
	
	public BooleanPrefSimilarity(){
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
		return coPurchased/Math.max(itemi.size() , itemj.size());
	}

	
	
}

