package org.myorg;

import org.myorg.framework.notation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.myorg.http.Server.run;

public class App {
    public static void main(String[] args) throws IOException {
        run();
    }

    @RequestMapping(path = "/bye")
    public static String adios() {
        return "Hasta Luego";
    }

    @RequestMapping(path = "/hello")
    public static String hello() {
        return "Hello World!";
    }

    @RequestMapping(path = "/files/{file}", files = true)
    public static File getFile(String file) {
        return new File("src/main/resources" + file);
    }
}
