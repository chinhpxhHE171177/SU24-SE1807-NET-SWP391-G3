package com.example.qrcodenew;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ImageView btnScan;
    private FloatingActionButton btnAdd, btnListView, btnShowHistory;
    private DatabaseHelper databaseHelper;
    private long startTime, endTime;
    private String scannedId;

    // Sử dụng danh sách để lưu IDCode đã quét
    private ArrayList<String> scannedIdCodes = new ArrayList<>();
    private boolean isRecording = false;

    // Biến để lưu danh sách vấn đề đã chọn
    private ArrayList<String> tempSelectedIssues = new ArrayList<>();

    // Sử dụng HashMap để lưu trạng thái quét cho từng IDCode
    private HashMap<String, Boolean> scannedIdRecordingStatus = new HashMap<>();

    // Các TextViews
    private TextView txt_code;
    private TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        btnScan = findViewById(R.id.btnScan);
        btnAdd = findViewById(R.id.btnAdd);
        btnListView = findViewById(R.id.btnViewList);
        btnShowHistory = findViewById(R.id.btnHistory);

        // Yêu cầu quyền truy cập camera
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // Quyền đã được cấp
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(MainActivity.this, "You must enable this permission", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

        btnScan.setOnClickListener(v -> startQrCodeScanner());
        btnAdd.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddData.class)));
        btnListView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListData.class)));
        btnShowHistory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListHistoryActivity.class)));
    }

    private void startQrCodeScanner() {
        Intent intent = new Intent(MainActivity.this, QrCodeScanner.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String scannedCode = data.getStringExtra("scannedCode");
            handleScannedResult(scannedCode);
        }
    }

    private void handleScannedResult(String scanResult) {
        Cursor deviceData = databaseHelper.selectData(scanResult);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_information, null);

        TextView txt_id = dialogView.findViewById(R.id.txt_id);
        TextView txt_idCode = dialogView.findViewById(R.id.txt_idCode);
        txt_code = dialogView.findViewById(R.id.txt_code);
        txt_name = dialogView.findViewById(R.id.txt_name);
        ListView listView = dialogView.findViewById(R.id.list_view_issues);

        ArrayList<String> issueList = new ArrayList<>();

        if (deviceData.moveToFirst()) {
            int idIndex = deviceData.getColumnIndex(Database.DEVICE_ID);
            int idCodeIndex = deviceData.getColumnIndex(Database.DEVICE_ID_CODE);
            int codeIndex = deviceData.getColumnIndex(Database.DEVICE_CODE);
            int nameIndex = deviceData.getColumnIndex(Database.DEVICE_NAME);
            int issueIndex = deviceData.getColumnIndex(Database.DEVICE_ISSUE);

            scannedId = deviceData.getString(idCodeIndex); // Lưu ID quét được
            txt_id.setText("ID: " + deviceData.getString(idIndex));
            txt_idCode.setText("IdCode: " + scannedId);
            txt_code.setText("Code: " + deviceData.getString(codeIndex));
            txt_name.setText("Name: " + deviceData.getString(nameIndex));

            // Lấy danh sách vấn đề từ cơ sở dữ liệu
            String issues = deviceData.getString(issueIndex);
            if (issues != null) {
                String[] issuesArray = issues.split(",");
                for (String issue : issuesArray) {
                    issueList.add(issue.trim());
                }
            }

            // Nếu quét lần đầu
            if (!scannedIdRecordingStatus.getOrDefault(scannedId, false)) {
                startTime = System.currentTimeMillis();
                scannedIdRecordingStatus.put(scannedId, true); // Đánh dấu IDCode là đã quét

                // Hiện dialog để chọn vấn đề
                showIssueDialog(dialogView, issueList, true);
            } else {
                // Kiểm tra IDCode quét lần này
                if (scannedIdRecordingStatus.containsKey(scannedId)) {
                    // Lưu dữ liệu vào cơ sở dữ liệu
                    endTime = System.currentTimeMillis();
                    saveDataOnSecondScan(); // Gọi phương thức lưu dữ liệu
                    resetScannedIdState(scannedId); // Reset trạng thái cho IDCode đã insert
                } else {
                    // Nếu IDCode không trùng, coi như là lần quét thứ nhất cho thiết bị này
                    startTime = System.currentTimeMillis(); // Đặt lại thời gian bắt đầu
                    showIssueDialog(dialogView, issueList, false); // Hiển thị lại dialog để chọn vấn đề cho lần quét thứ hai
                }
            }
        } else {
            // Nếu không tìm thấy dữ liệu
            Toast.makeText(this, "No data found for this device!", Toast.LENGTH_SHORT).show();
            txt_id.setText("No id found!!!");
            txt_code.setText("No code found!!!");
            txt_name.setText("No name found!!!");
        }
        deviceData.close();
    }


    private void showIssueDialog(View dialogView, ArrayList<String> issueList, boolean isFirstScan) {
        ListView listView = dialogView.findViewById(R.id.list_view_issues);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, issueList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> {
                    SparseBooleanArray checked = listView.getCheckedItemPositions();
                    ArrayList<String> selectedIssues = new ArrayList<>();
                    for (int i = 0; i < issueList.size(); i++) {
                        if (checked.get(i)) {
                            selectedIssues.add(issueList.get(i));
                        }
                    }
                    if (selectedIssues.size() > 5) {
                        Toast.makeText(MainActivity.this, "You cannot select more than 5 issues.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Lưu các vấn đề đã chọn vào biến toàn cục
                    tempSelectedIssues.clear();
                    tempSelectedIssues.addAll(selectedIssues);

                    // Chỉ thêm IDCode vào danh sách nếu là lần quét đầu tiên
                    if (isFirstScan) {
                        scannedIdCodes.add(scannedId);
                    }
                })
                .setNegativeButton("Close", (dialog, which) -> {
                    dialog.dismiss();
                    // Chỉ reset trạng thái nếu là lần quét đầu tiên
                    if (isFirstScan) {
                        isRecording = false; // Reset trạng thái quét
                        scannedIdCodes.clear(); // Xóa danh sách IDCode
                        tempSelectedIssues.clear(); // Xóa danh sách vấn đề đã chọn
                    }
                })
                .create()
                .show();
    }

    private void saveDataOnSecondScan() {
        long totalTime = endTime - startTime;
        String formattedStartTime = formatTime(startTime);
        String formattedEndTime = formatTime(endTime);
        String formattedTotalTime = formatTotalTime(totalTime);

        if (scannedIdRecordingStatus.containsKey(scannedId) && scannedIdRecordingStatus.get(scannedId)) {
            databaseHelper.insertListData(scannedId, txt_code.getText().toString(), txt_name.getText().toString(),
                    String.join(", ", tempSelectedIssues), formattedStartTime, formattedEndTime, formattedTotalTime);
            Toast.makeText(this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetScannedIdState(String scannedId) {
        // Chỉ reset trạng thái quét cho IDCode đã insert vào database
        if (scannedIdRecordingStatus.containsKey(scannedId)) {
            scannedIdRecordingStatus.put(scannedId, false); // Reset trạng thái cho IDCode đã quét
        }
    }

    private String formatTime(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date resultDate = new Date(timeInMillis);
        return sdf.format(resultDate);
    }

    private String formatTotalTime(long totalTimeMillis) {
        double totalTimeMinutes = totalTimeMillis / 60000.0;
        return String.format(Locale.getDefault(), "%.2f m", totalTimeMinutes);
    }
}





//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.SparseBooleanArray;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Locale;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionDeniedResponse;
//import com.karumi.dexter.listener.PermissionGrantedResponse;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.single.PermissionListener;
//
//public class MainActivity extends AppCompatActivity {
//
//    private Button btnStart, btnEnd;
//    private ImageView btnScan;
//    private FloatingActionButton btnAdd, btnListView, btnShowHistory;
//    private DatabaseHelper databaseHelper;
//    private long startTime, endTime;
//    private String scannedId; // Store the scanned ID
//    private boolean isRecording = false; // Track recording status
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        databaseHelper = new DatabaseHelper(this);
//        btnScan = findViewById(R.id.btnScan);
//        btnAdd = findViewById(R.id.btnAdd);
//        btnListView = findViewById(R.id.btnViewList);
////        btnStart = findViewById(R.id.btnStart);
//        btnEnd = findViewById(R.id.btnEnd);
//        btnShowHistory = findViewById(R.id.btnHistory);
//
//        // Request camera permission
//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission.CAMERA)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        // Permission granted
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        Toast.makeText(MainActivity.this, "You must enable this permission", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest(); // Continue with permission request
//                    }
//                }).check();
//
//        // Button onClick listeners
//        btnAdd.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddData.class)));
//
//        btnListView.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListData.class)));
//
//        btnShowHistory.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ListHistoryActivity.class)));
//
//        btnScan.setOnClickListener(v -> startQrCodeScanner());
//
////        btnStart.setOnClickListener(v -> startRecording());
//
//        btnEnd.setOnClickListener(v -> stopRecording());
//    }
//
//    private void startQrCodeScanner() {
//        Intent intent = new Intent(MainActivity.this, QrCodeScanner.class);
//        startActivityForResult(intent, 100); // Request code for scanning
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//            String scannedCode = data.getStringExtra("scannedCode");
//            handleScannedResult(scannedCode);
//        }
//    }
//
////    private void handleScannedResult(String scanResult) {
////        Cursor data = databaseHelper.selectData(scanResult);
//////        final TextView txt_id = new TextView(this);
//////        final TextView txt_idCode = new TextView(this);
//////        final TextView txt_code = new TextView(this);
//////        final TextView txt_name = new TextView(this);
//////        final TextView txt_issue = new TextView(this);
//////        LinearLayout layout = new LinearLayout(this);
//////        layout.setOrientation(LinearLayout.VERTICAL);
//////        layout.addView(txt_id);
//////        layout.addView(txt_idCode);
//////        layout.addView(txt_code);
//////        layout.addView(txt_name);
//////        layout.addView(txt_issue);
////
////        // Inflate custom layout
////        LayoutInflater inflater = this.getLayoutInflater();
////        View dialogView = inflater.inflate(R.layout.dialog_information, null);
////
////        TextView txt_id = dialogView.findViewById(R.id.txt_id);
////        TextView txt_idCode = dialogView.findViewById(R.id.txt_idCode);
////        TextView txt_code = dialogView.findViewById(R.id.txt_code);
////        TextView txt_name = dialogView.findViewById(R.id.txt_name);
////        TextView txt_issue = dialogView.findViewById(R.id.txt_issue);
////
////        if (data.moveToFirst()) {
////            int idIndex = data.getColumnIndex(Database.DEVICE_ID);
////            int idCodeIndex = data.getColumnIndex(Database.DEVICE_ID_CODE);
////            int codeIndex = data.getColumnIndex(Database.DEVICE_CODE);
////            int nameIndex = data.getColumnIndex(Database.DEVICE_NAME);
////            int issueIndex = data.getColumnIndex(Database.DEVICE_ISSUE);
////            scannedId = data.getString(idCodeIndex); // Store the scanned ID
////            Log.d("DatabaseHelper", "Scanning for ID: " + scannedId);
////
////            txt_id.setText("Id: " + data.getString(idIndex));
////            txt_id.setText("IdCode: " + data.getString(idCodeIndex));
////            txt_code.setText("Code: " + data.getString(codeIndex));
////            txt_name.setText("Name: " + data.getString(nameIndex));
////            txt_issue.setText("Issue: " + data.getString(issueIndex));
////
////            // Lưu thời gian bắt đầu ngay sau khi quét
////            startTime = System.currentTimeMillis(); // Lưu thời gian bắt đầu
////            isRecording = true; // Đặt trạng thái ghi chú
////            btnEnd.setEnabled(true); // Kích hoạt nút End
////
////            btnEnd.setVisibility(View.VISIBLE); // hien thi lai buton end
////        } else {
////            txt_id.setText("No id found!!!");
////            txt_code.setText("No code found!!!");
////            txt_name.setText("No name found!!!");
////            txt_issue.setText("");
////        }
////        data.close();
////
////        // Create and show the dialog
////        AlertDialog.Builder builder = new AlertDialog.Builder(this);
////        builder.setTitle("Information")
////                .setView(dialogView) // Set the custom layout
////                .setPositiveButton("OK", (dialog, which) -> {
////                    // Action when "OK" is pressed
////                })
////                .setNegativeButton("Close", (dialog, which) -> {
////                    btnEnd.setVisibility(View.INVISIBLE); // btn end gone
////                    dialog.dismiss();
////                })
////                .create().show();
////
////
//////        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//////        builder.setTitle("Information").setView(layout)
//////                .setPositiveButton("OK", (dialog, which) -> { })
//////                .create().show();
////    }
//
//    private void handleScannedResult(String scanResult) {
//        Cursor data = databaseHelper.selectData(scanResult);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_information, null);
//
//        TextView txt_id = dialogView.findViewById(R.id.txt_id);
//        TextView txt_idCode = dialogView.findViewById(R.id.txt_idCode);
//        TextView txt_code = dialogView.findViewById(R.id.txt_code);
//        TextView txt_name = dialogView.findViewById(R.id.txt_name);
//        ListView listView = dialogView.findViewById(R.id.list_view_issues); // Thêm ListView
//
//        ArrayList<String> issueList = new ArrayList<>();
//
//        if (data.moveToFirst()) {
//            int idIndex = data.getColumnIndex(Database.DEVICE_ID);
//            int idCodeIndex = data.getColumnIndex(Database.DEVICE_ID_CODE);
//            int codeIndex = data.getColumnIndex(Database.DEVICE_CODE);
//            int nameIndex = data.getColumnIndex(Database.DEVICE_NAME);
//            int issueIndex = data.getColumnIndex(Database.DEVICE_ISSUE);
//
//            scannedId = data.getString(idCodeIndex); // Store the scanned ID
//            Log.d("DatabaseHelper", "Scanning for ID: " + scannedId);
//
//            txt_id.setText("Id: " + data.getString(idIndex));
//            txt_idCode.setText("IdCode: " + data.getString(idCodeIndex));
//            txt_code.setText("Code: " + data.getString(codeIndex));
//            txt_name.setText("Name: " + data.getString(nameIndex));
//
//            String issues = data.getString(issueIndex);
//            if (issues != null) {
//                String[] issuesArray = issues.split(",");
//                for (String issue : issuesArray) {
//                    issueList.add(issue.trim());
//                }
//            }
//
//            // Lưu thời gian bắt đầu ngay sau khi quét
//            startTime = System.currentTimeMillis(); // Lưu thời gian bắt đầu
//            isRecording = true; // Đặt trạng thái ghi chú
//            btnEnd.setEnabled(true); // Kích hoạt nút End
//
//        } else {
//            txt_id.setText("No id found!!!");
//            txt_idCode.setText("No idCode found!!!");
//            txt_code.setText("No code found!!!");
//            txt_name.setText("No name found!!!");
//        }
//        data.close();
//
//        // Tạo danh sách chọn nhiều vấn đề
////        final boolean[] checkedIssues = new boolean[issueList.size()];
////        final ArrayList<String> selectedIssues = new ArrayList<>();
//
//        // Tạo Adapter cho ListView
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, issueList);
//        listView.setAdapter(adapter);
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // Cho phép chọn nhiều
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(dialogView)
//                .setPositiveButton("OK", (dialog, which) -> {
//                    // Lấy các vấn đề đã chọn
//                    SparseBooleanArray checked = listView.getCheckedItemPositions();
//                    ArrayList<String> selectedIssues = new ArrayList<>();
//                    for (int i = 0; i < issueList.size(); i++) {
//                        if (checked.get(i)) {
//                            selectedIssues.add(issueList.get(i));
//                        }
//                    }
//
//                    if (selectedIssues.size() > 5) {
//                        Toast.makeText(MainActivity.this, "You cannot select more than 5 issues.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    // Lưu vấn đề để sau này sử dụng khi nhấn nút End
//                    saveIssuesForEnd(selectedIssues, txt_code.getText().toString(), txt_name.getText().toString());
//
//                    btnEnd.setVisibility(View.VISIBLE); // hiển thị lại nút End
//                })
//                .setNegativeButton("Close", (dialog, which) -> dialog.dismiss())
//                .create()
//                .show();
//    }
//
//    // Biến toàn cục để lưu vấn đề tạm thời
//    private ArrayList<String> tempSelectedIssues = new ArrayList<>();
//    private String tempCode;
//    private String tempName;
//
//    // Hàm lưu thông tin vấn đề đã chọn
//    private void saveIssuesForEnd(ArrayList<String> selectedIssues, String code, String name) {
//        tempSelectedIssues.clear();
//        tempSelectedIssues.addAll(selectedIssues);
//        tempCode = code;
//        tempName = name;
//    }
//
//
//    private String formatTime(long timeInMillis) {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
//        Date resultDate = new Date(timeInMillis);
//        return sdf.format(resultDate);
//    }
//
//    String formattedStartTime = formatTime(startTime);
//    String formattedEndTime = formatTime(endTime);
//
//    private String formatTotalTime(long totalTimeSeconds) {
//        double totalTimeMinutes = totalTimeSeconds / 60000.0; // Chuyển từ giây sang phút
//        return String.format(Locale.getDefault(), "%.2f m", totalTimeMinutes);
//    }
//
//    private void startRecording() {
//        if (!isRecording) {
//            startTime = System.currentTimeMillis(); // Start timing
//            isRecording = true; // Set recording state
//            btnStart.setEnabled(false); // Disable start button
//            btnEnd.setEnabled(true); // Enable end button
//            Toast.makeText(this, "Recording started...", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Already recording!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void stopRecording() {
//        if (isRecording) {
//            endTime = System.currentTimeMillis();
//            long totalTime = endTime - startTime;
//
//            String formattedStartTime = formatTime(startTime);
//            String formattedEndTime = formatTime(endTime);
//            String formattedTotalTime = formatTotalTime(totalTime);
//
//            // Kiểm tra số lượng vấn đề và chỉ thực hiện insert nếu đã chọn vấn đề
//            if (!tempSelectedIssues.isEmpty()) {
//                // Gọi phương thức để lưu dữ liệu
//                databaseHelper.insertListData(scannedId, tempCode, tempName,
//                        String.join(", ", tempSelectedIssues), formattedStartTime,
//                        formattedEndTime, formattedTotalTime);
//
//                Toast.makeText(this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
//
//                // Reset tạm thời
//                tempSelectedIssues.clear();
//                tempCode = null;
//                tempName = null;
//            } else {
//                Toast.makeText(this, "No issues selected to save.", Toast.LENGTH_SHORT).show();
//            }
//
//            btnEnd.setEnabled(false);
//            isRecording = false;
//        } else {
//            Toast.makeText(this, "No recording in progress!", Toast.LENGTH_SHORT).show();
//        }
//    }
//}


//private void stopRecording() {
//    if (isRecording) {
//        endTime = System.currentTimeMillis(); // End timing
//        long totalTime = endTime - startTime;
//
//        String formattedStartTime = formatTime(startTime);
//        String formattedEndTime = formatTime(endTime);
//        String formattedTotalTime = formatTotalTime(totalTime);
//
//        // Insert the data into the database using the scannedId
//        Cursor data = databaseHelper.selectData(scannedId);
//        if (data.moveToFirst()) {
//            // Get column indexes
//            int codeIndex = data.getColumnIndex(Database.ID_CODE);
//            int nameIndex = data.getColumnIndex(Database.NAME);
//            int issueIndex = data.getColumnIndex(Database.ISSUE);
//
//            // Validate column indexes
//            if (codeIndex != -1 && nameIndex != -1 && issueIndex != -1) {
//                String code = data.getString(codeIndex);
//                String deviceName = data.getString(nameIndex);
//                String issue = data.getString(issueIndex);
//                databaseHelper.insertListData(scannedId, deviceName, issue, formattedStartTime, formattedEndTime, formattedTotalTime);
//            } else {
//                Toast.makeText(this, "Error: Required columns not found in the database.", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(this, "No data found for the scanned ID.", Toast.LENGTH_SHORT).show();
//        }
//        data.close();
//
//        Toast.makeText(this, "Recording saved.", Toast.LENGTH_SHORT).show();
//        btnStart.setEnabled(true); // Enable start button
//        btnEnd.setEnabled(false); // Disable end button
//        isRecording = false; // Reset recording state
//    } else {
//        Toast.makeText(this, "No recording in progress!", Toast.LENGTH_SHORT).show();
//    }
//}