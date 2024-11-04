package com.example.qrcode;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private Context context;
    private ArrayList<Device> arrayList;
    private DatabaseHelper databaseHelper;
    private Toast mToast;

    public Adapter(Context context, ArrayList<Device> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        Device model = arrayList.get(position);
        final String idCode = model.getIdCode();
        final String code = model.getCode();
        final String name = model.getName();
        final String stage = model.getStageName();
        final String issue = model.getIssue();

        //holder.txt_Id_Code.setText("Id Code: " + idCode);
        holder.txt_Code.setText("Code: " + code);
        holder.txt_Name.setText("Name: " + name);
        holder.txt_Stage.setText("Stage: " + stage);
        holder.txt_Issue.setText("Issue: " + issue);

        holder.img_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                if(currentPosition != RecyclerView.NO_POSITION) {
                    Device deviceToEdit = arrayList.get(currentPosition);
                    showEditDialog(deviceToEdit);
                }
            }
        });

        holder.img_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Hiển thị hộp thoại xác nhận
                    new AlertDialog.Builder(context)
                            .setTitle("Confirm Deletion")
                            .setMessage("Are you sure you want to delete this device?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Device deviceToDelete = arrayList.get(currentPosition);
                                    boolean deleted = databaseHelper.deleteDevice(deviceToDelete.getIdCode());
                                    if (deleted) {
                                        arrayList.remove(currentPosition);
                                        notifyItemRemoved(currentPosition);
                                        notifyItemRangeChanged(currentPosition, arrayList.size());
                                        showToast("The device removed.");
                                    } else {
                                        showToast("Delete not successful.");
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        });
    }


    private void showEditDialog(final Device device) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_device, null);
        dialogBuilder.setView(dialogView);

        final EditText editCode = dialogView.findViewById(R.id.edit_code);
        final EditText editName = dialogView.findViewById(R.id.edit_name);
        final EditText editStage = dialogView.findViewById(R.id.edit_stage);
        final EditText editIssue = dialogView.findViewById(R.id.edit_issue);

        editCode.setText(device.getCode());
        editName.setText(device.getName());
        editStage.setText(device.getStageName());
        editIssue.setText(device.getIssue());

        dialogBuilder.setTitle("Edit Device");
        dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                device.setCode(editCode.getText().toString());
                device.setName(editName.getText().toString());
                device.setStageName(editStage.getText().toString());
                device.setIssue(editIssue.getText().toString());

                // Update
                boolean updated = databaseHelper.updateDevice(device);
                if(updated) {
                    notifyItemChanged(arrayList.indexOf(device));
                    showToast("Device updated successfully!");
                } else {
                    showToast("Update failed!");
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", null);
        dialogBuilder.create().show();
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

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView img_photo;
        private ImageButton img_Edit, img_Delete;
        private TextView txt_Id_Code, txt_Code, txt_Name, txt_Issue, txt_Stage;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.image_shop);
            //txt_Id_Code = itemView.findViewById(R.id.txt_Id_Code);
            txt_Code = itemView.findViewById(R.id.txt_Code);
            txt_Name = itemView.findViewById(R.id.txt_Name);
            txt_Stage = itemView.findViewById(R.id.txt_Stage);
            txt_Issue = itemView.findViewById(R.id.txt_Issue);
            img_Edit = itemView.findViewById(R.id.btn_Edit_Row);
            img_Delete = itemView.findViewById(R.id.btn_Delete_Row);
        }
    }
}
