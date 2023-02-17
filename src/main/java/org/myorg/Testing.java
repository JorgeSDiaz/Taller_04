package org.myorg;

import org.myorg.notation.RequestMapping;

import java.io.IOException;

import static org.myorg.http.Server.*;

public class Testing {
    @RequestMapping(path = "/hello")
    public static String hello() {
        return "Hello World!";
    }

    public static String alo() {
        return "alo";
    }

    @RequestMapping(path = "/bye")
    public static String adios() {
        return "Hasta Luego";
    }

    public static String bye() {
        return "bye";
    }
}
