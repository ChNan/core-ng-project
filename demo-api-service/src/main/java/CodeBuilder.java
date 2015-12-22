//    public core.framework.api.web.Response execute(core.framework.api.web.Request request) throws Exception {
//        java.lang.String id = (java.lang.String) request.pathParam("id", java.lang.String.class);
//        app.product.api.ProductView response = delegate.get(id);
//        return core.framework.api.web.Response.bean(response).status(core.framework.api.http.HTTPStatus.OK);
//    }
//
//    public core.framework.api.web.Response execute(core.framework.api.web.Request request) throws Exception {
//        app.product.api.CreateProductRequest bean = (app.product.api.CreateProductRequest) request.bean(app.product.api.CreateProductRequest.class);
//        delegate.create(bean);
//        return core.framework.api.web.Response.empty().status(core.framework.api.http.HTTPStatus.CREATED);
//    }
//
