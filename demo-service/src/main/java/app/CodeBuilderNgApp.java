package app;

import core.framework.api.App;
import core.framework.api.http.HTTPMethod;
import core.framework.api.web.Controller;
import core.framework.api.web.CookieSpec;
import core.framework.api.web.MultipartFile;
import core.framework.api.web.Request;
import core.framework.api.web.Session;
import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author Dylan
 */
public class CodeBuilderNgApp extends App {
    @Override
    protected void initialize() {
        try {
            CodeBuilderNgModule<Controller> controllerCodeBuilderNgModule = new CodeBuilderNgModule<>();
            Controller controller =controllerCodeBuilderNgModule.run();
            try {
                controller.execute(new Request() {
                    @Override
                    public String requestURL() {
                        return null;
                    }

                    @Override
                    public String scheme() {
                        return null;
                    }

                    @Override
                    public String hostName() {
                        return null;
                    }

                    @Override
                    public String path() {
                        return null;
                    }

                    @Override
                    public HTTPMethod method() {
                        return null;
                    }

                    @Override
                    public Optional<String> header(String name) {
                        return null;
                    }

                    @Override
                    public <T> T pathParam(String name, Class<T> valueClass) {
                        return null;
                    }

                    @Override
                    public Optional<String> queryParam(String name) {
                        return null;
                    }

                    @Override
                    public Optional<String> formParam(String name) {
                        return null;
                    }

                    @Override
                    public Optional<MultipartFile> file(String name) {
                        return null;
                    }

                    @Override
                    public <T> T bean(Type instanceType) {
                        return null;
                    }

                    @Override
                    public String clientIP() {
                        return null;
                    }

                    @Override
                    public Optional<String> cookie(CookieSpec spec) {
                        return null;
                    }

                    @Override
                    public Session session() {
                        return null;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
