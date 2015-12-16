package core.framework.api;

import core.framework.impl.inject.BeanFactory;
import core.framework.impl.module.ModuleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author neo
 */
public abstract class App extends Module {
    private final Logger logger = LoggerFactory.getLogger(App.class);

    public final void start() {
        try {
            logger.info("[Debug-Start] App");
            configure();

            logger.info("execute startup methods");
            context.startupHook.forEach(java.lang.Runnable::run);
            logger.info("[Debug-End] App");
        } catch (Throwable e) {
            logger.error("application failed to start, error={}", e.getMessage(), e);
            System.exit(1);
        }
    }

    public final void configure() {
        logger.info("[Debug-Start] configure");
        logger.info("configure - initialize framework");
        context = new ModuleContext(new BeanFactory(), null);

        logger.info("configure - initialize application");

        logger.info("[Debug-Start] initialize");
        initialize();
        logger.info("[Debug-End] initialize");

        logger.info("[Debug-End] configure");
    }
}
