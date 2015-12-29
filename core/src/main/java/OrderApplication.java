import module.Module;
import module.SystemModule;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dylan
 */
public class OrderApplication extends Application {

    @Override
    public List<Module> loadModules() {
        return Arrays.asList(new OrderModule(context), new SystemModule("sys.properties"));
    }
}
