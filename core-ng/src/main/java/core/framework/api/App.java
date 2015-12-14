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
            logger.info("[Debug] App start...");
            configure();

            logger.info("execute startup methods");
            context.startupHook.forEach(java.lang.Runnable::run);
        } catch (Throwable e) {
            logger.error("application failed to start, error={}", e.getMessage(), e);
            System.exit(1);
        }
    }

    public final void configure() {
        logger.info("initialize framework");
        context = new ModuleContext(new BeanFactory(), null);

        logger.info("initialize application");
        logger.info("[Debug] configure start concrete initialize method...");
        initialize();
    }
}
