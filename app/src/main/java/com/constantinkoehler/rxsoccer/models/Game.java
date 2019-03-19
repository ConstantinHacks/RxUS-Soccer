package com.constantinkoehler.rxsoccer.models;

import com.google.gson.annotations.SerializedName;

public class Game implements Comparable<Game> {
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

    @SerializedName("Attendance")
    private final String attendance;

    @SerializedName("Result")
    private final int[] result;

    @SerializedName("Venue")
    private final String venue;

    @SerializedName("Goal Scorers")
    private final String goalScorers;

    public Game(com.constantinkoehler.rxsoccer.models.oid oid, numberDouble nd, String usTeam, String opponentTeam, String competition, String watch, String time, String attendance, int[] result, String venue, String goalScorers) {
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
    }

    public double getUnixTime(){
        return nd.getTimeStamp();
    }

    public numberDouble getNd() {
        return nd;
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

    @Override
    public int compareTo(Game o) {
        if(this.getNd() == null && o.getNd() != null){
            return 1;
        } else if(this.getNd() != null && o.getNd() == null){
            return -1;
        } else if(this.getNd() == null && o.getNd() == null){
            return 0;
        }
        return (int) (o.getUnixTime() - this.getUnixTime());
    }

}

class oid {
    @SerializedName("$oid")
    private final String id;

    public oid(String id) {
        this.id = id;
    }
}

class numberDouble {
    @SerializedName("$numberDouble")
    private final double timeStamp;

    public numberDouble(double timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getTimeStamp() {
        return timeStamp;
    }
}