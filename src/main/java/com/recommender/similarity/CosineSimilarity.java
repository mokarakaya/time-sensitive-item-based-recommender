package com.recommender.similarity;

import com.recommender.data.Data;
import com.recommender.model.Purchase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by mokarakaya on 26.09.2016.
 */
public class CosineSimilarity extends  AbstractSimilarity {

    /**
     * calculates cosine similarity between item i and item j
     * @param i
     * @param j
     * @param data
     * @return
     */
    public  Double calculateSimilarity(int i, int j,Data data) {

        Map<Integer, Purchase> itemi = data.getItem(i);
        Map<Integer, Purchase> itemj = data.getItem(j);
        HashSet<Integer> set = new HashSet<>(data.getItem(i).keySet());
        set.retainAll(itemj.keySet());
        double dotProduct = set.stream()
                .mapToDouble(itemId -> itemi.get(itemId).getRating() * itemj.get(itemId).getRating())
                .sum();
        double itemiSqrt = getSumOfSquares(itemi);
        double itemjSqrt = getSumOfSquares(itemj);

        double similarity = dotProduct / (Math.sqrt(itemiSqrt)*Math.sqrt(itemjSqrt));
        similarityMatrix.putIfAbsent(i,new HashMap<>());
        similarityMatrix.get(i).put(j,similarity);
        return similarity;
    }

    private double getSumOfSquares(Map<Integer, Purchase> map) {
        return map.values().stream()
                .mapToDouble(purchase -> purchase.getRating() * purchase.getRating())
                .sum();
    }
}
