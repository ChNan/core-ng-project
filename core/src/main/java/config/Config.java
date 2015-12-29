package config;

import web.ModuleContext;

/**
 * @author ChNan
 */
public class Config {

    public APIConfig api(ModuleContext context) {
        return new APIConfig(context);
    }

}
