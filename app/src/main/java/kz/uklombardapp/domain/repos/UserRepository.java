package kz.uklombardapp.domain.repos;

import android.content.Context;

import kz.uklombardapp.domain.utils.Config;
import kz.uklombardapp.models.User;


public class UserRepository {
    private Config config;

    public UserRepository(Context context) {
        this.config = Config.getInsrance();
    }

    public void saveUser(String iin, String pass, Integer code, String username) {
        User user = new User();
        user.iin = iin;
        user.pass = pass;
        user.code = code;
        user.username = username;
        config.user = user;
    }

    public User getUser() {
        return config.user;
    }
}
