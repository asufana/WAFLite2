package com.github.asufana.waf.functions.route;

public class RoutePath {
    
    private final String path;
    
    public RoutePath(final String path) {
        this.path = path;
    }
    
    public boolean match(final String path) {
        return this.path.toLowerCase().equals(path.toLowerCase());
    }
}
