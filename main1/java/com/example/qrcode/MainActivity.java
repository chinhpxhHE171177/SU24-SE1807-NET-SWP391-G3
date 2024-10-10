package com.example.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnScan, btnStart, btnStop, btnListView, btnGenerateQRCode;

    private long startTime;
    private long endTime;
    private String scannedDeviceId;
    private String scannedDeviceName;

    // Define the ActivityResultLauncher
    private ActivityResultLauncher<Intent> qrCodeLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnScan);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnListView = findViewById(R.id.btnListView);
        btnGenerateQRCode = findViewById(R.id.btnGenerateQRCode);

        btnStart.setEnabled(false);
        btnStop.setEnabled(false);

        // Initialize ActivityResultLauncher for the custom scanner
        qrCodeLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String qrCodeResult = result.getData().getStringExtra("SCAN_RESULT");
                        if (qrCodeResult != null) {
                            String[] scannedData = qrCodeResult.split(";");
                            scannedDeviceId = scannedData[0]; // Device ID
                            scannedDeviceName = scannedData[1]; // Device Name

                            Toast.makeText(MainActivity.this, "Scanned: " + scannedDeviceId, Toast.LENGTH_SHORT).show();
                            btnStart.setEnabled(true);
                            btnStop.setEnabled(false);
                        } else {
                            Toast.makeText(MainActivity.this, "Scan failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        // Set button click listeners
        btnScan.setOnClickListener(v -> scanQRCode());
        btnStart.setOnClickListener(v -> startRecording());
        btnStop.setOnClickListener(v -> stopRecording());

        btnListView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RecordListActivity.class);
            startActivity(intent);
        });

        btnGenerateQRCode.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GenerateQrCodeActivity.class);
            startActivity(intent);
        });
    }

    // Function to initiate QR code scanning
    private void scanQRCode() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        intent.setAction("com.google.zxing.client.android.SCAN");
        qrCodeLauncher.launch(intent);
    }

    public void startRecording() {
        startTime = System.currentTimeMillis();
        Toast.makeText(this, "Recording started...", Toast.LENGTH_SHORT).show();
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }

    public void stopRecording() {
        endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String startTimeFormatted = sdf.format(new Date(startTime));
        String endTimeFormatted = sdf.format(new Date(endTime));

        DatabaseHelper db = new DatabaseHelper(this);
        db.addRecord(new Record(
                0,
                scannedDeviceId,
                scannedDeviceName,
                "No issues",
                startTimeFormatted,
                endTimeFormatted,
                String.valueOf(totalTime)
        ));

        Toast.makeText(this, "Record saved.", Toast.LENGTH_SHORT).show();
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);
    }
}
