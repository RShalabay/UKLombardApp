package kz.uklombardapp.models;

public class User {
    public String iin;
    public String pass;
    public Integer code;
    public String username;

    public static User builder() {
        return new User();
    }

    public User setIin(String iin) {
        this.iin = iin;
        return this;
    }

    public User setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public User setCode(Integer code) {
        this.code = code;
        return this;
    }

    public User setUsername(String name) {
        this.username = name;
        return this;
    }
}
