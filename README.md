

# WAFLite2

[![Build Status](https://travis-ci.org/asufana/WAFLite2.svg?branch=master)](https://travis-ci.org/asufana/WAFLite2)

A Flask-like simple Web Application Framework for Java8.

## Examples

#### Application code

```java
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
```

#### Test code

```java
@Test
public void test() throws Exception {

    final Integer port = 8080;
    final Server server = new WebApp(port).start();
    
    final HttpResponse res01 = Http.get(port, "/hello");
    assertThat(res01.code(), is(200));
    assertThat(res01.contents(), is("Hello!"));
    
    final HttpResponse res02 = Http.get(port, "/hello/user/hana");
    assertThat(res02.code(), is(200));
    assertThat(res02.contents(), is("Hello hana"));
    
    server.stop();
}
```
