package com.recommender;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.recommender.data.Data;
import com.recommender.model.Purchase;
import com.recommender.similarity.Similarity;

public class ItemBasedRecommender  extends AbstractItemBasedRecommender{

	public ItemBasedRecommender(Data data,Similarity similarity){
		this.data=data;
		this.similarity=similarity;
		
	}

	public List<Integer> recommend(int userId,int numberOfRecommendation) {
		Map<Integer,Double>predictionMap= new HashMap<Integer, Double>();
		Map<Integer, Purchase> user = data.getUser(userId);
		for(int i=0; i<data.getNumberOfItems();i++){
			if(!user.containsKey(i)){
				double prediction= predict(userId,i);
				if(prediction!=0){
					predictionMap.put(i, prediction);
				}
			}
		}
		return getRecommendationList(numberOfRecommendation, predictionMap);
	}

	public double predict(int userId, int itemId) {
		Map<Integer, Purchase> user = data.getUser(userId);
		Iterator<Integer> iterator = user.keySet().iterator();
		double totalSimilarity=0;
		while(iterator.hasNext()){
			int purchasedItemId=iterator.next();
			totalSimilarity+=similarity.getSimilarity(itemId, purchasedItemId);
		}
		return totalSimilarity/user.size();
	}
}
