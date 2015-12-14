import app.DemoServiceApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author neo
 */
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("[Debug] Main start...");
        new DemoServiceApp().start();
    }
}
