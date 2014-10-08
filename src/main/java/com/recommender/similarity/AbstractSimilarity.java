package com.recommender.similarity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.google.gson.Gson;
import com.recommender.data.Data;

public abstract class AbstractSimilarity implements Similarity {
	
	private static final String EXTENSION = "similarity";
	public Map<Integer,Map<Integer,Double>> similarityMatrix;
	
	/**
	 * 
	 * @param data
	 * @param fileExists 
	 * if fileExists= true just reads the similarity file else builds a new one.
	 * @param path
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	protected void buildMatrix(Data data, boolean fileExists,String path) throws IOException, InterruptedException {
		if (fileExists) {
			readMatrix(path);
		} else {
			int numberOfItems = data.getNumberOfItems();
			for (int i = 0; i < numberOfItems; i++) {
				System.out.println(i +" of "+numberOfItems);
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
	
	/**
	 * similarity matrix should be symmetric.
	 */
	public double getSimilarity(int firstItem, int secondItem) {
		String i =""+ Math.min(firstItem, secondItem);
		String j = ""+Math.max(firstItem, secondItem);
		//if value does not exist in similarityMatrix simply return 0
		return similarityMatrix.get(i) !=null && similarityMatrix.get(i).containsKey(j)? similarityMatrix.get(i).get(j) : 0;
	}
}
