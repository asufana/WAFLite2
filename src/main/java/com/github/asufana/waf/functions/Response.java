package com.github.asufana.waf.functions;

import lombok.*;

@Getter
public class Response {
    
    private String renderStrings;
    
    public Response() {}
    
    public void render(final String renderString) {
        renderStrings = renderString;
    }
    
}
