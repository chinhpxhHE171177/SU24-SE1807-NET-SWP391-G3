package com.example.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecordDetailActivity extends AppCompatActivity {

    private TextView txtCode;
    private EditText edtName, edtCommonIssue;
    private Button btnUpdate, btnDelete;
    private DatabaseHelper databaseHelper;
    private int recordId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        txtCode = findViewById(R.id.txtCode);
        edtName = findViewById(R.id.edtName);
        edtCommonIssue = findViewById(R.id.edtCommonIssue);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        databaseHelper = new DatabaseHelper(this);

        // Get record ID from Intent
        recordId = getIntent().getIntExtra("recordId", -1);
        loadRecordDetails();

        btnUpdate.setOnClickListener(v -> updateRecord());
        btnDelete.setOnClickListener(v -> deleteRecord());
    }

//    private void loadRecordDetails() {
//        // Load record and display info
//        Record record = databaseHelper.getRecord(recordId);
//        if (record != null) {
//            txtCode.setText(record.getDeviceId());
//            edtName.setText(record.getDeviceName());
//            edtCommonIssue.setText(record.getCommonIssues());
//        }
//    }

    private void loadRecordDetails() {
        // Load record and display info
        Record record = databaseHelper.getRecord(recordId);
        if (record != null) {
            txtCode.setText(record.getDeviceId());
            edtName.setText(record.getDeviceName());
            edtCommonIssue.setText(record.getCommonIssues());
            // Giả sử thêm TextViews để hiển thị thời gian
            TextView txtStartTime = findViewById(R.id.txtStartTime);
            TextView txtEndTime = findViewById(R.id.txtEndTime);
            TextView txtTotalTime = findViewById(R.id.txtTotalTime);

            txtStartTime.setText(record.getStartTime());
            txtEndTime.setText(record.getEndTime());
            txtTotalTime.setText(record.getTotalTime());
        }
    }

    private void updateRecord() {
        String name = edtName.getText().toString();
        String commonIssue = edtCommonIssue.getText().toString();

        // Update record with correct parameters
        databaseHelper.updateRecord(new Record(recordId, txtCode.getText().toString(), name, commonIssue, "", "", ""));
        Toast.makeText(this, "Bản ghi đã được cập nhật", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteRecord() {
        databaseHelper.deleteRecord(recordId);
        Toast.makeText(this, "Bản ghi đã được xóa", Toast.LENGTH_SHORT).show();
        finish();
    }
}
