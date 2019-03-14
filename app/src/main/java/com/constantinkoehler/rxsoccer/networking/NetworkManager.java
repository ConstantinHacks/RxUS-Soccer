package com.constantinkoehler.rxsoccer.networking;

import android.os.StrictMode;

import com.constantinkoehler.rxsoccer.utils.SecretConstants;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

public class NetworkManager {

    public static Observable<Response> getGameData() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url(SecretConstants.gameURL)
                .get()
                .build();

        return Observable.create(new Observable.OnSubscribe<Response>() {
            @Override public void call(Subscriber<? super Response> subscriber) {
                try {
                    Response response = client.newCall(request).execute();
                    subscriber.onNext(response);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}
