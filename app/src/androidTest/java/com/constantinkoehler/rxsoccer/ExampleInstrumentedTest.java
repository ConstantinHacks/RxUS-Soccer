package com.constantinkoehler.rxsoccer;

import android.content.Context;

import com.constantinkoehler.rxsoccer.models.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.constantinkoehler.rxsoccer", appContext.getPackageName());
    }

    public List<Game> getGames(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        String json = null;
        Gson gson = new Gson();

        try {
            InputStream is = appContext.getAssets().open("sampledata.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Type gameType = new TypeToken<List<Game>>(){}.getType();

        return gson.fromJson(json,gameType);
    }

    @Test
    public void parseJSON(){
        assertEquals(getGames().size(),20);
    }

    @Test
    public void GameSorting(){
        List<Game> games = getGames();
        Collections.sort(games);
        assertEquals(games.get(0).getID(),"5c92986cb65aacd0bfd47204");
        assertEquals(games.get(games.size()-1).getID(),"5c92986cb65aacd0bfd471fb");
    }
}
