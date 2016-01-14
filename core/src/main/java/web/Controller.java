package web;

import web.request.Request;

/**
 * @author ChNan
 * This is the core class, implement mvc framework
 * 1: Request
 * 2: Execute
 * 3: Response
 */
public interface Controller {
    public String execute(Request request) throws Exception;
}
