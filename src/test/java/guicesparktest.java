import com.google.inject.Guice;
import com.google.inject.Injector;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Consumer;

import static spark.Spark.get;
import static spark.Spark.stop;

public class guicesparktest {

    static Logger LOG = Logger.getLogger(guicesparktest.class);


    private Consumer<Exception> initExceptionHandler = (e) -> {
        LOG.error("ignite failed", e);
        System.exit(100);
    };

    @Before
    public void initObjects() {
        try {
            Injector injector = Guice.createInjector();
            guicesparktest someClass = injector.getInstance(guicesparktest.class);
            get("/hello", (req, res) -> someClass.getMessage());
        } catch (Exception e) {
            Assert.assertTrue(false);
            initExceptionHandler.accept(e);
        }
    }

    private String getMessage() {
        return "Hello World";
    }


    @Test
    public void simpleTest() throws IOException {
        String response = getFromUrl("http://localhost:4567/hello");
        Assert.assertEquals("Hello World", response);
    }


    @After
    public void stopSpark() {
        stop();
    }

    String getFromUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

}
