package kz.uklombardapp.domain.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import kz.uklombardapp.domain.repos.UserRepository;
import kz.uklombardapp.domain.utils.Api;
import kz.uklombardapp.domain.utils.RetrofitUtil;
import kz.uklombardapp.models.LoginRequest;
import kz.uklombardapp.models.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository repository;
    private Api api;
    private MutableLiveData<LoginResponse> result = new MutableLiveData<>();

    public LoginViewModel(Application application) {
        super(application);
        this.repository = new UserRepository(application);
        this.api = RetrofitUtil.api(getApplication());
    }

    public LiveData<LoginResponse> login(String iin, String code) {
        //UserRepository r = this.repository;
        this.api.login(new LoginRequest(iin, code))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        result.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        LoginResponse response = new LoginResponse(false, t.getLocalizedMessage(), null, null, null);
                        result.postValue(response);
                    }
                });
        return result;
    }

    public void saveUser(String iin, String psw, Integer accessCode, String consumer) {
        this.repository.saveUser(iin, psw, accessCode, consumer);
    }

}
