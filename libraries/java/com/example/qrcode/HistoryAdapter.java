package com.example.qrcode;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Holder> {

    private Context context;
    private ArrayList<Device_History> arrayList;
    private DatabaseHelper databaseHelper;
    private Toast mToast;

    public HistoryAdapter(Context context, ArrayList<Device_History> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_history, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        Device_History model = arrayList.get(position);
        holder.txt_Code.setText(model.getCode());
        holder.txt_Name.setText(model.getName());
        holder.txt_Issue.setText(model.getIssue());
        holder.txt_StartTime.setText(model.getStartTime());
        holder.txt_EndTime.setText(model.getEndTime());
        holder.txt_Duration.setText(model.getTotalTime());
        holder.txt_ErrorCount.setText("Errors: " + model.getRecordCount());

        holder.img_Delete.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition != RecyclerView.NO_POSITION) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirm Deletion")
                        .setMessage("Are you sure want to delete this history record?")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // Nếu nhấn "OK", thực hiện xóa
                            boolean deleted = databaseHelper.deleteHistoryBId(model.getId());
                            if (deleted) {
                                arrayList.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                                notifyItemRangeChanged(currentPosition, arrayList.size());
                                showToast("History record deleted");
                            } else {
                                showToast("Failed to delete");
                            }
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            }
        });
    }


    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private ImageView img_photo;
        private ImageButton img_Delete;
        private TextView txt_Code, txt_Name, txt_Issue, txt_ErrorCount, txt_StartTime, txt_EndTime, txt_Duration;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.image_shop);
            txt_Code = itemView.findViewById(R.id.txt_Code);
            txt_Name = itemView.findViewById(R.id.txt_Name);
            txt_Issue = itemView.findViewById(R.id.txt_Issue);
            txt_ErrorCount = itemView.findViewById(R.id.txt_Error_Count);
            txt_StartTime = itemView.findViewById(R.id.txt_Start_Time);
            txt_EndTime = itemView.findViewById(R.id.txt_End_Time);
            txt_Duration = itemView.findViewById(R.id.txt_Duration);
            img_Delete = itemView.findViewById(R.id.btn_Delete_Row);
        }
    }
}
