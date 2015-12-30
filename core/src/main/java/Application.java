import module.Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.ModuleContext;

/**
 * @author ChNan
 */
public abstract class Application {
    public ModuleContext context;
    private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

    public void start() {
        LOGGER.info("Application...start begin");
        configure();

        loadModule();

        startupHookRun();

        LOGGER.info("Application...start end");
    }

    public void configure() {
        // 1 create module context, initialize context
        context = new ModuleContext();
        context.startupHook.add(context.httpServer::start);
    }

    private void startupHookRun() {
        context.startupHook.forEach(Runnable::run);
    }


    public void load(Module module) {
        module.initialized();
    }

    public abstract void loadModule();
}
