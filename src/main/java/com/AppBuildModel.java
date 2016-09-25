package com;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import com.recommender.similarity.BooleanPrefSimilarity;
import org.json.simple.parser.ParseException;

import com.recommender.Recommender;
import com.recommender.TimeAwareItemBasedRecommender;
import com.recommender.data.Data;
import com.recommender.data.GenericData;
import com.recommender.similarity.Similarity;

public class AppBuildModel {
	
	private static final String FILE_PATH= "ratings.dat";
    private static final int RECOMMENDED_ITEMS=20;
	public static void main( String[] args ) throws IOException, ParseException, InterruptedException
    {
		System.out.println(new Date());
        Data data= new GenericData(FILE_PATH);
        System.out.println("data loaded:"+new Date());
        Similarity similarity= new BooleanPrefSimilarity();
        System.out.println("matrix loaded:"+new Date());
        final Recommender recommender = new TimeAwareItemBasedRecommender(data,similarity);
        //recommend items to first 5 users.
        IntStream stream = IntStream.rangeClosed(1,5);
        stream.parallel().forEach(i->{
            try {
                List<Integer> recommend= recommender.recommend(i, RECOMMENDED_ITEMS);
                System.out.println("recommended items for user "+i+" :"+recommend);
                System.out.println(new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}

