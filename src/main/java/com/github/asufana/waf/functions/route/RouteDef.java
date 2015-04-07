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
            final int numOfParams = method.getParameterCount();
            if (numOfParams == 0) {
                return (String) method.invoke(instance);
            }
            else {
                //route settings: @Route("/group/:group/name/:name")
                //requset url:    /group/sales/name/hana
                // -> paramValues = {"sales", "hana"}
                final Object[] paramValues = parseParamValues(request,
                                                              numOfParams);
                return (String) method.invoke(instance, paramValues);
            }
        }
        catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new WAFLiteException(e);
        }
    }
    
    private Object[] parseParamValues(final Request request,
                                      final int numOfParams) {
        final Object[] paramValues = new String[numOfParams];
        final Parameter[] params = method.getParameters();
        for (int i = 0; i < numOfParams; i++) {
            final Parameter param = params[i];
            final Optional<String> map = path.paramRegex(param.getName())
                                             .map(regex -> request.relativePath()
                                                                  .replaceAll(regex,
                                                                              "$1"));
            paramValues[i] = map.orElse(null);
        }
        return paramValues;
    }
}
