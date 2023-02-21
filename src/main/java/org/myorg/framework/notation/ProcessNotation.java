package org.myorg.framework.notation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessNotation {
    public static Map<String, Request> paths() {
        return getEndPoints(false);
    }

    public static Map<String, Request> customPaths() {
        return getEndPoints(true);
    }

    private static Map<String, Request> getEndPoints(boolean custom) {
        Map<String, Request> endPoints = new HashMap<>();

        Class mainClass;
        try {
            mainClass = Class.forName("org.myorg.App");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        List<Method> methods = Arrays.stream(mainClass.getMethods())
                .filter((Method method) -> method.isAnnotationPresent(RequestMapping.class))
                .filter((Method method) -> custom ? method.getAnnotation(RequestMapping.class).path().contains("/{")
                        : !method.getAnnotation(RequestMapping.class).path().contains("/{"))
                .toList();
        List<String> path = methods.stream()
                .map((Method method) -> method.getAnnotation(RequestMapping.class))
                .map((RequestMapping::path))
                .toList();

        for (int position = 0; position < methods.size(); position++) {
            ContentType contentType = methods.get(position).getAnnotation(RequestMapping.class).contentType();
            boolean useFiles = methods.get(position).getAnnotation(RequestMapping.class).files();
            endPoints.put(path.get(position), generateRequest(contentType, methods.get(position), useFiles));
        }

        return endPoints;
    }

    public static Request generateRequest(ContentType contentType, Method method, boolean useFiles) {
        Method newMethod = method;
        Boolean newUseFiles = useFiles;

        return new Request() {
            ContentType cType = contentType;
            Method method = newMethod;

            @Override
            public List<String> getHeader() {
                String contentType;
                switch (getContentType()) {
                    case PNG -> contentType = "Content-Type: image/png";
                    case HTML -> contentType = "Content-Type: text/html";
                    default -> contentType = "Content-Type: text/plain";
                }
                return List.of("GET HTTP/1.1 200 OK", contentType);
            }

            @Override
            public Method getMethod() {
                return this.method;
            }

            @Override
            public ContentType getContentType() {
                return this.cType;
            }

            @Override
            public boolean useFiles() {
                return newUseFiles;
            }
        };
    }
}
