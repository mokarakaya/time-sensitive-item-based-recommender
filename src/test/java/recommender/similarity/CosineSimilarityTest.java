package recommender.similarity;

import com.recommender.data.Data;
import com.recommender.similarity.CosineSimilarity;
import com.recommender.similarity.Similarity;
import junit.framework.TestCase;
import org.junit.Test;
import recommender.data.GenericDataTest;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by mokarakaya on 28.09.2016.
 */
public class CosineSimilarityTest extends TestCase {

    @Test
    public void testGetSimilaritySymmetric() throws IOException, URISyntaxException {
        Data data = new GenericDataTest().getData();
        Similarity similarity= new CosineSimilarity();
        assertEquals(similarity.getSimilarity(1, 3, data),similarity.getSimilarity(3, 1, data));
        assertEquals(similarity.getSimilarity(1, 3, data),new CosineSimilarity().getSimilarity(3, 1, data));
    }

    @Test
    public void testGetSimilarity() throws IOException, URISyntaxException {
        Data data = new GenericDataTest().getData();
        Similarity similarity= new CosineSimilarity();
        assertEquals(0.7071067811865475,similarity.getSimilarity(3, 1, data));

    }


}
