package com.chtd.danceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chengtao.pianoview.view.PianoView;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    DanceDevice device;

    TextView textView;
    SeekBar seekBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DanceDevice.INIT();

        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String value = Integer.toString(progress);
                textView.setText(value);
                DanceDevice.DEVICE.write("P"+value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PianoActivity.class);
            MainActivity.this.startActivity(intent);
        });

        if(DanceDevice.DEVICE.isConnected) {
            textView.setText("CONNECTED");
        }
        else {
            textView.setText(":(");
        }
    }
}