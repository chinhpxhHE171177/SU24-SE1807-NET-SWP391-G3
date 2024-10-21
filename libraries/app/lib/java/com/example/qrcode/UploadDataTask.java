package com.example.qrcode;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UploadDataTask extends AsyncTask<ArrayList<Device_History>, Void, Boolean> {
    private Context context;
    private OnUploadCompleteListener listener;

    // Define a listener interface
    public interface OnUploadCompleteListener {
        void onUploadComplete(Boolean success);
    }

    // Constructor now accepts a listener
    public UploadDataTask(Context context, OnUploadCompleteListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(ArrayList<Device_History>... params) {
        ArrayList<Device_History> deviceList = params[0];
        try {
            URL url = new URL("http://192.168.1.8:8080/ssmqrcode/uploadData");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.connect();

            JSONArray jsonArray = new JSONArray();
            for (Device_History device : deviceList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", device.getId());
                jsonObject.put("code", device.getCode());
                jsonObject.put("name", device.getName());
                jsonObject.put("stage", device.getStage());
                jsonObject.put("issue", device.getIssue());
                jsonObject.put("startTime", device.getStartTime());
                jsonObject.put("endTime", device.getEndTime());
                jsonObject.put("duration", device.getTotalTime());
                jsonArray.put(jsonObject);
            }

            OutputStream os = connection.getOutputStream();
            os.write(jsonArray.toString().getBytes());
            os.close();

            Log.d("UploadDataTask", "JSON to send: " + jsonArray.toString());

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(context, "Data upload successful", Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onUploadComplete(true);
            }
        } else {
            Toast.makeText(context, "Data upload failed", Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onUploadComplete(false);
            }
        }
    }
}
