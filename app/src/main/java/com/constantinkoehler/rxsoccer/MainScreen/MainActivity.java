package com.constantinkoehler.rxsoccer.MainScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.constantinkoehler.rxsoccer.R;

public class MainActivity extends AppCompatActivity {

    public MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new MainViewModel();
        viewModel.fetchGameData();
    }

}
