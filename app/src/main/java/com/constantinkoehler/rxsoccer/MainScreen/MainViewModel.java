package com.constantinkoehler.rxsoccer.MainScreen;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.constantinkoehler.rxsoccer.models.Game;
import com.constantinkoehler.rxsoccer.networking.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainViewModel extends ViewModel {

    public List<Game> games;
    public final String tag = "Game Downloader";


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
                            ArrayList<Game> allGames = gson.fromJson(responseString,gameType);
                            games = allGames.subList(allGames.size() - 21, allGames.size() - 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public List<Game> getGames() {
        return games;
    }

}
