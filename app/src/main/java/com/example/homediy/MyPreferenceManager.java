package com.example.homediy;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.homediy.Models.Device;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyPreferenceManager
{
    private static final String SET_DEVICE = "SET_DEVICE";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "HomeDIY";

    // Constructor
    public MyPreferenceManager(Context context)
    {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void storeDevice(Device device)
    {
        Set<String> currentSet = pref.getStringSet(SET_DEVICE, new HashSet<String>());
        currentSet.add(Device.toJson(device));
        editor.putStringSet(SET_DEVICE, currentSet);
        editor.commit();
    }

    public ArrayList<Device> getDevices()
    {
        Set<String> currentSet = pref.getStringSet(SET_DEVICE, new HashSet<String>());

        ArrayList<Device> devices = new ArrayList<>();

        for(String deviceString : currentSet)
        {
            devices.add(Device.fromJson(deviceString));
        }
        return devices;
    }


    public void deleteDevice(Device deviceToDelete)
    {
        Set<String> currentSet = pref.getStringSet(SET_DEVICE, new HashSet<String>());

        for(String deviceString : currentSet)
        {
            Device device = Device.fromJson(deviceString);

            if(device == null)
                continue;

            if(device.Type.equals(deviceToDelete.Type))
            {
                if(device.Key.equals(deviceToDelete.Key))
                {
                    currentSet.remove(deviceString);
                    break;
                }
            }
        }
        editor.putStringSet(SET_DEVICE, currentSet);
        editor.commit();
    }

    public void clear()
    {
        editor.clear();
        editor.commit();
    }

}
