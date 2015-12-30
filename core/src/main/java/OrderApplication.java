import module.SystemModule;
import test.order.OrderModule;

/**
 * @author ChNan
 */
public class OrderApplication extends Application {

    @Override
    public void loadModule() {
        load(new SystemModule("sys.properties"));
        load(new OrderModule(context));
    }
}
