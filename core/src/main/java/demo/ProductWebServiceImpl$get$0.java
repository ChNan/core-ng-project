////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package demo;
//
//import app.product.api.ProductView;
//import core.framework.api.http.HTTPStatus;
//import core.framework.api.web.Request;
//import core.framework.api.web.Response;
//
//public class ProductWebServiceImpl$get$0 {
//    final ProductWebService delegate;
//
//    public ProductWebServiceImpl$get$0(ProductWebService var1) {
//        this.delegate = var1;
//    }
//
//    public Response execute(Request var1) throws Exception {
//        String var2 = (String)var1.pathParam("id", String.class);
//        ProductView var3 = this.delegate.get(var2);
//        return Response.bean(var3).status(HTTPStatus.OK);
//    }
//}
