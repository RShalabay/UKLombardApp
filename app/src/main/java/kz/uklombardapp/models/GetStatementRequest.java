package kz.uklombardapp.models;

public class GetStatementRequest {
    public String iin;
    public String psw;
    public String token;

    public GetStatementRequest(String iin, String psw, String token) {
        this.iin = iin;
        this.psw = psw;
        this.token = token;
    }
}
