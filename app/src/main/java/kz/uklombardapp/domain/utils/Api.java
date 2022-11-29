package kz.uklombardapp.domain.utils;

import kz.uklombardapp.models.CheckUserResponse;
import kz.uklombardapp.models.GetStatementRequest;
import kz.uklombardapp.models.GetStatementResponse;
import kz.uklombardapp.models.LoginRequest;
import kz.uklombardapp.models.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @GET("mobileAPI_check.php")
    Call<CheckUserResponse> checkUser(@Query("iin") String login);

    @POST("mobileAPI_stat.php")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("mobileAPI_stat.php")
    Call<GetStatementResponse> getStatement(@Body GetStatementRequest request);
}
