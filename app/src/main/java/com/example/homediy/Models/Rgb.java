package com.example.homediy.Models;

import android.graphics.Color;
import android.support.annotation.ColorInt;


import java.util.Locale;

public class Rgb
{
    public int Red;
    public int Green;
    public int Blue;

    public Rgb(@ColorInt int intColor)
    {
        Red = Color.red(intColor);
        Green = Color.green(intColor);
        Blue = Color.blue(intColor);
    }

    /**
     * Replaces {R} with Red value, {G} with Green value, {B} with Blue value
     * @param format
     * @return
     */
    public String Format(String format)
    {
        return format
            .replace("{R}", String.valueOf(Red))
            .replace("{G}", String.valueOf(Green))
            .replace("{B}", String.valueOf(Blue));
    }
}
