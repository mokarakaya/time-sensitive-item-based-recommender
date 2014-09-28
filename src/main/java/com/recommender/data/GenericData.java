package com.recommender.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import com.recommender.model.Purchase;

public class GenericData implements Data{

	private Map<Integer,Map<Integer,Purchase>> userMap;
	private Map<Integer,List<Integer>> itemMap;
	
	public GenericData(String filePath) throws IOException, ParseException {
		userMap = new HashMap<Integer, Map<Integer, Purchase>>();
		itemMap = new HashMap<Integer, List<Integer>>();
		loadData(filePath);
	}

	private void loadData(String filePath) throws FileNotFoundException, IOException {
		BufferedReader br =null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			String line = br.readLine();
			while (line != null) {
				String[] split = line.split(",");
				int userId = Integer.parseInt(split[0].trim());
				int itemId = Integer.parseInt(split[1].trim());
				long time = Long.parseLong(split[2]);
				if(userMap.get(userId)==null){
					userMap.put(userId, new HashMap<Integer, Purchase>());
				}
				userMap.get(userId).put(itemId, new Purchase(time));
				
				if(itemMap.get(itemId)==null){
					itemMap.put(itemId, new ArrayList<Integer>());
				}
				itemMap.get(itemId).add(userId);
				line = br.readLine();
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	public Map<Integer, Purchase> getUser(int userId) {
		return userMap.get(userId);
	}

	public List<Integer> getItem(int itemId) {
		return itemMap.get(itemId);
	}

	public int getNumberOfItems() {
		return itemMap.size();
	}

	public int getNumberOfUsers() {
		return userMap.size();
	}

	
}
