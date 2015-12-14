package app;

import core.framework.api.App;
import core.framework.api.module.SystemModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author neo
 */
public class DemoServiceApp extends App {
    Logger logger = LoggerFactory.getLogger(DemoServiceApp.class);
    @Override
    protected void initialize() {
        logger.info("[Debug] DemoServiceApp initialize...");
        load(new SystemModule("sys.properties"));

        load(new ProductModule());
        load(new JobModule());
    }
}
