package recommender.data;

import com.recommender.data.Data;
import com.recommender.data.GenericData;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by mokarakaya on 28.09.2016.
 */
public class GenericDataTest {

    public static final String TEST_FILE_PATH = "/testRatings.dat";
    public static final String TEST_FILE_PATH_LOTS_OF_ITEMS = "/testRatingsLotsOfItems.dat";

    @Test
    public void testLoadData() throws IOException, URISyntaxException {
        Data data = getData(TEST_FILE_PATH);
        assertEquals(4,data.getItemMap().size());
    }

    public Data getData(String filePath) throws IOException, URISyntaxException {
        Data data = new GenericData();
        data.loadData(getClass().getResource(filePath).toURI());
        return data;
    }
}
