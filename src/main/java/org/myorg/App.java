package org.myorg;

import org.myorg.notation.RequestMapping;

import java.io.IOException;

import static org.myorg.http.Server.run;

public class App {
    public static void main(String[] args) throws IOException {
        run(args);
    }

    //    public static void paths() throws ClassNotFoundException {
//        Map<String, Method> endPoints = new HashMap<>();
//        Class mainClass = Class.forName("org.myorg.App");
//        List<Method> methods = Arrays.stream(mainClass.getMethods())
//                .filter(method -> method.isAnnotationPresent(RequestMapping.class)).toList();
//        List<String> path = methods.stream()
//                .map((Method method) -> method.getAnnotation(RequestMapping.class)).map((RequestMapping::path)).toList();
//        for (int position = 0; position < methods.size(); position++) {
//            List<Object> tuple = List.of(path.get(position), methods.get(position));
//            System.out.println(tuple);
//            endPoints.put(path.get(position), methods.get(position));
//        }
//    }
}
