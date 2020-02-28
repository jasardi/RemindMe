package com.example.remindme;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TTermine")
public class TerminItem {
    @PrimaryKey(autoGenerate = true)
    private int Id;

    private int prioritaet;
    private String textTermin;
    private String bisZeit;
    private String alarm;

    public TerminItem(int prioritaet, String textTermin, String bisZeit) {
        this.prioritaet = prioritaet;
        this.textTermin = textTermin;
        this.bisZeit = bisZeit;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public int getId(){ return Id; }

    public int getPrioritaet(){ return prioritaet; }

    public String getTextTermin(){
        return textTermin;
    }

    public String getBisZeit(){
        return bisZeit;
    }

    public String getAlarm() { return alarm; }
}
