package org.myorg.http;

import org.myorg.framework.enums.ContentType;
import org.myorg.framework.service.Request;

import static org.myorg.framework.notation.ProcessNotation.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Server {

    private static Map<String, Request> endPoints = new HashMap<>();
    private static Map<String, Request> customEndPoints = new HashMap<>();
    private static final Server _instance = new Server();
    private static int defaultPort = 32000;

    public Server(){}

    public static Server run() throws IOException {
        run(defaultPort);
        return _instance;
    }

    private static void run(int port) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Couldn't listen on port: " + port);
            System.exit(1);
        }

        endPoints = paths();
        customEndPoints = customPaths();

        System.out.println(endPoints);
        System.out.println(customEndPoints);

        Socket clientSocket = null;
        while (!serverSocket.isClosed()) {
            try {
                System.out.println("Ready to receive");
                System.out.println("Server is running on: http://localhost:" + port + "/");
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

            if (!endPoints.containsKey(path)) {
                addNewEndpointFromCustomEndpoint(path);
            }

            byte[] value = new byte[0];
            List<String> header = List.of("GET HTTP/1.1 400 Bad Request", "Content-Type: application/json");
            if (endPoints.containsKey(path) && !endPoints.get(path).getContentType().equals(ContentType.NULL)) {
                Request service = endPoints.get(path);
                if (!service.useFiles()) {
                    try {
                        value = ((String)endPoints.get(path).getMethod().invoke(null)).getBytes();
                        header = service.getHeader();
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                } else if (!service.getContentType().equals(ContentType.PLAIN)) {
                    try {
                        Method method = endPoints.get(path).getMethod();
                        String paths = "/" + path.split("/")[path.split("/").length - 1];
                        String filePath = String.valueOf(((File)endPoints.get(path).getMethod()
                                .invoke(null, (Object)paths)).toPath());
                        value = Files.readAllBytes(Paths.get(filePath));
                        header = service.getHeader();
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (header.get(0).contains("400")) {
                endPoints.remove(path);
            }

            for (String line: header) {
                output.println(line);
            }
            output.println();

            if (value.length > 0) {
                outputStream.write(value);
            } else {
                List<String> show = new ArrayList<>();
                show.addAll(endPoints.keySet().stream().toList());
                show.addAll(customPaths().keySet().stream().toList());
                output.println("{ \"EndPoints\":" + (show.stream().sorted().
                        map(key -> "\"" + key + "\"").toList()) + ", \"Error\": \"Service no found\"}");
            }

            output.close();
            input.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static void addNewEndpointFromCustomEndpoint(String path) {
        for (String customPAth: customEndPoints.keySet()) {
            if (path.startsWith("/" + customPAth.split("/")[customPAth.split("/").length - 2])) {
                Request block = customEndPoints.get(customPAth);
                ContentType ctype = ContentType.NULL;
                Method mtd = block.getMethod();

                if (block.useFiles() && path.split("\\.").length > 1) {
                    ctype = ContentType.valueOf(path.split("\\.")[1].toUpperCase());
                } else {
                    ctype = block.getContentType();
                }

                endPoints.put(path, generateRequest(ctype, mtd, block.useFiles()));
            }
        }
    }

}
