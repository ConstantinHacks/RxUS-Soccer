package com.constantinkoehler.rxsoccer.mainScreen;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.constantinkoehler.rxsoccer.DaggerMainViewModelComponent;
import com.constantinkoehler.rxsoccer.MainViewModelComponent;
import com.constantinkoehler.rxsoccer.adapters.GameAdapter;
import com.constantinkoehler.rxsoccer.R;
import com.constantinkoehler.rxsoccer.gameDetails.GameDetailsActivity;
import com.constantinkoehler.rxsoccer.models.Game;
import com.constantinkoehler.rxsoccer.utils.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public MainViewModel viewModel;
    private GameAdapter adapter = new GameAdapter();
    private ListView gamelist;
    private FloatingActionButton filterButton;
    private TextView headerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModelComponent component = DaggerMainViewModelComponent.create();
        viewModel = component.getMainViewModel();

        gamelist = findViewById(R.id.gameList);
        filterButton = findViewById(R.id.filterButton);
        headerTextView = findViewById(R.id.headerTextView);

        filterButton.setOnClickListener(v -> createFilterDialog());

        viewModel.getGamesList().observe(this, games -> {
            adapter.setAllGames(games);
            gamelist.setAdapter(adapter);
            gamelist.setSelection(adapter.getFirstUpcomingGame());
            gamelist.setOnItemClickListener((parent, view, position, id) -> {
                Game selectedGame = adapter.getGameForIndex(position);
                Intent intent = new Intent(MainActivity.this, GameDetailsActivity.class);
                intent.putExtra(GameDetailsActivity.GAME_EXTRA, selectedGame);
                startActivity(intent);
            });
            setHeader("All");
        });

    }

    public void setHeader(String team){
        @SuppressLint("DefaultLocale") String headerText = String.format("%s Games",team);
        headerTextView.setText(headerText);
    }

    public void createFilterDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
        builderSingle.setIcon(R.drawable.ic_filter);
        builderSingle.setTitle("Select a Team:");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
        for (String team: Constants.TEAMS) {
            arrayAdapter.add(team);
        }

        builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

        builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
            String strName = arrayAdapter.getItem(which);
            adapter.getFilter().filter(strName);
            setHeader(strName);
        });
        builderSingle.show();
    }

}
