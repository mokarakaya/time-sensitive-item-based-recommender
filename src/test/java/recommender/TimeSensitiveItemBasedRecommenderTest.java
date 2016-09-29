package recommender;

import com.recommender.Recommender;
import com.recommender.TimeSensitiveItemBasedRecommender;
import com.recommender.data.Data;
import com.recommender.similarity.CosineSimilarity;
import org.junit.Test;
import recommender.data.GenericDataTest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static recommender.data.GenericDataTest.TEST_FILE_PATH;
import static recommender.data.GenericDataTest.TEST_FILE_PATH_LOTS_OF_ITEMS;

/**
 * Created by 212457624 on 9/28/2016.
 */

public class TimeSensitiveItemBasedRecommenderTest {

    @Test
    public void testRecommend() throws IOException, URISyntaxException {
        Recommender recommender = getRecommender(TEST_FILE_PATH);
        List<Integer> recommend = recommender.recommend(1, 20);
        assertEquals(1,recommend.size());
        assertEquals(4,recommend.get(0).intValue());
    }

    @Test
    public void testRecommendLotsOfItems() throws IOException, URISyntaxException {
        Recommender recommender = getRecommender(TEST_FILE_PATH_LOTS_OF_ITEMS);
        List<Integer> recommend = recommender.recommend(1, 20);
        System.out.println(recommend);
        assertEquals(20,recommend.size());
        int[] recommendedArr= new int []{23, 22, 21, 20, 14, 15, 16, 17, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 18, 19};
        for (int i : recommendedArr) {
            assertTrue(recommend.contains(i));
        }

    }



    @Test(expected = IllegalArgumentException.class)
    public void testRecommendedNotEnoughRatings() throws IOException, URISyntaxException {
        Recommender recommender = getRecommender(TEST_FILE_PATH);
        recommender.recommend(3,20);

    }
    private Recommender getRecommender(String filePath) throws IOException, URISyntaxException {
        Data data = new GenericDataTest().getData(filePath);
        return new TimeSensitiveItemBasedRecommender(data,new CosineSimilarity(),100);
    }
}
