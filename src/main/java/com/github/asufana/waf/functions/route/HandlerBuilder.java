package com.github.asufana.waf.functions.route;

import io.undertow.server.*;
import io.undertow.util.*;

import java.util.*;

import com.github.asufana.waf.functions.*;

public class HandlerBuilder {
    
    public static HttpHandler build(final RouteDefList routes) {
        return new HttpHandler() {
            @Override
            public void handleRequest(final HttpServerExchange exchange) throws Exception {
                final Request request = new Request(exchange);
                final Response response = new Response();
                final Optional<RouteDef> route = routes.findRoute(request);
                if (route.isPresent()) {
                    exchange.getResponseSender().send(route.get().exec());
                }
                else {
                    exchange.setResponseCode(404);
                }
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
                                                  "text/plain");
            }
        };
    }
}
