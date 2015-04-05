package com.github.asufana.waf;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.github.asufana.waf.annotations.*;
import com.github.asufana.waf.interfaces.*;
import com.github.asufana.waf.testutils.*;
import com.github.asufana.waf.testutils.Http.HttpResponse;

public class WAFLiteTest {
    
    private final Integer port = 8888;
    private Server server;
    
    @Before
    public void before() throws Exception {
        server = new WebApp(port).start();
        assertThat(Netstat.isPortOpen(port), is(true));
    }
    
    @After
    public void after() throws Exception {
        server.stop();
        assertThat(Netstat.isPortOpen(port), is(false));
    }
    
    @Test
    public void testRouteNotFound() throws Exception {
        assertThat(Http.get(port, "/no_route").code(), is(404));
    }
    
    @Test
    public void testRoutingWithoutParams() throws Exception {
        final HttpResponse res = Http.get(port, "/hello");
        assertThat(res.code(), is(200));
        assertThat(res.contents(), is("Hello!"));
    }
    
    @Test
    public void testRoutingWithOneParam() throws Exception {
        final HttpResponse res = Http.get(port, "/hello/user/hana");
        assertThat(res.code(), is(200));
        assertThat(res.contents(), is("Hello hana"));
    }
    
    public static class WebApp extends WAFLite {
        
        public WebApp(final Integer port) {
            super(port);
        }
        
        @Route("/hello")
        public String hello() {
            return "Hello!";
        }
        
        @Route("/hello/user/:name")
        public String hello(final String name) {
            return "Hello " + name;
        }
    }
}
