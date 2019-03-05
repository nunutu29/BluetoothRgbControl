package com.example.homediy.Models;

import java.io.Serializable;

public class Device implements Serializable
{
    public String Key;
    public String Name;
    public String Details;
    public DeviceType Type;

    public Device(String key, String name, String details, DeviceType type)
    {
        Key = key;
        Name = name;
        Details = details;
        Type = type;
    }
}
