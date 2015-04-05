package com.github.asufana.waf.functions.route;

import java.lang.reflect.*;

import com.github.asufana.waf.*;
import com.github.asufana.waf.annotations.*;
import com.github.asufana.waf.exceptions.*;

public class RouteDef {
    
    private final Object instance;
    private final Method method;
    private final RoutePath path;
    
    public <T extends WAFLite> RouteDef(final T instance,
            final Method method,
            final Route annotation) {
        this.instance = instance;
        this.method = method;
        path = new RoutePath(annotation.value());
    }
    
    public boolean matchPath(final String relativePath) {
        return path.match(relativePath);
    }
    
    public String exec() {
        try {
            if (!path.hasParams()) {
                return (String) method.invoke(instance);
            }
            else {
                return (String) method.invoke(instance, "hana");
            }
        }
        catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new WAFLiteException(e);
        }
    }
    
}
