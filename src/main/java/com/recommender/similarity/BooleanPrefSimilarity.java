package com.recommender.similarity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.recommender.data.Data;

public class BooleanPrefSimilarity implements Similarity{
	
	
	private static final String EXTENSION = "similarity";
	private Map<Integer,Map<Integer,Double>> similarityMatrix;
	
	public BooleanPrefSimilarity(Data data,String path,boolean fileExists) throws IOException{
		similarityMatrix=new HashMap<Integer, Map<Integer,Double>>();
		buildMatrix(data,fileExists,path);
	}

	private void buildMatrix(Data data, boolean fileExists,String path) throws IOException {
		if (fileExists) {
			readMatrix(path);
		} else {
			int numberOfItems = data.getNumberOfItems();
			for (int i = 0; i < numberOfItems; i++) {
				System.out.println(i+";"+numberOfItems);
				similarityMatrix.put(i, new HashMap<Integer, Double>());
				for (int j = i+1; j < numberOfItems; j++) {
					double similarity=calculateSimilarity(i, j, data);
					if(similarity!=0){
						similarityMatrix.get(i).put(j,similarity );
					}
				}
			}
			writeMatrix(path);
		}
	}

	private void writeMatrix(String path) throws IOException {

		BufferedWriter bw =null;
		try {
			bw = new BufferedWriter(new FileWriter(path+EXTENSION));
			Gson gson= new Gson();
			String write=gson.toJson(similarityMatrix);
			bw.write(write);
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
	}

	private void readMatrix(String path) throws IOException {
		BufferedReader br =null;
		try {
			br = new BufferedReader(new FileReader(path+EXTENSION));
			String line = br.readLine();
			Gson gson = new Gson();
			similarityMatrix=gson.fromJson(line, HashMap.class);
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	private Double calculateSimilarity(int i, int j,Data data) {
		List<Integer> itemi = data.getItem(i);
		List<Integer> itemj = data.getItem(j);
		double coPurchased=0;
		for(int userId:itemi){
			if(itemj.contains(userId)){
				coPurchased++;
			}
		}
		int divider = itemi.size() > itemj.size() ? itemi.size() : itemj.size();
		return coPurchased/divider;
	}

	public double getSimilarity(int firstItem, int secondItem) {
		return firstItem < secondItem ? similarityMatrix.get(firstItem).get(secondItem) : similarityMatrix.get(secondItem).get(firstItem);
	}
	
	
}
