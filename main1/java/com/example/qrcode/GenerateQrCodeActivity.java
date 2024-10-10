package com.example.qrcode;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code39Writer;

public class GenerateQrCodeActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);
        imageView = findViewById(R.id.imageView);

        String deviceInfo = "Thiết bị 123"; // Thay đổi theo nhu cầu
        generateQRCode(deviceInfo);
    }

    private void generateQRCode(String deviceId) {
        Code39Writer writer = new Code39Writer();
        try {
            BitMatrix bitMatrix = writer.encode(deviceId, BarcodeFormat.CODE_39, 200, 100);
            Bitmap bitmap = Bitmap.createBitmap(200, 100, Bitmap.Config.RGB_565);
            for (int x = 0; x < 200; x++) {
                for (int y = 0; y < 100; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
