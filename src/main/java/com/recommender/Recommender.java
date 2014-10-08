package com.recommender;

import java.util.List;

/**
 * this is a general Recommender interface with basic functions and all recommenders should implement this.
 * @author p.bell
 *
 */
public interface Recommender {

	List<Integer> recommend(int userId,int numberOfRecommendation);
	double predict(int userId, int itemId);
}
