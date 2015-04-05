package com.github.asufana.waf.functions.route;

import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

import org.slf4j.*;

public class RoutePath {
    private static final Logger logger = LoggerFactory.getLogger(RoutePath.class);
    
    private final String path;
    private final Pattern pathRegex;
    private final List<String> pathParams;
    
    public RoutePath(final String path) {
        this.path = path;
        pathRegex = Pattern.compile(parsePathRegex(path),
                                    Pattern.CASE_INSENSITIVE);
        pathParams = parsePathParams(path);
    }
    
    private String parsePathRegex(final String path) {
        //e.g. /user/:name/id -> ^/user/.*?/id$
        return String.format("^%s$", path.replaceAll(":[^/]*", ".*?"));
    }
    
    private List<String> parsePathParams(final String path) {
        return Arrays.asList(path.split("/"))
                     .stream()
                     .filter(p -> p.startsWith(":"))
                     .map(p -> p.substring(1, p.length()))
                     .collect(Collectors.toList());
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
    
    public Integer numOfParams() {
        return pathParams != null && pathParams.size() != 0
                ? pathParams.size()
                : 0;
    }
}
