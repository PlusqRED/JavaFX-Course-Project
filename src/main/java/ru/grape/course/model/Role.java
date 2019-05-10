package ru.grape.course.model;

public enum Role {
    ADMIN(1), EXPERT(2), USER(3);


    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Role identify(int id) {
        switch (id) {
            case 1:
                return ADMIN;
            case 2:
                return EXPERT;
            case 3:
                return USER;
            default:
                return null;
        }
    }
}