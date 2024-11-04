package com.example.qrcode;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ImageView btnScan;
    private FloatingActionButton btnAdd, btnListView, btnShowHistory, btnProcess;
    private DatabaseHelper databaseHelper;
    private TextView txtProcessingStatus;
    private long startTime, endTime;
    private String scannedId;
    private ListView listview;
    private ArrayList<String> scannedIdCodes = new ArrayList<>();
    private boolean isRecording = false;
    private ArrayList<String> tempSelectedIssues = new ArrayList<>();
    // HashMap để lưu trạng thái quét từng IDCode
    private HashMap<String, Boolean> scannedIdRecordingStatus = new HashMap<>();
    private HashMap<String, Long> scannedIdStartTime = new HashMap<>();
    private HashMap<String, ArrayList<String>> scannedIssuesMap = new HashMap<>();
    public static HashMap<String, DeviceProcess> processingDevices = new HashMap<>();
    private TextView txt_code;
    private TextView txt_name;
    private TextView txt_stage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        btnScan = findViewById(R.id.btnScan);
        btnAdd = findViewById(R.id.btnAdd);
        btnListView = findViewById(R.id.btnViewList);
        btnShowHistory = findViewById(R.id.btnHistory);
        btnProcess = findViewById(R.id.btnViewProcessing);
        listview = findViewById(R.id.list_view_issues);
        txtProcessingStatus = findViewById(R.id.txtProcessingStatus);

        // yc camera
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
        btnProcess.setOnClickListener(v -> {
            if (!processingDevices.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, DeviceProcessingActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No devices to process.", Toast.LENGTH_SHORT).show();
            }
        });
        // Check the processing status initially
        updateProcessingStatus(); // hide textView
    }

    // Method to update the visibility of the processing TextView
    private void updateProcessingStatus() {
        if (!processingDevices.isEmpty()) {
            txtProcessingStatus.setVisibility(View.VISIBLE);
        } else {
            txtProcessingStatus.setVisibility(View.GONE);
        }
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
        txt_stage = dialogView.findViewById(R.id.txt_stage);
        ListView listView = dialogView.findViewById(R.id.list_view_issues);
        ArrayList<String> issueList = new ArrayList<>();

        if (deviceData.moveToFirst()) {
            int idIndex = deviceData.getColumnIndex(Database.DEVICE_ID);
            int idCodeIndex = deviceData.getColumnIndex(Database.DEVICE_ID_CODE);
            int codeIndex = deviceData.getColumnIndex(Database.DEVICE_CODE);
            int nameIndex = deviceData.getColumnIndex(Database.DEVICE_NAME);
            int stageIndex = deviceData.getColumnIndex(Database.DEVICE_STAGE);
            int issueIndex = deviceData.getColumnIndex(Database.DEVICE_ISSUE);

            scannedId = deviceData.getString(idCodeIndex); // Lưu ID quét được

            txt_id.setText("ID: " + deviceData.getString(idIndex));
            txt_idCode.setText("IdCode: " + scannedId);
            txt_code.setText("Code: " + deviceData.getString(codeIndex));
            txt_name.setText("Name: " + deviceData.getString(nameIndex));
            txt_stage.setText("Stage: " + deviceData.getString(stageIndex));

            // Lấy vấn đề từ cơ sở dữ liệu
            String issues = deviceData.getString(issueIndex);
            if (issues != null) {
                String[] issuesArray = issues.split(",");
                for (String issue : issuesArray) {
                    issueList.add(issue.trim());
                }
            }

            // Quét lần đầu
            if (!scannedIdRecordingStatus.getOrDefault(scannedId, false)) {
                startTime = System.currentTimeMillis();
                scannedIdStartTime.put(scannedId, startTime);
                scannedIdRecordingStatus.put(scannedId, true);
                processingDevices.put(scannedId, new DeviceProcess(
                        deviceData.getString(codeIndex),
                        deviceData.getString(nameIndex),
                        deviceData.getString(stageIndex),
                        new ArrayList<>(issueList),
                        startTime
                ));
                //updateProcessingStatus(); // show textView
                //showIssueDialog(dialogView, issueList, true);
                showSelectOption(dialogView, issueList, true); // Pass dialogView and issueList here
            } else {
                // Lấy thông tin từ danh sách đang xử lý
                DeviceProcess deviceInfo = processingDevices.get(scannedId);
                if (deviceInfo != null) {
                    deviceInfo.issues.clear(); // Xóa các vấn đề cũ
                    deviceInfo.issues.addAll(issueList); // Cập nhật các vấn đề mới
                }
                endTime = System.currentTimeMillis();
                saveDataOnSecondScan(scannedId);
                resetScannedIdState(scannedId);
            }
        } else {
            // Không thấy dữ liệu
            Toast.makeText(this, "No data found for this device!", Toast.LENGTH_SHORT).show();
            txt_id.setText("No id found!!!");
            txt_code.setText("No code found!!!");
            txt_name.setText("No name found!!!");
        }

        deviceData.close();
    }

//    private void showSelectOption(View dialogView, ArrayList<String> issueList) {
//        String[] options = {"Dừng ngắn", "Dừng dài", "Đổi mã", "Đầu ca", "Phế phẩm", "Ngoài ra"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Chọn loại trường hợp:")
//                .setItems(options, (dialog, which) -> {
//                    String selectedOption = options[which];
//                    if ("Dừng ngắn".equals(selectedOption)) {
//                        showIssueDialog(dialogView, issueList, true); // Pass the dialogView and issueList here
//                    } else {
//                        scannedIssuesMap.put(scannedId, new ArrayList<>(Collections.singletonList(selectedOption)));
//                        scannedIdCodes.add(scannedId);
//                        Toast.makeText(this, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();
//                        updateProcessingStatus();
//                    }
//                })
//                .setNegativeButton("Close", (dialog, which) -> dialog.dismiss())
//                .create().show();
//    }

//    private void showSelectOption(View dialogView, ArrayList<String> issueList, boolean isFirstScan) {
//        String[] options = {"Dừng ngắn", "Dừng dài", "Phế phẩm", "Đổi mã, Thay dao", "Đầu cuối ca", "Ngoài ra"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View optionDialogView = inflater.inflate(R.layout.dialog_select_option, null);
//        ListView listView = optionDialogView.findViewById(R.id.list_view_options);
//
//        // Set up the ListView with the options
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, options);
//        listView.setAdapter(adapter);
//        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//
//        builder.setView(optionDialogView);
//
//        // Set Positive (OK) Button
//        builder.setPositiveButton("OK", (dialog, which) -> {
//            SparseBooleanArray checked = listView.getCheckedItemPositions();
//            ArrayList<String> selectedIssues = new ArrayList<>();
//
//            // Collect the selected issues
//            for (int i = 0; i < options.length; i++) {
//                if (checked.get(i)) {
//                    selectedIssues.add(options[i]);
//                }
//            }
//
//            // Check if "Dừng ngắn" was selected
//            if (selectedIssues.contains("Dừng ngắn")) {
//                showIssueDialog(dialogView, issueList, true); // Show the issue dialog
//            } else {
//                // Process other selected issues
//                scannedIssuesMap.put(scannedId, selectedIssues);
//                Toast.makeText(MainActivity.this, "Selected issues: " + selectedIssues, Toast.LENGTH_SHORT).show();
//            }
//
//            dialog.dismiss();
//        });
//
//        // Set Negative (Cancel) Button
//        builder.setNegativeButton("Cancel", (dialog, which) -> {
//            dialog.dismiss();
//            // reset trạng thái
//            if (isFirstScan) {
//                processingDevices.clear();
//                resetScannedIdState(scannedId);
//                isRecording = false;
//                scannedIdCodes.clear();
//                tempSelectedIssues.clear();
//            }
//        });
//
//        // Show the dialog
//        builder.create().show();
//    }



    private void showSelectOption(View dialogView, ArrayList<String> issueList, boolean isFirstScan) {
        String[] options = {"Dừng ngắn", "Dừng dài", "Phế phẩm", "Đổi mã, Thay dao", "Đầu cuối ca", "Ngoài ra"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View optionDialogView = inflater.inflate(R.layout.dialog_select_option, null);
        ListView listView = optionDialogView.findViewById(R.id.list_view_options);

        // Thiết lập ListView với các tùy chọn
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, options);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        builder.setView(optionDialogView);

        // Thiết lập nút Positive (OK)
        builder.setPositiveButton("OK", (dialog, which) -> {
            SparseBooleanArray checked = listView.getCheckedItemPositions();
            ArrayList<String> selectedTypeName = new ArrayList<>();

            // Lấy các tùy chọn đã chọn
            for (int i = 0; i < options.length; i++) {
                if (checked.get(i)) {
                    selectedTypeName.add(options[i]);
                }
            }

            // Lưu typeName vào DeviceProcess
            DeviceProcess deviceInfo = processingDevices.get(scannedId);
            if (deviceInfo != null) {
                deviceInfo.typeName = String.join(", ", selectedTypeName);
                Log.d("SelectedTypeName", "TypeName set to: " + deviceInfo.typeName); // Log để xác minh
            }

            // Hiển thị dialog issues nếu "Dừng ngắn" được chọn
            if (selectedTypeName.contains("Dừng ngắn")) {
                showIssueDialog(dialogView, issueList, true);
            } else {
                // Nếu không có vấn đề nào được chọn, chúng ta chỉ cần lưu typeName
                scannedIssuesMap.put(scannedId, new ArrayList<>()); // Thiết lập rỗng cho issues
            }

            dialog.dismiss();
        });

        // Thiết lập nút Negative (Cancel)
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
            // Reset trạng thái nếu là quét đầu tiên
            if (isFirstScan) {
                processingDevices.clear();
                resetScannedIdState(scannedId);
                isRecording = false;
                scannedIdCodes.clear();
                tempSelectedIssues.clear();
            }
        });

        // Hiện dialog
        builder.create().show();
    }

    private void showIssueDialog(View dialogView, ArrayList<String> issueList, boolean isFirstScan) {
        ListView listView = dialogView.findViewById(R.id.list_view_issues);
        issueList.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, issueList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Thiet lap cho viec xoa
//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            String selectedIssue = issueList.get(position);
//            if (selectedIssue != null && !selectedIssue.equals("Other")) {
//                showDeleteIssueDialog(selectedIssue, issueList, listView);
//            }
//        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> {
                    SparseBooleanArray checked = listView.getCheckedItemPositions();
                    ArrayList<String> selectedIssues = new ArrayList<>();
                    boolean otherSelected = false;
                    updateProcessingStatus();

                    for (int i = 0; i < issueList.size(); i++) {
                        if (checked.get(i)) {
                            selectedIssues.add(issueList.get(i));
                            if ("Other".equals(issueList.get(i))) {
                                otherSelected = true;
                            }
                        }
                    }
                    if (selectedIssues.size() > 5) {
                        Toast.makeText(MainActivity.this, "You cannot select more than 5 issues.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //tempSelectedIssues.clear();
                    //tempSelectedIssues.addAll(selectedIssues);

                    scannedIssuesMap.put(scannedId, selectedIssues);

                    //Neu other duoc chon
                    if (otherSelected) {
                        showNewIssueDialog(selectedIssues);
                    } else {
                        if (isFirstScan) {
                            scannedIdCodes.add(scannedId);
                        }
                    }
                })
                .setNegativeButton("Close", (dialog, which) -> {
                    dialog.dismiss();
                    // reset trạng thái
                    if (isFirstScan) {
                        processingDevices.clear();
                        resetScannedIdState(scannedId);
                        isRecording = false;
                        scannedIdCodes.clear();
                        tempSelectedIssues.clear();
                    }
                })
                .create()
                .show();
    }

    private void showDeleteIssueDialog(String issue, ArrayList<String> issueList, ListView listview) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Issue");
        builder.setMessage("Are you sure you want to delete the issue: " + issue + "?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            databaseHelper.deleteIssueFromDevice(scannedId, issue);

            issueList.remove(issue);
            Toast.makeText(MainActivity.this, "Issue deleted successfully.", Toast.LENGTH_SHORT).show();

            updateIssueList(listview, issueList);
        })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .show();
    }

    private void updateIssueList(ListView listview, ArrayList<String> issueList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, issueList);
        listview.setAdapter(adapter);
    }

    private void showNewIssueDialog (ArrayList<String> selectedIssues) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter New Issue");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String newIssue = input.getText().toString().trim();
            if(!newIssue.isEmpty()) {
                selectedIssues.add(newIssue);
                tempSelectedIssues.add(newIssue);

                databaseHelper.insertNewIssue(scannedId, newIssue);

                Toast.makeText(MainActivity.this, "New issue added.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Please enter valid issue.", Toast.LENGTH_SHORT).show();
            }
        })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                })
                .show();
    }

    // Phương thức để lưu dữ liệu trên lần quét thứ hai
    private void saveDataOnSecondScan(String scannedId) {
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - scannedIdStartTime.get(scannedId);
        String formattedStartTime = formatTime(scannedIdStartTime.get(scannedId));
        String formattedEndTime = formatTime(endTime);
        String formattedTotalTime = formatTotalTime(totalTime);

        // Kiểm tra xem scannedId có trong danh sách ghi âm không
        if (scannedIdRecordingStatus.containsKey(scannedId)) {
            ArrayList<String> selectedIssues = scannedIssuesMap.get(scannedId);

            // Lấy thông tin thiết bị
            DeviceProcess deviceInfo = processingDevices.get(scannedId);
            if (deviceInfo != null) {
                Log.d("DatabaseInsertion", "Inserting with TypeName: " + deviceInfo.typeName);

                // Lưu dữ liệu vào cơ sở dữ liệu
                databaseHelper.insertListData(
                        scannedId,
                        deviceInfo.code,
                        deviceInfo.name,
                        deviceInfo.stage,
                        deviceInfo.typeName,
                        String.join(", ", selectedIssues),
                        formattedStartTime,
                        formattedEndTime,
                        formattedTotalTime
                );

                Toast.makeText(this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
                // Xóa thiết bị khỏi danh sách đang xử lý
                processingDevices.remove(scannedId);
                updateProcessingStatus(); // Cập nhật trạng thái
            } else {
                Log.e("DeviceInfo", "DeviceInfo is null for scannedId: " + scannedId); // Log nếu deviceInfo là null
            }
        }
    }
    private void resetScannedIdState(String scannedId) {
        // reset Id nao da duoc insert vao db
        if (scannedIdRecordingStatus.containsKey(scannedId)) {
            scannedIdRecordingStatus.put(scannedId, false);
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


//    private void saveDataOnSecondScan(String scannedId) {
//        long endTime = System.currentTimeMillis();
//        long totalTime = endTime - scannedIdStartTime.get(scannedId);
//        String formattedStartTime = formatTime(scannedIdStartTime.get(scannedId));
//        String formattedEndTime = formatTime(endTime);
//        String formattedTotalTime = formatTotalTime(totalTime);
//
//        if (scannedIdRecordingStatus.containsKey(scannedId)) {
//            ArrayList<String> selectedIssues = scannedIssuesMap.get(scannedId);
//            if(selectedIssues != null) {
//                databaseHelper.insertListData(scannedId, txt_code.getText().toString(), txt_name.getText().toString(),
//                        String.join(", ", selectedIssues), formattedStartTime, formattedEndTime, formattedTotalTime);
//                Toast.makeText(this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "No issues selected.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }Vay Vay lam sao de toi co the




//    private void saveDataOnSecondScan(String scannedId, ArrayList<String> selectedIssue) {
//        long endTime = System.currentTimeMillis();
//        long totalTime = endTime - scannedIdStartTime.get(scannedId);
//        String formattedStartTime = formatTime(scannedIdStartTime.get(scannedId));
//        String formattedEndTime = formatTime(endTime);
//        String formattedTotalTime = formatTotalTime(totalTime);
//
//        if (scannedIdRecordingStatus.containsKey(scannedId)) {
//            databaseHelper.insertListData(scannedId, txt_code.getText().toString(), txt_name.getText().toString(),
//                    String.join(", ", selectedIssue), formattedStartTime, formattedEndTime, formattedTotalTime);
//            Toast.makeText(this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
//        }
//    }


//    private void handleScannedResult(String scanResult) {
//        Cursor deviceData = databaseHelper.selectData(scanResult);
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.dialog_information, null);
//
//        TextView txt_id = dialogView.findViewById(R.id.txt_id);
//        TextView txt_idCode = dialogView.findViewById(R.id.txt_idCode);
//        txt_code = dialogView.findViewById(R.id.txt_code);
//        txt_name = dialogView.findViewById(R.id.txt_name);
//        ListView listView = dialogView.findViewById(R.id.list_view_issues);
//
//        ArrayList<String> issueList = new ArrayList<>();
//
//        if (deviceData.moveToFirst()) {
//            int idIndex = deviceData.getColumnIndex(Database.DEVICE_ID);
//            int idCodeIndex = deviceData.getColumnIndex(Database.DEVICE_ID_CODE);
//            int codeIndex = deviceData.getColumnIndex(Database.DEVICE_CODE);
//            int nameIndex = deviceData.getColumnIndex(Database.DEVICE_NAME);
//            int issueIndex = deviceData.getColumnIndex(Database.DEVICE_ISSUE);
//
//            scannedId = deviceData.getString(idCodeIndex); // Lưu ID quét được
//            txt_id.setText("ID: " + deviceData.getString(idIndex));
//            txt_idCode.setText("IdCode: " + scannedId);
//            txt_code.setText("Code: " + deviceData.getString(codeIndex));
//            txt_name.setText("Name: " + deviceData.getString(nameIndex));
//
//            // lay van de tu csdl
//            String issues = deviceData.getString(issueIndex);
//            if (issues != null) {
//                String[] issuesArray = issues.split(",");
//                for (String issue : issuesArray) {
//                    issueList.add(issue.trim());
//                }
//            }
//
//            // quet lan dau
//            if (!scannedIdRecordingStatus.getOrDefault(scannedId, false)) {
//                startTime = System.currentTimeMillis();
//                scannedIdStartTime.put(scannedId, startTime);
//                scannedIdRecordingStatus.put(scannedId, true);
//                showIssueDialog(dialogView, issueList, true);
//            } else {
//                if (scannedIdRecordingStatus.containsKey(scannedId)) {
//                    endTime = System.currentTimeMillis();
//                    saveDataOnSecondScan(scannedId);
//                    resetScannedIdState(scannedId);
//                } else {
//                    startTime = System.currentTimeMillis();
//                    showIssueDialog(dialogView, issueList, false);
//                }
//            }
//        } else {
//            // K thay data
//            Toast.makeText(this, "No data found for this device!", Toast.LENGTH_SHORT).show();
//            txt_id.setText("No id found!!!");
//            txt_code.setText("No code found!!!");
//            txt_name.setText("No name found!!!");
//        }
//        deviceData.close();
//    }
