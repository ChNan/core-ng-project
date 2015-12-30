import module.SystemModule;

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
