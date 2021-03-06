package core.framework.impl.web.route;

import core.framework.api.http.HTTPMethod;
import core.framework.api.util.ASCII;
import core.framework.api.util.Strings;

/**
 * @author neo
 */
class ActionInfo {
    private final HTTPMethod method;
    private final String pathPattern;

    ActionInfo(HTTPMethod method, String pathPattern) {
        this.method = method;
        this.pathPattern = pathPattern;
    }

    String action() {
        return "web/" + ASCII.toLowerCase(method.name()) + "-" + transformPathPattern();
    }

    private String transformPathPattern() {
        if ("/".equals(pathPattern)) return "root";

        String[] tokens = Strings.split(pathPattern, '/');
        StringBuilder builder = new StringBuilder(pathPattern.length());
        int index = 0;
        for (String token : tokens) {
            if (token.length() == 0) continue;
            if (index > 0) builder.append('-');
            if (token.startsWith(":")) {
                int paramIndex = token.indexOf('(');
                int endIndex = paramIndex > 0 ? paramIndex : token.length();
                builder.append(token.substring(1, endIndex));
            } else {
                builder.append(token);
            }
            index++;
        }
        return builder.toString();
    }
}
