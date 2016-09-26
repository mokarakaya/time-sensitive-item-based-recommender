package com.recommender.similarity;

import com.recommender.data.Data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mokarakaya on 26.09.2016.
 */
public class CosineSimilarity extends  AbstractSimilarity {

    /**
     * calculates similarity between item i and item j
     * formula: coPurchased/MaxItemSize(size of item i, size of item j)
     * @param i
     * @param j
     * @param data
     * @return
     */
    public  Double calculateSimilarity(int i, int j,Data data) {
        List<Integer> itemi = data.getItem(i);
        List<Integer> itemj = data.getItem(j);
        double coPurchased=0;
        for(int userId:itemi){
            if(itemj.contains(userId)){
                coPurchased++;
            }
        }
        double similarity = coPurchased / Math.max(itemi.size(), itemj.size());
        similarityMatrix.putIfAbsent(i,new HashMap<>());
        similarityMatrix.get(i).put(j,similarity);
        return similarity;
    }
}
