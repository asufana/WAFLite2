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
    private final WAFLite waf = new WAFLite(port);
    
    @Test
    public void testServerStartAndStop() throws Exception {
        final Server server = waf.start();
        assertThat(Netstat.isPortOpen(port), is(true));
        
        server.stop();
        assertThat(Netstat.isPortOpen(port), is(false));
    }
    
    @Test
    public void testRouting() throws Exception {
        final Server server = new WebApp(port).start();
        
        final HttpResponse res = Http.get(port, "/hello");
        assertThat(res.code(), is(200));
        assertThat(res.contents(), is("Hello!"));
        
        server.stop();
    }
    
    public static class WebApp extends WAFLite {
        
        public WebApp(final Integer port) {
            super(port);
        }
        
        @Route("/hello")
        public String hello() {
            return "Hello!";
        }
        
    }
    
}
