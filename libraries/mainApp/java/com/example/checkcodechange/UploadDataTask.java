package com.example.checkcodechange;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadDataTask extends AsyncTask<String, Void, Boolean> {
    private Context context;

    public UploadDataTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        String code = params[0]; // Lấy mã quét được
        try {
            URL url = new URL("http://192.168.11.102:8080/CheckCodeChange/uploadData"); // Thay đổi URL nếu cần
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.connect();

            // Tạo đối tượng JSON để gửi dữ liệu
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", code); // Chỉ gửi mã quét

            OutputStream os = connection.getOutputStream();
            os.write(jsonObject.toString().getBytes()); // Gửi JSON
            os.close();

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
            Toast.makeText(context, "Dữ liệu đã được tải lên thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Gửi dữ liệu thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
