package com.main;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.recommender.Recommender;
import com.recommender.TimeAwareItemBasedRecommender;
import com.recommender.data.Data;
import com.recommender.data.GenericData;
import com.recommender.similarity.BooleanPrefSimilarity;
import com.recommender.similarity.Similarity;

public class AppBuildModel {
	
	static final String FILE_PATH= "C:/is/rocket/purchasesConvertedMeasure20Dev.csv";
	public static void main( String[] args ) throws IOException, ParseException
    {
        Data data= new GenericData(FILE_PATH);
        Similarity similarity= new BooleanPrefSimilarity(data, FILE_PATH,true);
        Recommender recommender = new TimeAwareItemBasedRecommender(data,similarity);
    }
}

