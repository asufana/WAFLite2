package com.github.asufana.waf.functions.route;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class RoutePathParamsParserTest {
    
    /**
     * <pre>
     * Route Path: /hello/country/:country/name/:name
     * 
     * -> new HashMap<>(){{
     *   put("country", "^/hello/country/(.*?)/name/.*?$");
     *   put("name",    "^/hello/country/.*?/name/(.*?)$");
     * }};
     * </pre>
     */
    @Test
    public void testParse() {
        final Map<String, String> map = RoutePathParamsParser.parse("/hello/country/:country/name/:name");
        assertThat(map.size(), is(2));
        assertThat(map.get("country"), is("^/hello/country/(.*?)/name/.*?$"));
        assertThat(map.get("name"), is("^/hello/country/.*?/name/(.*?)$"));
    }
    
}
