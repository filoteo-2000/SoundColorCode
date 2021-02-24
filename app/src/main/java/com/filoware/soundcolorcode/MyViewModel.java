package com.filoware.soundcolorcode;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {
    private String soundCode = "classic3";

    public String getSoundCode() {
        return soundCode;
    }

    public void setSoundCode(String soundCode) {
        this.soundCode = soundCode;
    }
}
