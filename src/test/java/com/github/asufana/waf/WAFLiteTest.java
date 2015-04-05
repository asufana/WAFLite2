package com.github.asufana.waf;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.github.asufana.waf.interfaces.*;
import com.github.asufana.waf.testutils.*;

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
    
}
