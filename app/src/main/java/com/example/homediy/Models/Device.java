package com.example.homediy.Models;

import org.json.JSONObject;

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

    public static String toJson(Device device)
    {
        try
        {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("Key", device.Key);
            jsonObject.put("Name", device.Name);
            jsonObject.put("Details", device.Details);
            jsonObject.put("Type", device.Type);

            return jsonObject.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return "";
        }
    }

    public static Device fromJson(String deviceString)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(deviceString);

            return new Device(
                    jsonObject.getString("Key"),
                    jsonObject.getString("Name"),
                    jsonObject.getString("Details"),
                    DeviceType.valueOf(jsonObject.getString("Type"))
            );
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}
