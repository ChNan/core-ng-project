package web.demo;

import http.annotation.GET;
import http.annotation.RequestPath;

/**
 * @author Dylan
 */
public interface OrderWebService {

    @RequestPath(value = "/order/{id}")
    @GET
    public String getOrder();

}