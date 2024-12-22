//package com.example.checkcodechange;
//
//import android.os.Bundle;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.journeyapps.barcodescanner.ScanContract;
//import com.journeyapps.barcodescanner.ScanOptions;
//
//public class MainActivity extends AppCompatActivity {
//
//    private EditText etQRCode; // Ô input TextInputEditText
//    private FloatingActionButton btnScan;
//
//    // ZXing ScanContract Launcher
//    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(
//            new ScanContract(),
//            result -> {
//                if (result.getContents() != null) {
//                    etQRCode.setText(result.getContents());
//                    Toast.makeText(this, "Kết quả: " + result.getContents(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
//                }
//            }
//    );
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // Ánh xạ View
//        etQRCode = findViewById(R.id.etQRCode);
//        btnScan = findViewById(R.id.btnScan);
//
//        // Nút bấm để khởi chạy trình quét mã QR
//        btnScan.setOnClickListener(v -> startQRCodeScanner());
//    }
//
//    // Phương thức khởi chạy QR Scanner
//    private void startQRCodeScanner() {
//        ScanOptions options = new ScanOptions();
//        options.setPrompt("Quét mã QR Code");
//        options.setBeepEnabled(true);
//        options.setOrientationLocked(true); // Khóa hướng màn hình
//        barcodeLauncher.launch(options);  // Khởi chạy quét QR
//    }
//}


package com.example.checkcodechange;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class MainActivity extends AppCompatActivity {

    private EditText etQRCode;
    private FloatingActionButton btnScan;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() != null) {
                    String scannedCode = result.getContents();
                    etQRCode.setText(scannedCode);
                    // Gửi mã lên server
                    uploadCode(scannedCode);
                } else {
                    Toast.makeText(this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etQRCode = findViewById(R.id.etQRCode);
        btnScan = findViewById(R.id.btnScan);

        btnScan.setOnClickListener(v -> startQRCodeScanner());
    }

    private void startQRCodeScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Quét mã QR Code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        barcodeLauncher.launch(options);
    }

    private void uploadCode(String code) {
        new com.example.checkcodechange.UploadDataTask(this).execute(code);
    }
}



