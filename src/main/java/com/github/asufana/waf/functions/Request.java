package com.github.asufana.waf.functions;

import io.undertow.server.*;
import io.undertow.util.*;

import java.util.*;

public class Request {
    
    private final HttpServerExchange exchange;
    
    public Request(final HttpServerExchange exchange) {
        this.exchange = exchange;
    }
    
    public HttpString httpMethod() {
        return exchange.getRequestMethod();
    }
    
    public String relativePath() {
        return exchange.getRelativePath();
    }
    
    public HeaderMap headers() {
        return exchange.getRequestHeaders();
    }
    
    public Map<String, Deque<String>> parameters() {
        return exchange.getPathParameters();
    }
    
    public Optional<String> getParam(final String paramName) {
        final Deque<String> param = parameters().get(paramName);
        return Optional.ofNullable(param != null
                ? param.element()
                : null);
    }
    
}
