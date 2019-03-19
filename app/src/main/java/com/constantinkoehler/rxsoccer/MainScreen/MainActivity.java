package com.constantinkoehler.rxsoccer.MainScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.constantinkoehler.rxsoccer.Adapters.GameAdapter;
import com.constantinkoehler.rxsoccer.R;
import com.constantinkoehler.rxsoccer.models.Game;
import com.constantinkoehler.rxsoccer.networking.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public MainViewModel viewModel;
    private GameAdapter adapter = new GameAdapter();
    private ListView gamelist;
    public List<Game> games;
    private String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        viewModel = new MainViewModel();
        gamelist = findViewById(R.id.gameList);
        gamelist.setAdapter(adapter);

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
                        Log.d(tag,"Downloaded " + games.size() + " games");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(tag,e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Response response) {
                        try {
                            assert response.body() != null;
                            String responseString = response.body().string();
                            Type gameType = new TypeToken<List<Game>>(){}.getType();
                            games = gson.fromJson(responseString,gameType);
                            Collections.sort(games);
                            adapter.setGames(games);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
