package org.myorg.http;

import org.myorg.notation.RequestMapping;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {

    static Map<String, Method> endPoints = new HashMap<>();

    public static void run(String... args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(32000);
        } catch (IOException e) {
            System.out.println("Couldn't listen on port: 32000");
            System.exit(1);
        }

        getDefinedRequest(args);

        Socket clientSocket = null;
        while (!serverSocket.isClosed()) {
            try {
                System.out.println("Ready to receive");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Accept failed");
                System.exit(1);
            }

            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter output = new PrintWriter(outputStream, true);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean firstLine = true;
            String path = "/Simple";
            while ((inputLine = input.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    if (!inputLine.split(" ")[1].equals("/")) {
                        path = inputLine.split(" ")[1];
                    }
                    firstLine = false;
                }
                if (!input.ready()) {
                    break;
                }
            }

            Object value;
            if (endPoints.containsKey(path)) {
                try {
                   value = (String) endPoints.get(path).invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } else {
                value =  List.of(path, endPoints.get(path));
            }

            output.println(value);

            output.close();
            input.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static void getDefinedRequest(String[] args) {
        for (String arg: args) {
            Class mainClass = null;
            try {
                mainClass = Class.forName(arg);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            List<Method> methods = Arrays.stream(mainClass.getMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class)).toList();
            List<String> path = methods.stream()
                    .map((Method method) -> method.getAnnotation(RequestMapping.class)).map((RequestMapping::path)).toList();
            for (int position = 0; position < methods.size(); position++) {
                List<Object> tuple = List.of(path.get(position), methods.get(position));
                System.out.println(tuple);
                endPoints.put(path.get(position), methods.get(position));
            }
        }
    }

}
