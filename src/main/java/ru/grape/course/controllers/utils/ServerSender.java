package ru.grape.course.controllers.utils;

import org.json.JSONObject;
import ru.grape.course.controllers.commons.DaoAction;
import ru.grape.course.dao.Datasource;

import java.io.IOException;

public class ServerSender {

    Datasource datasource = Datasource.getInstance();

    private static ServerSender instance;

    private ServerSender() {

    }

    public void send(JSONObject message, DaoAction action) {
        message.put("action", action);
        datasource.getWriter().println(message);
    }

    public JSONObject retrieve() {
        try {
            return new JSONObject(datasource.getReader().readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ServerSender getInstance() {
        if(instance == null) {
            synchronized (ServerSender.class) {
                if(instance == null) {
                    instance = new ServerSender();
                }
            }
        }
        return instance;
    }
}
