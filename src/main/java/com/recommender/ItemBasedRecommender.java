package com.recommender;

import com.recommender.data.Data;
import com.recommender.similarity.Similarity;

/**
 * This class is a classical implementation of item based recommender
 * @author p.bell
 *
 */
public class ItemBasedRecommender  extends AbstractItemBasedRecommender{

	public ItemBasedRecommender(Data data,Similarity similarity){
		this.data=data;
		this.similarity=similarity;
	}
	
	public ItemBasedRecommender(){
		
	}



	
}
