import module.Module;
import web.ModuleContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChNan
 */
public abstract class Application {
    private final List<Module> moduleList = new ArrayList<>();

    public ModuleContext context;

    public void start() {
        configure();

        moduleInitialized();

        startupHookRun();
    }

    public void configure() {
        // 1 create module context, initialize context
        context = new ModuleContext();
        context.startupHook.add(context.httpServer::start);
        moduleList.addAll(loadModules());
    }

    private void moduleInitialized() {
        moduleList.forEach(Module::initialized);
    }

    private void startupHookRun() {
        context.startupHook.forEach(Runnable::run);
    }

    public abstract List<Module> loadModules();
}
