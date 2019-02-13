package com.example.homediy.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class DeviceContent {

    public static final ArrayList<Device> DEVICES = new ArrayList<>();
    public static final HashMap<Integer, Device> DEVICE_MAP = new HashMap<>();

    static {
        // Add 2 items
        for (int i = 1; i <= 4; i++) {
            addDevice(createDevice(i));
        }
    }

    private static void addDevice(Device device){
        DEVICES.add(device);
        DEVICE_MAP.put(device.Id, device);
    }

    private static Device createDevice(int id){
        return new Device(id, "Name " + id, "Details " + id, DeviceType.Bluetooth);
    }

}
