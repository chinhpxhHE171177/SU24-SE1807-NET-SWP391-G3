package com.example.qrcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DeviceProcessAdapter extends ArrayAdapter<DeviceProcess> {
    public DeviceProcessAdapter(Context context, ArrayList<DeviceProcess> deviceProcesses) {
        super(context, 0, deviceProcesses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeviceProcess deviceProcess = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_device_process, parent, false);
        }

        TextView txtCode = convertView.findViewById(R.id.txtCode);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtLine = convertView.findViewById(R.id.txtLine);
        TextView txtType = convertView.findViewById(R.id.txtType);
        TextView txtStartTime = convertView.findViewById(R.id.txtStartTime);

        txtCode.setText("Code: " + deviceProcess.getCode());
        txtName.setText("Name: " + deviceProcess.getName());
        txtLine.setText("Line: " + deviceProcess.getLine());
        txtType.setText("Type: " + deviceProcess.getTypeName());
        txtStartTime.setText("Start Time: " + formatTime(deviceProcess.getStartTime()));

        return convertView;
    }

    private String formatTime(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date resultDate = new Date(timeInMillis);
        return sdf.format(resultDate);
    }
}
