package com.recommender;

import java.util.List;

public interface Recommender {

	List<Integer> recommend(int userId,int numberOfRecommendation);
	double predict(int userId, int itemId);
}
