package com.emc.verticalweekcalendar.interfaces;

import android.graphics.Typeface;

public interface ResProvider {

    int getSelectedDayBackground();

    int getSelectedDayTextColor();

    int getDayTextColor();

    int getWeekDayTextColor();

    int getDayBackground();

    Typeface getCustomFont();
}
