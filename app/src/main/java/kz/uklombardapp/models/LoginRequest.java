package kz.uklombardapp.models;

public class LoginRequest {
    public String iin;
    public String code;

    public LoginRequest(String i, String c) {
        this.iin = i;
        this.code = c;
    }
}
