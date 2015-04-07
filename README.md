

# WAFLite2

<!--[![Build Status](https://travis-ci.org/asufana/WAFLite2.svg?branch=master)](https://travis-ci.org/asufana/WAFLite2)-->

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
    
    @Route("/group/:group/name/:name")
    public String hello(final String group, final String name) {
        return String.format("Hello %s in %s.", name, group);
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
    
    final HttpResponse res02 = Http.get(port, "/group/sales/name/hana");
    assertThat(res02.code(), is(200));
    assertThat(res02.contents(), is("Hello hana in sales"));
    
    server.stop();
}
```

## Settings

If you are using eclipse, check "Store information about method parameters (usable via reflection)" in Java Compiler tab.


