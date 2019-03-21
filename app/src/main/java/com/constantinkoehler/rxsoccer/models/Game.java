package com.constantinkoehler.rxsoccer.models;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Game implements Comparable<Game>, Serializable {
    @SerializedName("_id")
    private final oid oid;

    @SerializedName("TimeStamp")
    private final numberDouble nd;

    @SerializedName("USTeam")
    private final String usTeam;

    @SerializedName("OpponentTeam")
    private final String opponentTeam;

    @SerializedName("Competition")
    private final String competition;

    @SerializedName("Watch")
    private final String watch;

    @SerializedName("Time")
    private final String time;

    @SerializedName("Date")
    private final String date;

    @SerializedName("Attendance")
    private final String attendance;

    @SerializedName("Result")
    private final int[] result;

    @SerializedName("Venue")
    private final String venue;

    @SerializedName("Goal Scorers")
    private final String goalScorers;

    public Game(com.constantinkoehler.rxsoccer.models.oid oid, numberDouble nd, String usTeam, String opponentTeam, String competition, String watch, String time, String attendance, int[] result, String venue, String goalScorers, String date) {
        this.oid = oid;
        this.nd = nd;
        this.usTeam = usTeam;
        this.opponentTeam = opponentTeam;
        this.competition = competition;
        this.watch = watch;
        this.time = time;
        this.attendance = attendance;
        this.result = result;
        this.venue = venue;
        this.goalScorers = goalScorers;
        this.date = date;
    }

    public String getID() {
        return oid.getId();
    }

    public String getDate() {
        return date;
    }

    public String getUsTeam() {
        return usTeam;
    }

    public String getOpponentTeam() {
        return opponentTeam;
    }

    public String getCompetition() {
        return competition;
    }

    public String getWatch() {
        return watch;
    }

    public String getTime() {
        return time;
    }

    public String getAttendance() {
        return attendance;
    }

    public int[] getResult() {
        return result;
    }

    public String getVenue() {
        return venue;
    }

    public String getGoalScorers() {
        return goalScorers;
    }

    public Date getDateObject(){
        if(nd != null && getUnixTime() != 0){
            return new Date((long) getUnixTime() * 1000);
        } else {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
            try {
                return dateFormat.parse(getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getFlagResource(Context context){
        String regexStr = " U-(\\d+)";
        String flagName =  getOpponentTeam().replaceAll(regexStr,"").replace(" ","_").toLowerCase();

        return context.getResources().getIdentifier(flagName, "drawable", context.getPackageName());
    }

    public boolean isMatchComplete() {
        return getResult().length != 0;
    }

    public String getFormattedDateString(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(getDateObject());
    }

    public String getFormattedTimeString(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setTimeZone(TimeZone.getDefault());
        return timeFormat.format(getDateObject());
    }

    public int getUnixTime(){
        return nd.getTimeStamp();
    }

    public String getResultString(){
        if(isMatchComplete()){
            return getScoreLine();
        } else {
            return getFormattedTimeString();
        }
    }

    public String getScoreLine(){
        String usScore = getResult().length != 0 ? String.valueOf(getResult()[0]) : "-";
        String oppScore = getResult().length != 0 ? String.valueOf(getResult()[1]) : "-";

        return String.format("%s : %s",usScore,oppScore);
    }

    public String getGameCalTitle(){
        return String.format("%s vs. %s",getUsTeam(),getOpponentTeam());
    }

    @Override
    public int compareTo(Game o) {
        return this.getDateObject().compareTo(o.getDateObject());
    }

}

class oid implements Serializable {
    @SerializedName("$oid")
    private final String id;

    public oid(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

class numberDouble implements Serializable {
    @SerializedName("$numberInt")
    private final int timeStamp;

    public numberDouble(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTimeStamp() {
        return timeStamp;
    }
}