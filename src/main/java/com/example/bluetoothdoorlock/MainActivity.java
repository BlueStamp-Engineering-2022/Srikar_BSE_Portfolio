package com.example.bluetoothdoorlock;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.UUID;

import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    private static final boolean TODO = false;
    private final String DEVICE_ADDRESS = "98:D3:31:90:82:9A"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    boolean stopThread;
    boolean connected = false;
    String command;

    Button openDoorButton, closeDoorButton, bluetoothConnectButton;

    TextView enterPasswordTextView;

    BluetoothConnectionService bcs;

    BluetoothManager bluetoothManager;

    BluetoothAdapter bluetoothAdapter;

    EditText passwordBoxEditText;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bcs = new BluetoothConnectionService(this.getApplicationContext());
        passwordBoxEditText = findViewById(R.id.passwordBoxEditText);

        bluetoothManager = getSystemService(BluetoothManager.class);
        bluetoothAdapter = bluetoothManager.getAdapter();

    }

    public void sendOpenMessage() {
        String passwordString = passwordBoxEditText.getText().toString();
        String send = "OPEN=" + passwordString + "\n";

        byte[] byteArraySend = send.getBytes();
        bcs.write(byteArraySend);
    }

    public void sendClosedMessage() {
        String passwordString = passwordBoxEditText.getText().toString();
        String send = "CLOSE=" + passwordString + "\n";

        byte[] byteArraySend = send.getBytes();
        bcs.write(byteArraySend);

    }

    public void connectBluetooth() {

        ActivityResultLauncher<String> requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                    }
                    else {
                        Toast.makeText(this, "Bluetooth not allowed", Toast.LENGTH_SHORT).show();
                    }
                });


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT);
                    }

                    Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            String deviceHardwareAddress = device.getAddress();
                            if (deviceHardwareAddress.equals("00:21:08:35:0E:2A")) {
                                BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice("00:21:08:35:0E:2A");

                                BluetoothConnectionService.
                            }
                        }
                    }
                }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
