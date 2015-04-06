package com.github.asufana.waf.functions.route;

import java.lang.reflect.*;
import java.util.*;

import com.github.asufana.waf.*;
import com.github.asufana.waf.annotations.*;
import com.github.asufana.waf.exceptions.*;
import com.github.asufana.waf.functions.*;

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
    
    public String exec(final Request request) {
        try {
            if (method.getParameterCount() == 0) {
                return (String) method.invoke(instance);
            }
            else {
                final Object[] params = new String[method.getParameterCount()];
                final Integer i = 0;
                for (final Parameter param : method.getParameters()) {
                    final Optional<String> map = path.paramRegex(param.getName())
                                                     .map(regex -> request.relativePath()
                                                                          .replaceAll(regex,
                                                                                      "$1"));
                    params[i] = map.get();
                }
                return (String) method.invoke(instance, params);
            }
        }
        catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new WAFLiteException(e);
        }
    }
}
