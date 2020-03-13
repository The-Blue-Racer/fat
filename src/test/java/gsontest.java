import com.google.gson.Gson;
import org.junit.Test;
import org.junit.Assert;

public class gsontest {

    @Test
    public void simpleTest() {
        String s = "Gson Test";
        Gson gson = new Gson();
        String result = gson.toJson(s);
        Assert.assertEquals(result,"\"Gson Test\"");
    }


}
