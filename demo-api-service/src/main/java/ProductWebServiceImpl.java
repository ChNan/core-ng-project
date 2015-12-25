
import javax.inject.Inject;

/**
 * @author neo
 */
public class ProductWebServiceImpl implements ProductWebService {
    @Inject
    ProductService productService;

    @Override
    public ProductView get(String id) {
        ActionLogContext.put("pid", id);
        return productService.get(id);
    }
}
