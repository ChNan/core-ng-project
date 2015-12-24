package app;

import app.product.api.ProductView;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author neo
 */
public interface ProductWebService {
    @GET
    @Path("/product/:id")
    ProductView get(@PathParam("id") String id);

}
