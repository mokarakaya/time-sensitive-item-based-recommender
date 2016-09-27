package recommender.data;

import com.recommender.data.Data;
import com.recommender.data.GenericData;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by mokarakaya on 28.09.2016.
 */
public class GenericDataTest extends TestCase {

    public static final String FILE_PATH = "ratings.dat";

    @Test
    public void testLoadData() throws IOException, URISyntaxException {
        Data data = new GenericData();
        data.loadData(FILE_PATH);
        assertEquals(2,data.getItemMap().size());
    }
}
