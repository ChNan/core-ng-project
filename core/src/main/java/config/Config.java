package config;

import web.ModuleContext;

/**
 * @author Dylan
 */
public class Config {

    public APIConfig api(ModuleContext context) {
        return new APIConfig(context);
    }

}
