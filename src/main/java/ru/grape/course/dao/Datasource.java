package ru.grape.course.dao;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Datasource {
    private static Datasource instance;
    private BufferedReader reader;
    private PrintWriter writer;

    private Datasource() {
        connect();
    }

    public static Datasource getInstance() {
        if (instance == null) {
            synchronized (Datasource.class) {
                if (instance == null) {
                    instance = new Datasource();
                }
            }
        }
        return instance;
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
            properties.load(new FileInputStream("properties/server.properties"));
            String host = properties.getProperty("server.host");
            int port = Integer.parseInt(properties.getProperty("server.port"));
            Socket socket = new Socket(host, port);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
