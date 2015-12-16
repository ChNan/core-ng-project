package core.framework.api;

import core.framework.api.module.APIConfig;
import core.framework.api.module.CacheConfig;
import core.framework.api.module.DBConfig;
import core.framework.api.module.HTTPConfig;
import core.framework.api.module.LogConfig;
import core.framework.api.module.MongoConfig;
import core.framework.api.module.QueueConfig;
import core.framework.api.module.RedisConfig;
import core.framework.api.module.RouteConfig;
import core.framework.api.module.SchedulerConfig;
import core.framework.api.module.SearchConfig;
import core.framework.api.module.SiteConfig;
import core.framework.api.util.Exceptions;
import core.framework.impl.module.ModuleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author neo
 */
public abstract class Module {
    private final Logger logger = LoggerFactory.getLogger(Module.class);

    ModuleContext context;

    protected void load(Module module) {
        logger.info("[Debug-Start] load module start, module={}", module.getClass().getName());
        module.context = context;
        module.initialize();
        logger.info("[Debug-End] load module end, module={}", module.getClass().getName());
    }

    public void onShutdown(Runnable runnable) {
        context.shutdownHook.add(runnable);
    }

    public void onStartup(Runnable runnable) {
        context.startupHook.add(runnable);
    }

    public <T> T bind(Class<T> instanceClass) {
        T instance = context.beanFactory.create(instanceClass);
        return bind(instanceClass, null, instance);
    }

    public <T> T bind(T instance) {
        return bind(instance.getClass(), null, instance);
    }

    public <T> T bind(Class<? super T> type, T instance) {
        return bind(type, null, instance);
    }

    public <T> T bind(Type type, String name, T instance) {
        return bindSupplier(type, name, () -> instance);
    }

    public <T> T bindSupplier(Type type, String name, Supplier<T> supplier) {
        return context.beanFactory.bindSupplier(type, name, supplier);
    }

    public <T> T bean(Type instanceType, String name) {
        return context.beanFactory.bean(instanceType, name);
    }

    public <T> T bean(Class<T> instanceType) {
        return bean(instanceType, null);
    }

    public void loadProperties(String path) {
        logger.info("load properties, path={}", path);
        context.properties.load(path);
    }

    public Optional<String> property(String key) {
        return context.properties.get(key);
    }

    public String requiredProperty(String key) {
        return property(key).orElseThrow(() -> Exceptions.error("property key not found, key={}", key));
    }

    public LogConfig log() {
        return new LogConfig(context);
    }

    public HTTPConfig http() {
        return new HTTPConfig(context);
    }

    public RouteConfig route() {
        return new RouteConfig(context);
    }

    public SiteConfig site() {
        return new SiteConfig(context);
    }

    public CacheConfig cache() {
        return new CacheConfig(context);
    }

    public QueueConfig queue() {
        return new QueueConfig(context);
    }

    public SchedulerConfig schedule() {
        return new SchedulerConfig(context);
    }

    public APIConfig api() {
        return new APIConfig(context);
    }

    public DBConfig db() {
        return db(null);
    }

    public DBConfig db(String name) {
        return new DBConfig(context, name);
    }

    public RedisConfig redis() {
        return new RedisConfig(context);
    }

    public SearchConfig search() {
        return new SearchConfig(context);
    }

    public MongoConfig mongo() {
        return new MongoConfig(context);
    }

    protected abstract void initialize();
}
