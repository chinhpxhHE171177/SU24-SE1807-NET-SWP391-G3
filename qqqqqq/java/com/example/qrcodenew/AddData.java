package com.example.qrcodenew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddData extends AppCompatActivity {

    private EditText edtIdCode, edtCode, edtName, edtIssue1, edtIssue2, edtIssue3, edtIssue4, edtIssue5;
    private Button btnAdd;
    private FloatingActionButton btnOther;
    private ImageView imgCamera;
    private DatabaseHelper databaseHelper;
    private int issueCount = 1; // Đếm số lượng vấn đề đã nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        // Initialize UI components
        edtIdCode = findViewById(R.id.edt_IdCode_add);
        edtCode = findViewById(R.id.edt_Code_add);
        edtName = findViewById(R.id.edt_Name_add);
        edtIssue1 = findViewById(R.id.edt_Issue_1);
        edtIssue2 = findViewById(R.id.edt_Issue_2);
        edtIssue3 = findViewById(R.id.edt_Issue_3);
        edtIssue4 = findViewById(R.id.edt_Issue_4);
        edtIssue5 = findViewById(R.id.edt_Issue_5);
        btnAdd = findViewById(R.id.btn_Add);
        btnOther = findViewById(R.id.btn_Other);
        imgCamera = findViewById(R.id.img_photo_add);

        databaseHelper = new DatabaseHelper(this);

        // Set up the 'Other' button click listener
        btnOther.setOnClickListener(v -> addIssueField());

        // Set up the camera icon click listener
        imgCamera.setOnClickListener(v -> openQrCodeScanner());

        // Set up the 'Add' button click listener
        btnAdd.setOnClickListener(v -> addData());
    }

    private void addIssueField() {
        if (issueCount == 1) {
            edtIssue2.setVisibility(View.VISIBLE);
        } else if (issueCount == 2) {
            edtIssue3.setVisibility(View.VISIBLE);
        } else if (issueCount == 3) {
            edtIssue4.setVisibility(View.VISIBLE);
        } else if (issueCount == 4) {
            edtIssue5.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "You cannot add more than 5 issues.", Toast.LENGTH_SHORT).show();
            return; // Không cho thêm nữa
        }
        issueCount++;
    }

    private void openQrCodeScanner() {
        Intent intent = new Intent(AddData.this, QrCodeScanner.class);
        startActivityForResult(intent, 100); // Start QR code scanner and expect a result
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String scannedCode = data.getStringExtra("scannedCode");
            edtIdCode.setText(scannedCode);
        }
    }

    private void addData() {
        String idCode = edtIdCode.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        StringBuilder issues = new StringBuilder();

        // Lấy tất cả các vấn đề đã nhập
        issues.append(edtIssue1.getText().toString().trim());
        if (issueCount > 1) issues.append(", ").append(edtIssue2.getText().toString().trim());
        if (issueCount > 2) issues.append(", ").append(edtIssue3.getText().toString().trim());
        if (issueCount > 3) issues.append(", ").append(edtIssue4.getText().toString().trim());
        if (issueCount > 4) issues.append(", ").append(edtIssue5.getText().toString().trim());

        // Validate inputs
        if (idCode.isEmpty() || code.isEmpty() || name.isEmpty() || issues.toString().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if ID_CODE already exists
        boolean checkExist = databaseHelper.isIdExists(idCode);
        if (checkExist) {
            Toast.makeText(this, "Data addition failed. ID already exists.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert data and check the result
        boolean isInserted = databaseHelper.insertData(idCode, code, name, issues.toString());
        if (isInserted) {
            Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show();
            edtIdCode.setText("");
            edtCode.setText("");
            edtName.setText("");
            edtIssue1.setText("");
            edtIssue2.setText(""); // Reset each issue input
            edtIssue3.setText("");
            edtIssue4.setText("");
            edtIssue5.setText("");
            issueCount = 1; // Reset issue count
            edtIssue2.setVisibility(View.GONE);
            edtIssue3.setVisibility(View.GONE);
            edtIssue4.setVisibility(View.GONE);
            edtIssue5.setVisibility(View.GONE); // Hide all issue fields
        } else {
            Toast.makeText(this, "Data addition failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}




//package com.example.qrcodenew;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class AddData extends AppCompatActivity {
//
//    private EditText edtIdCode, edtCode, edtName, edtIssue;
//    private Button btnAdd;
//    private ImageView imgCamera;
//    private DatabaseHelper databaseHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_data);
//
//        // Initialize the UI components
//        edtIdCode = findViewById(R.id.edt_IdCode_add);
//        edtCode = findViewById(R.id.edt_Code_add);
//        edtName = findViewById(R.id.edt_Name_add);
//        edtIssue = findViewById(R.id.edt_Issue_add);
//        btnAdd = findViewById(R.id.btn_Add);
//        imgCamera = findViewById(R.id.img_photo_add);
//
//        databaseHelper = new DatabaseHelper(this);
//
//        // Set up the click listener for the camera icon
//        imgCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openQrCodeScanner();
//            }
//        });
//
//        // Set up the click listener for the add button
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addData();
//            }
//        });
//    }
//
//    private void openQrCodeScanner() {
//        Intent intent = new Intent(AddData.this, QrCodeScanner.class);
//        startActivityForResult(intent, 100); // Start QR code scanner and expect a result
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//            String scannedCode = data.getStringExtra("scannedCode");
//            edtIdCode.setText(scannedCode);
//        }
//    }
//
//    private void addData() {
//        String idCode = edtIdCode.getText().toString().trim();
//        String code = edtCode.getText().toString().trim();
//        String name = edtName.getText().toString().trim();
//        String issue = edtIssue.getText().toString().trim();
//
//        // Validate inputs
//        if (idCode.isEmpty() || code.isEmpty() || name.isEmpty() || issue.isEmpty()) {
//            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Check if the ID_CODE already exists
//        Boolean checkExist = databaseHelper.isIdExists(idCode);
//        if (checkExist) {
//            Toast.makeText(this, "Data addition failed. ID already exists.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Insert data and check the result
//        boolean isInserted = databaseHelper.insertData(idCode, code, name, issue);
//        if (isInserted) {
//            Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show();
//            edtIdCode.setText("");
//            edtCode.setText("");
//            edtName.setText("");
//            edtIssue.setText("");
//        } else {
//            Toast.makeText(this, "Data addition failed. Please try again.", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
