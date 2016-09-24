package com;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.recommender.similarity.BooleanPrefSimilarity;
import org.json.simple.parser.ParseException;

import com.recommender.Recommender;
import com.recommender.TimeAwareItemBasedRecommender;
import com.recommender.data.Data;
import com.recommender.data.GenericData;
import com.recommender.similarity.Similarity;

public class AppBuildModel {
	
	static final String FILE_PATH= "ratings.dat";
	static final int USER_ID=1;
	static final int RECOMMENDED_ITEMS=5;
	public static void main( String[] args ) throws IOException, ParseException, InterruptedException
    {
		System.out.println(new Date());
        Data data= new GenericData(FILE_PATH);
        System.out.println("data loaded:"+new Date());
        Similarity similarity= new BooleanPrefSimilarity();
        System.out.println("matrix loaded:"+new Date());
        Recommender recommender = new TimeAwareItemBasedRecommender(data,similarity);
        List<Integer> recommend = recommender.recommend(USER_ID, RECOMMENDED_ITEMS);
        System.out.println("recommended items:"+recommend);
        System.out.println(new Date());
    }
}

