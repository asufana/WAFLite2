package com.github.asufana.waf;

import io.undertow.*;

import java.lang.reflect.*;
import java.util.*;

import com.github.asufana.waf.annotations.*;
import com.github.asufana.waf.functions.route.*;
import com.github.asufana.waf.interfaces.*;

public class WAFLite {
    private static final Integer DEFAULT_PORT = 8080;
    
    private final Integer port;
    private Undertow server;
    
    public WAFLite() {
        this(DEFAULT_PORT);
    }
    
    public WAFLite(final Integer port) {
        this.port = port;
    }
    
    public Server start() {
        server = Undertow.builder()
                         .addHttpListener(port, "localhost")
                         .setHandler(detectRoute().httpHandler())
                         .build();
        server.start();
        
        return new Server() {
            @Override
            public void stop() {
                server.stop();
            }
        };
    }
    
    private RouteDefList detectRoute() {
        final List<RouteDef> routes = new ArrayList<RouteDef>();
        for (final Method method : getClass().getMethods()) {
            final Route annotation = method.getAnnotation(Route.class);
            if (annotation != null) {
                routes.add(new RouteDef(this, method, annotation));
            }
        }
        return new RouteDefList(routes);
    }
}
