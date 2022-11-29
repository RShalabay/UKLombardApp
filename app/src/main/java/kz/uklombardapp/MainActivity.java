package kz.uklombardapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessaging;

import kz.uklombardapp.R;
import kz.uklombardapp.models.Ticket;
import kz.uklombardapp.ui.AlertDialogEventsListener;
import kz.uklombardapp.ui.CheckUserFragment;
import kz.uklombardapp.ui.FragmentsEventsListener;
import kz.uklombardapp.ui.InputAccessCodeFragment;
import kz.uklombardapp.ui.InputCodeFragment;
import kz.uklombardapp.ui.PermissionsFragment;
import kz.uklombardapp.ui.StatementListFragment;
import kz.uklombardapp.ui.TicketFragment;

public class MainActivity extends AppCompatActivity implements FragmentsEventsListener, AlertDialogEventsListener {
    private MenuItem item;
    private Menu menu;
    private boolean needShowMenuItems = false;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        prefs = this.getSharedPreferences("LOMBARD_SETTINGS", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("ApiKey", "74e51a8f32be00bec6655e903135cc2b")
                .putString("device", Build.BRAND + " " + Build.MODEL)
                .apply();

        boolean isAllowed = prefs.getBoolean("isAllowed", false);

        if (!isAllowed) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, new PermissionsFragment(this), null)
                    .commit();
        } else {
            SharedPreferences finalPrefs = prefs;
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.e("firebase token", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        /* Get new FCM registration token */
                        String token = task.getResult();
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("firebase token", msg);
                        /* Пишем токен в настройки */
                        finalPrefs.edit().putString("token", msg).apply();
                    });

            if (savedInstanceState == null) {

                Context context = this;
                prefs = context.getSharedPreferences("LOMBARD_SETTINGS", Context.MODE_PRIVATE);
                String s = prefs.getString("code", "");
                if (s.isEmpty()) {
                    CheckUserFragment fragment = new CheckUserFragment(this);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, fragment, null)
                            .commit();
                } else {
                    String iin = prefs.getString("login", "");
                    String psw = prefs.getString("psw", "");
                    String consumer = prefs.getString("consumer", "");
                    InputAccessCodeFragment fragment = new InputAccessCodeFragment(this, iin, psw, consumer);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container_view, fragment, null)
                            .commit();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        item = menu.findItem(R.id.item1);
        if (this.needShowMenuItems) {
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }
        this.menu = menu;
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        SharedPreferences prefs = this.getSharedPreferences("LOMBARD_SETTINGS", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("code", "")
                .putString("login", "")
                .putString("psw", "")
                .putString("consumer", "")
                .apply();
        logout();
        return true;
    }

    @Override
    public void userIsChecked(String iin, boolean result) {
        InputCodeFragment fragment = new InputCodeFragment(this, iin);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment, null)
                .commit();
    }

    @Override
    public void codeIsChecked(String iin, String psw, boolean result, String consumer) {
        InputAccessCodeFragment fragment = new InputAccessCodeFragment(this, iin, psw, consumer);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment, null)
                .commit();
    }

    @Override
    public void accessCodeIsSaved() {
        this.needShowMenuItems = true;
        item.setVisible(true);
        getMenuInflater().inflate(R.menu.main, menu);
        StatementListFragment fragment = new StatementListFragment(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment, null)
                .commit();
    }

    @Override
    public void ticketHasSelected(Ticket ticket) {
        TicketFragment fragment = new TicketFragment(this, ticket);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logout() {
        this.needShowMenuItems = false;
        item.setVisible(false);
        getMenuInflater().inflate(R.menu.main, menu);
        CheckUserFragment fragment = new CheckUserFragment(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, fragment, null)
                .commit();
    }

    @Override
    public void policyAllowed() {
        SharedPreferences finalPrefs = prefs;
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase token", "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    /* Get new FCM registration token */
                    String token = task.getResult();
                    String msg = getString(R.string.msg_token_fmt, token);
                    Log.d("firebase token", msg);
                    /* Пишем токен в настройки */
                    finalPrefs.edit().putString("token", msg).apply();
                });

        Context context = this;
        prefs = context.getSharedPreferences("LOMBARD_SETTINGS", Context.MODE_PRIVATE);
        String s = prefs.getString("code", "");
        if (s.isEmpty()) {
            CheckUserFragment fragment = new CheckUserFragment(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, fragment, null)
                    .commit();
        } else {
            String iin = prefs.getString("login", "");
            String psw = prefs.getString("psw", "");
            String consumer = prefs.getString("consumer", "");
            InputAccessCodeFragment fragment = new InputAccessCodeFragment(this, iin, psw, consumer);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, fragment, null)
                    .commit();
        }
    }

    @Override
    public void ok() {

    }
}