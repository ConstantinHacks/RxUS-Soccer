package com.constantinkoehler.rxsoccer.utils;

import android.content.Context;
import android.content.Intent;

import com.constantinkoehler.rxsoccer.models.Game;

import java.util.Calendar;

public class CalendarUtil {
    public static void addGameToCalendar(Game game, Context context){
        Calendar cal = Calendar.getInstance();
        cal.setTime(game.getDateObject());
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*2*1000);
        intent.putExtra("title", game.getGameCalTitle());
        intent.putExtra("eventLocation", game.getVenue());
        context.startActivity(intent);
    }
}
