package com.constantinkoehler.rxsoccer.mainScreen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.constantinkoehler.rxsoccer.models.Game;
import com.constantinkoehler.rxsoccer.networking.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainViewModel extends ViewModel {
    public List<Game> games = new ArrayList<>();
    private MutableLiveData<List<Game>> gamesList;
    private String tag = "MainViewModel";
    private NetworkManager networkManager;

    @Inject
    public MainViewModel(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    public MutableLiveData<List<Game>> getGamesList() {
        if (gamesList == null) {
            gamesList = new MutableLiveData<>();
            fetchGameData();
        }
        return gamesList;
    }

    public void fetchGameData(){
        final Gson gson = new Gson();
        games.clear();

        networkManager.getGameData()
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
                            gamesList.setValue(games);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
