package ru.grape.course.controllers.commons;

import ru.grape.course.model.Client;

public class Session {
    private static Session instance;
    private Client client;
    public static Session getInstance() {
        if(instance == null) {
            synchronized (Session.class) {
                if(instance == null) {
                    instance = new Session();
                }
            }
        }
        return instance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
