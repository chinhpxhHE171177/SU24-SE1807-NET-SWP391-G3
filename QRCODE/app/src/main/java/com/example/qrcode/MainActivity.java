package com.example.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnScan, btnStart, btnStop, btnListView, btnGenerateQRCode;

    private long startTime;
    private long endTime;
    private String scannedDeviceId;
    private String scannedDeviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnScan);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnListView = findViewById(R.id.btnListView);
        btnGenerateQRCode = findViewById(R.id.btnGenerateQRCode);

        //Ẩn các nút Bắt đầu và Dừng ban đầu
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);

        // Set click listeners for buttons
        btnScan.setOnClickListener(v -> {
            new IntentIntegrator(this).initiateScan();  // Bắt đầu quét mã QR
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                // Giả sử định dạng quét mã QR là "ID;Tên"
                String[] scannedData = result.getContents().split(";");
                scannedDeviceId = scannedData[0]; // Lấy ID
                scannedDeviceName = scannedData[1]; // Lấy tên

                Toast.makeText(this, "Đã quét: " + scannedDeviceId, Toast.LENGTH_SHORT).show();

                // Kích hoạt các nút Bắt đầu và Dừng
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            } else {
                Toast.makeText(this, "Quét không thành công", Toast.LENGTH_SHORT).show();
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

        // Tính tổng thời gian đã ghi
        long totalTime = endTime - startTime;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String startTimeFormatted = sdf.format(new Date(startTime));
        String endTimeFormatted = sdf.format(new Date(endTime));

        // Lưu thông tin vào cơ sở dữ liệu
        DatabaseHelper db = new DatabaseHelper(this);
        db.addRecord(new Record(
                0, // auto-generated ID
                scannedDeviceId, // ID thiết bị
                scannedDeviceName, // Tên thiết bị
                "Không có vấn đề", // Vấn đề
                startTimeFormatted, // Thời gian bắt đầu
                endTimeFormatted, // Thời gian kết thúc
                String.valueOf(totalTime) // Tổng thời gian
        ));

        Toast.makeText(this, "Đã lưu bản ghi.", Toast.LENGTH_SHORT).show();

        // Reset các nút
        btnStart.setEnabled(false);
        btnStop.setEnabled(false);
    }
}
