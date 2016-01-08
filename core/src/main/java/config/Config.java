package config;

import context.ModuleContext;

/**
 * @author ChNan
 */
public class Config {
    public ModuleContext context;

    public APIConfig api() {
        return new APIConfig(context);
    }

    public DBConfig db() {
        return new DBConfig();
    }
}
