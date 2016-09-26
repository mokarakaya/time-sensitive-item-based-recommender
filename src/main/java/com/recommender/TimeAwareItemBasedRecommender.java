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
 * @author mokarakaya
 *
 */
public class TimeAwareItemBasedRecommender extends AbstractItemBasedRecommender{

	private final int candidateItemPercentage;
	public TimeAwareItemBasedRecommender(Data data, Similarity similarity, int candidateItemPercentage){
		super(data,similarity);
		this.candidateItemPercentage=candidateItemPercentage;
	}

	public List<Integer> recommend(int userId,int numberOfRecommendation){
		Map<Integer, Purchase> user = data.getUser(userId);
		if(user.size()<=2){
			throw new IllegalArgumentException("user does not have enough ratings. Number of ratings is "+user.size());
		}
		final DoubleSummaryStatistics statistics = user.values().stream()
				.mapToDouble(purchase -> purchase.getTimestamp())
				.summaryStatistics();
		final double firstPurchase=statistics.getMin();
		final double lastPurchase=statistics.getMax();
		final Random random= new Random();
		final Map<Integer,Double>predictionMap= new ConcurrentHashMap<>();
		data.getItemMap().keySet()
				.parallelStream()
				.filter(itemId->!user.containsKey(itemId) && random.nextInt(100)<=candidateItemPercentage )
				.forEach(itemId->{
					double prediction= predict(userId,itemId,lastPurchase,firstPurchase);
					if(prediction!=0){
						predictionMap.put(itemId, prediction);
					}
				});
		return getRecommendationList(numberOfRecommendation, predictionMap);
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
	public double predict(final int userId, final int itemId, final double lastPurchase, final double firstPurchase) {
		Map<Integer, Purchase> user = data.getUser(userId);
		return user.entrySet().stream()
				.mapToDouble(entry -> {
					double weight = 1d - ((lastPurchase - entry.getValue().getTimestamp())
							/ (lastPurchase - firstPurchase));
					return similarity.getSimilarity(itemId, entry.getKey(), data) * weight;
				}).summaryStatistics().getAverage();
	}
}
