package com.filoware.soundcolorcode;

public class ExploreRow {

    // Store the name of the color
    private String colorName;
    // Store the hex code for the background
    private String bgColor;
    // Store the file name for the audio file
    private String audio;

    public ExploreRow(String colorName, String bgColor, String audio) {
        this.colorName = colorName;
        this.bgColor = bgColor;
        this.audio = audio;
    }

    public String getColorName() {
        return this.colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getAudio() { return this.audio; }

    public void setAudio(String audio) { this.audio = audio; }
}
