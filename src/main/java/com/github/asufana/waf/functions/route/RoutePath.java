package com.github.asufana.waf.functions.route;

import java.util.*;
import java.util.regex.*;

import org.slf4j.*;

public class RoutePath {
    private static final Logger logger = LoggerFactory.getLogger(RoutePath.class);
    
    private final String path;
    private final Pattern pathRegex;
    private final Map<String, String> pathParamNames;
    
    public RoutePath(final String path) {
        this.path = path;
        pathRegex = Pattern.compile(parsePathRegex(path),
                                    Pattern.CASE_INSENSITIVE);
        pathParamNames = parsePathParamNames(path);
    }
    
    private String parsePathRegex(final String path) {
        //e.g. /user/:name/id -> ^/user/.*?/id$
        return String.format("^%s$", path.replaceAll(":[^/]*", ".*?"));
    }
    
    private Map<String, String> parsePathParamNames(final String path) {
        return RoutePathParamsParser.parse(path);
    }
    
    public boolean match(final String path) {
        final boolean match = pathRegex.matcher(path).find();
        logger.info(String.format("%s Path:%s, Pttn:%s", match
                ? "o"
                : "x", path, pathRegex));
        return match;
    }
    
    public String value() {
        return path;
    }
    
    public boolean hasParams() {
        return pathParamNames != null && pathParamNames.size() != 0;
    }
    
    public Optional<String> paramRegex(final String name) {
        return Optional.ofNullable(pathParamNames.get(name));
    }
}
