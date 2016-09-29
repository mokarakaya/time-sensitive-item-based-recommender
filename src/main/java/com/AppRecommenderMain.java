package com;

import com.recommender.Recommender;
import com.recommender.TimeSensitiveItemBasedRecommender;
import com.recommender.data.Data;
import com.recommender.data.GenericData;
import com.recommender.similarity.CosineSimilarity;
import com.recommender.similarity.Similarity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class AppRecommenderMain {

    //this is MovieLens 100K Dataset @ http://grouplens.org/datasets/movielens/100k/
	private static final String FILE_PATH= "/ratings.dat";
    private static final int RECOMMENDED_ITEMS=20;
    private static final int CANDIDATE_ITEM_PERCENTAGE=20;

    public static void main( String[] args ) throws IOException, URISyntaxException {
        Data data= new GenericData();
        data.loadData(AppRecommenderMain.class.getResource(FILE_PATH).toURI());
        Similarity similarity= new CosineSimilarity();
        System.out.println(new Date());
        final Recommender recommender = new TimeSensitiveItemBasedRecommender(data,similarity,CANDIDATE_ITEM_PERCENTAGE);
        //recommend 20 items to first 5 users.
        IntStream.rangeClosed(1,5).forEach(i-> {
            List<Integer> recommend = recommender.recommend(i, RECOMMENDED_ITEMS);
            System.out.println("recommended items for user " + i + " :" + recommend);
        });
        System.out.println(new Date());
    }
}

