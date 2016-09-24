package com.recommender;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.recommender.data.Data;

/**
 * Abstract recommender consists common functions of any recommenders
 * @author p.bell
 *
 */
public abstract class AbstractRecommender implements Recommender{
	
	protected Data data;
	
	protected List<Integer> getRecommendationList(int numberOfRecommendation, Map<Integer, Double> predictionMap) {
		return predictionMap.entrySet().stream()
				.sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
				.limit(numberOfRecommendation)
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}
}
