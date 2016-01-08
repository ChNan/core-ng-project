package test.order;

import config.Config;
import context.ModuleContext;
import module.Module;
import web.demo.OrderWebService;
import web.demo.OrderWebServiceImpl;

/**
 * @author ChNan
 */
public class OrderModule extends Config implements Module {

    public OrderModule(ModuleContext context) {
        super.context = context;
    }

    @Override
    public void initialized() {
        api().service(OrderWebService.class, context.beanFactory.bind(OrderWebServiceImpl.class));
    }
}
