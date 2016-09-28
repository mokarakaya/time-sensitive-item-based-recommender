package recommender.data;

import com.recommender.data.Data;
import com.recommender.data.GenericData;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

/**
 * Created by mokarakaya on 28.09.2016.
 */
public class GenericDataTest {

    public static final String TEST_FILE_PATH = "/testRatings.dat";

    @Test
    public void testLoadData() throws IOException, URISyntaxException {
        Data data = getData();
        assertEquals(3,data.getItemMap().size());
    }

    public Data getData() throws IOException, URISyntaxException {
        Data data = new GenericData();
        data.loadData(getClass().getResource(TEST_FILE_PATH).toURI());
        return data;
    }
}
