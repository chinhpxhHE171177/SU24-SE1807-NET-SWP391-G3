package com.example.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QrScanActivity extends AppCompatActivity {
    private long startTime;
    private long endTime;
    private String scannedDeviceId;
    private String scannedDeviceName;

    private Button btnStart;
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan); // Giao diện quét mã QR

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        // Khởi động quét mã QR
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                String[] scannedData = result.getContents().split(";");
                scannedDeviceId = scannedData[0]; // Lấy ID
                scannedDeviceName = scannedData[1]; // Lấy tên

                Toast.makeText(this, "Đã quét: " + scannedDeviceId, Toast.LENGTH_SHORT).show();

                // Kích hoạt các nút bắt đầu và dừng
                btnStop.setEnabled(false);
                btnStart.setEnabled(true);

                btnStart.setOnClickListener(v -> startRecording());
                btnStop.setOnClickListener(v -> stopRecording());
            } else {
                Toast.makeText(this, "Quét không thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startRecording() {
        startTime = System.currentTimeMillis();
        Toast.makeText(this, "Bắt đầu ghi...", Toast.LENGTH_SHORT).show();
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
    }

    public void stopRecording() {
        endTime = System.currentTimeMillis();

        // Lưu các thông tin vào cơ sở dữ liệu
        long totalTime = endTime - startTime;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String startTimeFormatted = sdf.format(new Date(startTime));
        String endTimeFormatted = sdf.format(new Date(endTime));

        DatabaseHelper db = new DatabaseHelper(this);
        db.addRecord(new Record(
                0, // auto-generated ID
                scannedDeviceId,
                scannedDeviceName,
                "Không có vấn đề",
                startTimeFormatted,
                endTimeFormatted,
                String.valueOf(totalTime)
        ));

        Toast.makeText(this, "Đã lưu bản ghi.", Toast.LENGTH_SHORT).show();
        finish();
    }
}
