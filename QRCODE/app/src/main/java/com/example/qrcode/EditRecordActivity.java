package com.example.qrcode;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditRecordActivity extends AppCompatActivity {

    private EditText etDeviceName, etCommonIssues;
    private Button btnSave;
    private int recordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);

        etDeviceName = findViewById(R.id.etDeviceName);
        etCommonIssues = findViewById(R.id.etCommonIssues);
        btnSave = findViewById(R.id.btnSave);

        recordId = getIntent().getIntExtra("recordId", -1);
        DatabaseHelper db = new DatabaseHelper(this);
        Record record = db.getRecord(recordId);
        if (record != null) {
            etDeviceName.setText(record.getDeviceName());
            etCommonIssues.setText(record.getCommonIssues());
        }

        btnSave.setOnClickListener(v -> {
            String newDeviceName = etDeviceName.getText().toString();
            String newCommonIssues = etCommonIssues.getText().toString();
            db.updateRecord(new Record(recordId, record.getDeviceId(), newDeviceName, newCommonIssues, record.getStartTime(), record.getEndTime(), record.getTotalTime()));
            finish();
        });
    }
}
