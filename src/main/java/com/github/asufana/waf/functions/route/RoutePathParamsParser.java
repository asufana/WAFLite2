package com.github.asufana.waf.functions.route;

import java.util.*;
import java.util.stream.*;

public class RoutePathParamsParser {
    
    /**
     * <pre>
     * Route Path: /hello/country/:country/name/:name
     * 
     * -> new HashMap<>(){{
     *   put("country", "^/hello/country/(.*?)/name/.*?$");
     *   put("name",    "^/hello/country/.*?/name/(.*?)$");
     * }};
     * </pre>
     */
    static Map<String, String> parse(final String path) {
        
        // /hello/country/:country/name/:name -> country, name
        final List<String> paramNames = Arrays.asList(path.split("/"))
                                              .stream()
                                              .filter(p -> p.startsWith(":"))
                                              .map(p -> p.substring(1,
                                                                    p.length()))
                                              .collect(Collectors.toList());
        
        final Map<String, String> pathParamRegexMap = new HashMap<>();
        for (final String name : paramNames) {
            final String regex = path.replaceAll(":" + name, "(.*?)")
                                     .replaceAll(":[^/]*", ".*?");
            pathParamRegexMap.put(name, "^" + regex + "$");
        }
        return pathParamRegexMap;
    }
}
