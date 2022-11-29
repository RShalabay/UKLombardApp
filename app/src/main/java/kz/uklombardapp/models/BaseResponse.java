package kz.uklombardapp.models;

public class BaseResponse {
    public boolean success;
    public String msg;

    public BaseResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
