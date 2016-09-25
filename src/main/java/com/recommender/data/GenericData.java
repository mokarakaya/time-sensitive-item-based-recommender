package com.recommender.data;

import com.recommender.model.Purchase;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericData implements Data{

	private final Map<Integer,Map<Integer,Purchase>> userMap;
	private final Map<Integer,List<Integer>> itemMap;
	
	public GenericData(String filePath) throws IOException {
		userMap = new HashMap<>();
		itemMap = new HashMap<>();
		loadData(filePath);
	}

	private void loadData(String filePath) throws FileNotFoundException, IOException {
		BufferedReader br =null;
		try {
			br = new BufferedReader(new FileReader(getFile(filePath)));
			String line = br.readLine();
			while (line != null) {
				String[] split = line.split("::");
				int userId = Integer.parseInt(split[0].trim());
				int itemId = Integer.parseInt(split[1].trim());
				int rating = Integer.parseInt(split[2].trim());
				double time = Double.parseDouble(split[3]);
				if(userMap.get(userId)==null){
					userMap.put(userId, new HashMap<Integer, Purchase>());
				}
				userMap.get(userId).put(itemId, new Purchase(rating,time));
				
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
	private File getFile(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		return new File(classLoader.getResource(fileName).getFile());
	}
	public int getNumberOfUsers() {
		return userMap.size();
	}

	public Map<Integer, List<Integer>> getItemMap() {
		return itemMap;
	}
}
