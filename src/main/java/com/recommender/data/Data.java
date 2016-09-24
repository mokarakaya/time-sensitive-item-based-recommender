package com.recommender.data;

import java.util.List;
import java.util.Map;

import com.recommender.model.Purchase;

public interface Data {
	
	Map<Integer,Purchase> getUser(int userId);
	List<Integer> getItem(int itemId);
	int getNumberOfItems();
	int getNumberOfUsers();
	Map<Integer, List<Integer>> getItemMap();
	

}
