package com.constantinkoehler.rxsoccer;

import com.constantinkoehler.rxsoccer.mainScreen.MainViewModel;

import dagger.Component;

@Component
public interface MainViewModelComponent {
    MainViewModel getMainViewModel();
}
