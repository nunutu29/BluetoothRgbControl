package com.example.homediy.Models;

import java.util.Locale;

public class Device {
    public int Id;
    public String Name;
    public String Details;
    public DeviceType Type;

    public Device(int id, String name, String details, DeviceType type){
        Id = id;
        Name = name;
        Details = details;
        Type = type;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%d-%s(%s)", Id, Name, Details);
    }
}
