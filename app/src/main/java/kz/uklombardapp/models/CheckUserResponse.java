package kz.uklombardapp.models;

public class CheckUserResponse extends BaseResponse{
    public boolean IsChecked;

    public CheckUserResponse(boolean success, String msg, boolean IsChecked) {
        super(success, msg);
        this.IsChecked = IsChecked;
    }
}
