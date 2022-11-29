package kz.uklombardapp.domain.utils;

import kz.uklombardapp.models.User;

public class Config {
    private static Config instance;
    public User user;

    private Config() {

    }

    public static Config getInsrance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
}
