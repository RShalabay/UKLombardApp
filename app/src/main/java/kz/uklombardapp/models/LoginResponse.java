package kz.uklombardapp.models;

public class LoginResponse extends BaseResponse{
    public String iin;
    public String consumer;
    public String psw;

    public LoginResponse(boolean success, String msg, String iin, String consumer, String pass) {
        super(success, msg);
        this.iin = iin;
        this.consumer = consumer;
        this.psw = pass;
    }
}
