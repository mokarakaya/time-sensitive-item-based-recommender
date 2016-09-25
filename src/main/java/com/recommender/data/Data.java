package com.recommender.data;

import com.recommender.model.Purchase;

import java.util.List;
import java.util.Map;

public interface Data {
	
	Map<Integer,Purchase> getUser(int userId);
	List<Integer> getItem(int itemId);
	Map<Integer, List<Integer>> getItemMap();
	

}
