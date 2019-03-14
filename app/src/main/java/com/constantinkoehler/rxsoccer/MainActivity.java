package com.constantinkoehler.rxsoccer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.constantinkoehler.rxsoccer.models.Game;
import com.constantinkoehler.rxsoccer.networking.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public final String activity = "MainActivity";

    public ArrayList<Game> games;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fetchGameData();
    }

    public void fetchGameData(){
        final Gson gson = new Gson();

        NetworkManager.getGameData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {
                        Log.d(activity,"Downloaded " + games.size() + " games");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(activity,e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        try {
                            assert response.body() != null;
                            String responseString = response.body().string();
                            Type gameType = new TypeToken<List<Game>>(){}.getType();
                            games = gson.fromJson(responseString,gameType);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
