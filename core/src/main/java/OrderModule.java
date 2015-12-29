import config.Config;
import module.Module;
import web.ModuleContext;
import web.demo.OrderWebService;
import web.demo.OrderWebServiceImpl;

/**
 * @author ChNan
 */
public class OrderModule extends Config implements Module {
    private ModuleContext context;

    public OrderModule(ModuleContext context) {
        this.context = context;
    }

    @Override
    public void initialized() {
        api(context).service(OrderWebService.class, context.beanFactory.bind(OrderWebServiceImpl.class));
    }
}
