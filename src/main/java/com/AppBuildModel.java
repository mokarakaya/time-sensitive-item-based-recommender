package com;

import com.recommender.Recommender;
import com.recommender.TimeAwareItemBasedRecommender;
import com.recommender.data.Data;
import com.recommender.data.GenericData;
import com.recommender.similarity.BooleanPrefSimilarity;
import com.recommender.similarity.Similarity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class AppBuildModel {
	
	private static final String FILE_PATH= "ratings.dat";
    private static final int RECOMMENDED_ITEMS=20;
	public static void main( String[] args ) throws IOException, InterruptedException, URISyntaxException {
		System.out.println(new Date());
        Data data= new GenericData();
        data.loadData(FILE_PATH);
        System.out.println("data loaded:"+new Date());
        Similarity similarity= new BooleanPrefSimilarity();
        final Recommender recommender = new TimeAwareItemBasedRecommender(data,similarity);
        //recommend items to first 5 users.
        IntStream.rangeClosed(1,5).parallel().forEach(i->{
            try {
                List<Integer> recommend= recommender.recommend(i, RECOMMENDED_ITEMS);
                System.out.println("recommended items for user "+i+" :"+recommend);
                System.out.println(new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });
    }
}

