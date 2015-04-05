package com.github.asufana.waf;

import io.undertow.*;
import io.undertow.server.*;
import io.undertow.util.*;

import java.lang.reflect.*;

import com.github.asufana.waf.annotations.*;
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
        detectRoute();
        server = Undertow.builder()
                         .addHttpListener(port, "localhost")
                         .setHandler(new HttpHandler() {
                             @Override
                             public void handleRequest(final HttpServerExchange exchange) throws Exception {
                                 exchange.getResponseHeaders()
                                         .put(Headers.CONTENT_TYPE,
                                              "text/plain");
                                 exchange.getResponseSender()
                                         .send("Hello World");
                             }
                         })
                         .build();
        server.start();
        
        return new Server() {
            @Override
            public void stop() {
                server.stop();
            }
        };
    }
    
    private void detectRoute() {
        for (final Method method : getClass().getMethods()) {
            final Route route = method.getAnnotation(Route.class);
            if (route != null) {
                System.out.println(route.toString());
            }
        }
    }
    
}
