package com.github.asufana.waf.functions.route;

import io.undertow.server.*;

import java.util.*;

import com.github.asufana.waf.functions.*;

public class RouteDefList {
    
    private final List<RouteDef> routes;
    
    public RouteDefList(final List<RouteDef> routes) {
        this.routes = routes != null && routes.size() != 0
                ? routes
                : Collections.emptyList();
    }
    
    public HttpHandler httpHandler() {
        return HandlerBuilder.build(this);
    }
    
    public Optional<RouteDef> findRoute(final Request request) {
        return routes.stream()
                     .filter(route -> route.matchPath(request.relativePath()))
                     .findFirst();
    }
}
