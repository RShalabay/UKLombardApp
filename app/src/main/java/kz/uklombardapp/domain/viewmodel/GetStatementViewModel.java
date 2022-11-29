package kz.uklombardapp.domain.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import kz.uklombardapp.domain.utils.Api;
import kz.uklombardapp.domain.utils.RetrofitUtil;
import kz.uklombardapp.models.GetStatementRequest;
import kz.uklombardapp.models.GetStatementResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetStatementViewModel extends AndroidViewModel {
    private Api api;
    private MutableLiveData<GetStatementResponse> result = new MutableLiveData<>();

    public GetStatementViewModel(Application application) {
        super(application);
        this.api = RetrofitUtil.api(getApplication());
    }

    public MutableLiveData<GetStatementResponse> getStatement(String iin, String psw, String token) {
        this.api.getStatement(new GetStatementRequest(iin, psw, token))
                .enqueue(new Callback<GetStatementResponse>() {
                    @Override
                    public void onResponse(Call<GetStatementResponse> call, Response<GetStatementResponse> response) {
                       if (response.body().success) {
                           result.postValue(response.body());
                       } else {
                           result.postValue(new GetStatementResponse(false,
                                   response.body().msg,
                                   null,
                                   null,
                                   null,
                                   null,
                                   null,
                                   null));
                       }
                    }

                    @Override
                    public void onFailure(Call<GetStatementResponse> call, Throwable t) {
                        result.postValue(new GetStatementResponse(false,
                                t.getLocalizedMessage(),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null));
                    }
                });
        return result;
    }
}
