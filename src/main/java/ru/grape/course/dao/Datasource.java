package ru.grape.course.dao;

import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Properties;

public class Datasource {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;
    private static Datasource instance;

    public static Datasource getInstance() {
        if(instance == null) {
            synchronized (Datasource.class) {
                if(instance == null) {
                    instance = new Datasource();
                }
            }
        }
        return instance;
    }

    private Datasource() {
        connect();
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    private void connect() {
        System.out.println("Connecting...");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(getClass().getResource("/properties/server.properties").toURI())));
            String host = properties.getProperty("server.host");
            int port = Integer.parseInt(properties.getProperty("server.port"));
            socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
