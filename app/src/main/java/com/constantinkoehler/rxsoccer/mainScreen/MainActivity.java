package com.constantinkoehler.rxsoccer.mainScreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.constantinkoehler.rxsoccer.adapters.GameAdapter;
import com.constantinkoehler.rxsoccer.R;
import com.constantinkoehler.rxsoccer.gameDetails.GameDetailsActivity;
import com.constantinkoehler.rxsoccer.models.Game;
import com.constantinkoehler.rxsoccer.networking.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
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
    private String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new MainViewModel();
        gamelist = findViewById(R.id.gameList);

        viewModel.getGamesList().observe(this, games -> {
            adapter.setGames(games);
            gamelist.setAdapter(adapter);
            gamelist.setOnItemClickListener((parent, view, position, id) -> {
                Game selectedGame = viewModel.getGameForIndex(position);
                Intent intent = new Intent(MainActivity.this, GameDetailsActivity.class);
                intent.putExtra(GameDetailsActivity.GAME_EXTRA, selectedGame);
                startActivity(intent);
            });
        });
    }

}
