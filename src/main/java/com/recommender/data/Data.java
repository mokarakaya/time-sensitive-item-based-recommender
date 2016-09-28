package com.recommender.data;

import com.recommender.model.Purchase;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public interface Data {
	
	Map<Integer,Purchase> getUser(int userId);
	Map<Integer, Purchase> getItem(int itemId);
	Map<Integer, Map<Integer, Purchase>> getItemMap();
	void loadData(URI filePath) throws IOException,URISyntaxException ;

}
