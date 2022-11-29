package kz.uklombardapp.domain.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import kz.uklombardapp.domain.utils.Api;
import kz.uklombardapp.domain.utils.RetrofitUtil;
import kz.uklombardapp.models.CheckUserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckUserViewModel extends AndroidViewModel {
    private Api api;
    private MutableLiveData<String> result = new MutableLiveData<>();

    public CheckUserViewModel(Application application) {
        super(application);
        this.api = RetrofitUtil.api(getApplication());
    }

    public LiveData<String> checkUser(String iin) {
        this.api.checkUser(iin)
                .enqueue(new Callback<CheckUserResponse>() {
                    @Override
                    public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                        if (response.body().success) {
                            if (!response.body().IsChecked) {
                                result.postValue(response.body().msg);
                            } else {
                                result.postValue("true");
                            }
                        } else {
                            result.postValue(response.body().msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                        result.postValue(t.getLocalizedMessage());
                    }
                });
        return result;
    }
}
