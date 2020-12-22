package com.chtd.danceapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class DanceDevice {
    static DanceDevice DEVICE;
    public static void INIT() {
        DEVICE = new DanceDevice();
        DEVICE.initBluetooth();
    }
    boolean isConnected = false;
    BluetoothDevice device;
    BluetoothSocket socket;
    InputStream inputStream;
    OutputStream outputStream;

    public boolean initBluetooth() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            if (adapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = adapter.getBondedDevices();

                for (BluetoothDevice device:
                        bondedDevices) {
                    String name = device.getName();
                    try {
                        if (name.equals("IPHONE IS DEAD")) {
                            this.device = device;
                            this.socket = device.createRfcommSocketToServiceRecord(device.getUuids()[0].getUuid());
                            this.socket.connect();
                            inputStream = socket.getInputStream();
                            outputStream = socket.getOutputStream();
                            isConnected = true;
                            return true;
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        }
        isConnected = false;
        return false;
    }

    public boolean write(String string) {
        try
        {
            byte[] stringBytes = string.getBytes(StandardCharsets.US_ASCII);
            byte[] command = new byte[stringBytes.length+1];
            for (int i = 0; i < stringBytes.length; i++) {
                command[i] = stringBytes[i];
            }
            command[stringBytes.length] = 0;
            outputStream.write(command);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
