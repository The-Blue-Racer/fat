import org.apache.log4j.Logger;
import org.junit.Test;

public class log4jtest {

    static Logger log = Logger.getLogger(log4jtest.class);

    @Test
    public void simpleTest() {
        log.info("This is Logger Info");
    }


}
