import app.DemoServiceApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author neo
 */
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);
    ThreadLocal<Integer> threadCount = new ThreadLocal<>();
    public static void main(String[] args) {
        logger.info("[Debug-Start] Main");
        new DemoServiceApp().start();
        logger.info("[Debug-End] Main");
    }
}
