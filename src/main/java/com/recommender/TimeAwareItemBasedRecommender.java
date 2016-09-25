package com.recommender;

import com.recommender.data.Data;
import com.recommender.model.Purchase;
import com.recommender.similarity.Similarity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * TimeAwareItemBasedRecommender is a kind of item based recommender.
 * we just override prediction algorithm and prioritize the last purchases.
 * formula is;
 * similarity= similarity * (1- ((last-current) / (last-first)))
 * 
 * this is applied to the customers who has more than 2 purchases.
 * @author p.bell
 *
 */
public class TimeAwareItemBasedRecommender extends AbstractItemBasedRecommender{

	public TimeAwareItemBasedRecommender(Data data,Similarity similarity){
		this.data=data;
		this.similarity=similarity;
		
	}

	public List<Integer> recommend(int userId,int numberOfRecommendation) throws InterruptedException {
		Map<Integer, Purchase> user = data.getUser(userId);
		if(user.size()<=2){
			throw new IllegalArgumentException("user does not have enough ratings. Number of ratings is "+user.size());
		}
		Map<Integer,Double>predictionMap= new ConcurrentHashMap<>();
		Map<Integer, Double> purchaseTimeRanges = getPurchaseTimeRanges(user);
		final double firstPurchase=purchaseTimeRanges.get(0);
		final double lastPurchase=purchaseTimeRanges.get(1);
		final Random random= new Random();
		data.getItemMap().keySet()
				.parallelStream()
				.filter(itemId->!user.containsKey(itemId) && random.nextInt(10)>=8 )
				.forEach(itemId->{
					double prediction= predict(userId,itemId,lastPurchase,firstPurchase);
					if(prediction!=0){
						predictionMap.put(itemId, prediction);
					}
				});
		return getRecommendationList(numberOfRecommendation, predictionMap);
	}
	public Map<Integer,Double> getPurchaseTimeRanges(Map<Integer, Purchase> user){
		//we should get last and first purchase dates. we will use these dates while prediction
		double firstPurchase=Double.MAX_VALUE;
		double lastPurchase=Double.MIN_VALUE;
		Iterator<Integer> iterator = user.keySet().iterator();
		while(iterator.hasNext()){
			Purchase purchase = user.get(iterator.next());
			if(purchase.getTimestamp()>lastPurchase){
				lastPurchase=purchase.getTimestamp();
			}else if(purchase.getTimestamp()<firstPurchase){
				firstPurchase=purchase.getTimestamp();
			}
		}
		Map<Integer,Double> purchaseTimeRange= new HashMap<>();
		purchaseTimeRange.put(0,firstPurchase);
		purchaseTimeRange.put(1,lastPurchase);
		return purchaseTimeRange;
	}
	/**
	 * time awareness formula is as follows;
	 * (last-current) / (last-first)
	 * @param userId
	 * @param itemId
	 * @param lastPurchase
	 * @param firstPurchase
	 * @return
	 */
	public double predict(int userId, int itemId, double lastPurchase, double firstPurchase) {
		Map<Integer, Purchase> user = data.getUser(userId);
		Iterator<Integer> iterator = user.keySet().iterator();
		double totalSimilarity=0;
		while(iterator.hasNext()){
			int purchasedItemId=iterator.next();
			double currentPurchase=user.get(purchasedItemId).getTimestamp();
			double weight = 1l - ((lastPurchase - currentPurchase) / (lastPurchase - firstPurchase));
			totalSimilarity+=similarity.getSimilarity(itemId, purchasedItemId,data)* weight;
		}
		return totalSimilarity/user.size();
	}
}
