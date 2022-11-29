package kz.uklombardapp.models;

public class CheckUserRequest {
    public String login;

    public CheckUserRequest setLogin(String login) {
        this.login = login;
        return this;
    }
}
