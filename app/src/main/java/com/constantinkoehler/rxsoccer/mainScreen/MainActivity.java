package com.constantinkoehler.rxsoccer.mainScreen;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.constantinkoehler.rxsoccer.adapters.GameAdapter;
import com.constantinkoehler.rxsoccer.R;
import com.constantinkoehler.rxsoccer.gameDetails.GameDetailsActivity;
import com.constantinkoehler.rxsoccer.models.Game;

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
