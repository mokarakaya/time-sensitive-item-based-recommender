package com.recommender.mahout;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MahoutMain {
	public static void main(String[] args) throws TasteException, IOException {
		DataModel model = new GenericBooleanPrefDataModel(new FileDataModel(new File("C:/is/rocket/purchasesConvertedMeasure.csv")));
//		Collection<GenericItemSimilarity.ItemItemSimilarity> correlations =new TanimotoCoefficientSimilarity(model);
		
//		RecommenderBuilder builder = new RecommenderBuilder() {
//			public Recommender buildRecommender(DataModel model) throws TasteException {
////				ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
////				Recommender recommender = new SVDRecommender(model,new ParallelArraysSGDFactorizer(model, 40, 40),new AllSimilarItemsCandidateItemsStrategy(similarity));
//				Recommender recommender = new SVDRecommender(model,new ALSWRFactorizer(model, 20, 0.005, 3),new AllUnknownItemsCandidateItemsStrategy());
//				return new CachingRecommender(recommender);
//			}
//		};
		
//		RecommenderBuilder builder = new RecommenderBuilder() {
//			public Recommender buildRecommender(DataModel model) throws TasteException {
//				ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
//				Recommender recommender = new GenericBooleanPrefItemBasedRecommender(model, similarity);
//				return new CachingRecommender(recommender);
//			}
//		};
		
		RecommenderBuilder builder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException {
				UserSimilarity similarity = new LogLikelihoodSimilarity(model);
				UserNeighborhood neighborhood =new NearestNUserNeighborhood(20, similarity, model);
				Recommender recommender = new GenericBooleanPrefUserBasedRecommender(model, neighborhood, similarity);
				return new CachingRecommender(recommender);
			}
		};
					
					
		
		
		DataModelBuilder modelBuilder = new DataModelBuilder() {
			public DataModel buildDataModel(FastByIDMap<PreferenceArray> trainingData) {
				return new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(trainingData));
			}
		};
		RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
		IRStatistics stats = evaluator.evaluate(builder, modelBuilder, model, null, 15, 0.5, 0.01);
		System.out.println(stats.getPrecision());
		System.out.println(stats.getRecall());
		System.out.println(stats.getF1Measure());
	}
}
