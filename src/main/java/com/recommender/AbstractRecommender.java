package com.recommender;

import java.util.List;
import java.util.Map;

import com.recommender.data.Data;
import com.recommender.math.Math;

/**
 * Abstract recommender consists common functions of any recommenders
 * @author p.bell
 *
 */
public abstract class AbstractRecommender implements Recommender{
	
	protected Data data;
	
	protected List<Integer> getRecommendationList(int numberOfRecommendation, Map<Integer, Double> predictionMap) {
		List<Integer> recommendationList = Math.sortByValue(predictionMap);
		return recommendationList.size() <= numberOfRecommendation ?
					recommendationList : recommendationList.subList(0, numberOfRecommendation );
	}
}
