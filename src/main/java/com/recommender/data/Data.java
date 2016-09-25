package com.recommender.data;

import com.recommender.model.Purchase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface Data {
	
	Map<Integer,Purchase> getUser(int userId);
	List<Integer> getItem(int itemId);
	Map<Integer, List<Integer>> getItemMap();
	void loadData(String filePath) throws IOException, URISyntaxException;

}
