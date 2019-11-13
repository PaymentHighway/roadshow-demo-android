package io.paymenthighway.phroadshowdemo;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.InputStream;
import java.util.Properties;

import io.paymenthighway.phroadshowdemo.Service.ApiService;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class ApiActivity extends AppCompatActivity {

    protected Properties properties;
    protected ApiService apiService;
    protected CompositeDisposable compositeDisposable;
    protected String user;


    protected void initApiActivity() {
        compositeDisposable = new CompositeDisposable();
        loadProperties();
        authUser();
        initApiService();
    }

    protected void loadProperties() {
        properties = new Properties();
        AssetManager assetManager = getBaseContext().getAssets();
        try {
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
        } catch (Exception e) {
            Log.e("PHDEMO", "Cannot load properties");
        }
    }


    protected void authUser() {
        /* TODO make user auth */
        user = properties.getProperty("user");
    }

    protected void initApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(properties.getProperty("api.base_url"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    protected void printError(Throwable e) {
        Log.e("PHDEMO", e.getMessage());
    }

    protected void gotoAction(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }
}
