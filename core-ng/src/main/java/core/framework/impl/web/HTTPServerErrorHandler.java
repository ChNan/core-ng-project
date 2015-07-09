package core.framework.impl.web;

import core.framework.api.exception.Warning;
import core.framework.api.http.ContentTypes;
import core.framework.api.http.HTTPStatus;
import core.framework.api.log.ActionLogContext;
import core.framework.api.util.Exceptions;
import core.framework.api.web.ErrorHandler;
import core.framework.api.web.Response;
import core.framework.api.web.ResponseImpl;
import core.framework.api.web.exception.BadRequestException;
import core.framework.api.web.exception.ForbiddenException;
import core.framework.api.web.exception.MethodNotAllowedException;
import core.framework.api.web.exception.NotFoundException;
import core.framework.api.web.exception.UnauthorizedException;
import core.framework.api.web.exception.ValidationException;
import core.framework.impl.web.exception.ErrorResponse;
import core.framework.impl.web.exception.ValidationErrorResponse;
import core.framework.impl.web.response.ResponseHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author neo
 */
public class HTTPServerErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(HTTPServerErrorHandler.class);
    private final ResponseHandler responseHandler;
    ErrorHandler customErrorHandler;

    public HTTPServerErrorHandler(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    public void handleError(Throwable e, HttpServerExchange exchange, RequestImpl request) {
        ActionLogContext.put(ActionLogContext.ERROR_MESSAGE, e.getMessage());
        ActionLogContext.put(ActionLogContext.EXCEPTION_CLASS, e.getClass().getCanonicalName());

        if (e.getClass().isAnnotationPresent(Warning.class)) {
            logger.warn(e.getMessage(), e);
        } else {
            logger.error(e.getMessage(), e);
        }

        if (exchange.isResponseStarted()) {
            logger.error("response was sent, discard the current http transaction");
            return;
        }

        renderErrorPage(e, exchange, request);
    }

    private void renderErrorPage(Throwable e, HttpServerExchange exchange, RequestImpl request) {
        try {
            Response errorResponse = null;
            if (customErrorHandler != null) {
                Optional<Response> customErrorResponse = customErrorHandler.handle(request, e);
                if (customErrorResponse.isPresent()) errorResponse = customErrorResponse.get();
            }
            String accept = exchange.getRequestHeaders().getFirst(Headers.ACCEPT);
            if (errorResponse == null) errorResponse = defaultErrorResponse(e, accept);
            responseHandler.handle((ResponseImpl) errorResponse, exchange);
        } catch (Throwable error) {
            if (exchange.isResponseStarted()) {
                logger.error("response was sent, discard the current http transaction", error);
                return;
            }
            renderDefaultErrorPage(error, exchange);
        }
    }

    private Response defaultErrorResponse(Throwable e, String accept) {
        HTTPStatus status;
        if (e instanceof BadRequestException || e instanceof ValidationException) {
            status = HTTPStatus.BAD_REQUEST;
        } else if (e instanceof MethodNotAllowedException) {
            status = HTTPStatus.METHOD_NOT_ALLOWED;
        } else if (e instanceof NotFoundException) {
            status = HTTPStatus.NOT_FOUND;
        } else if (e instanceof UnauthorizedException) {
            status = HTTPStatus.UNAUTHORIZED;
        } else if (e instanceof ForbiddenException) {
            status = HTTPStatus.FORBIDDEN;
        } else {
            status = HTTPStatus.INTERNAL_SERVER_ERROR;
        }

        if (accept != null && accept.contains("application/json")) {
            Object bean;
            if (e instanceof ValidationException) {
                bean = validationErrorBody((ValidationException) e);
            } else {
                bean = errorBody(e);
            }
            return Response.bean(bean, status);
        } else {
            return Response.text(errorHTML(e), status, ContentTypes.TEXT_HTML);
        }
    }

    private String errorHTML(Throwable e) {
        return "<html><body><h1>Error</h1><p>" + e.getMessage() + "</p><pre>" + Exceptions.stackTrace(e) + "</pre></body></html>";
    }

    private void renderDefaultErrorPage(Throwable e, HttpServerExchange exchange) {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, ContentTypes.TEXT_HTML);
        exchange.setResponseCode(HTTPStatus.INTERNAL_SERVER_ERROR.code);
        ActionLogContext.put("responseCode", String.valueOf(exchange.getResponseCode()));
        exchange.getResponseSender().send(errorHTML(e));
    }

    private ErrorResponse errorBody(Throwable e) {
        ErrorResponse response = new ErrorResponse();
        response.message = e.getMessage();
        response.stackTrace = Exceptions.stackTrace(e);
        return response;
    }

    private ValidationErrorResponse validationErrorBody(ValidationException e) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.message = e.getMessage();
        response.fieldErrors = e.fieldErrors;
        return response;
    }
}