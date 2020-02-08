package com.example.remindme;

public class TerminItem {
    private int ImageResource;
    private String TextTermin;
    private String TextZeit;

    public TerminItem(int imageResource, String textTermin, String textZeit){
        ImageResource = imageResource;
        TextTermin = textTermin;
        TextZeit = textZeit;
    }

    public int getImageResource(){
        return ImageResource;
    }

    public String getTextTermin(){
        return TextTermin;
    }

    public String getTextZeit(){
        return TextZeit;
    }
}
