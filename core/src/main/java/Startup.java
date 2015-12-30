import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ChNan
 */
public class Startup {
    private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Startup...begin");
        new OrderApplication().start();
        LOGGER.info("Startup...end");
    }
}
