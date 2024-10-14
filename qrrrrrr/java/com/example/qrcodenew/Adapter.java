package com.example.qrcodenew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

//    @Override
//    public void onBindViewHolder(@NonNull final Holder holder, int position) {
//        Device model = arrayList.get(position);
//        final String idCode = model.getIdCode(); // Lấy ID để xóa
//        final String code = model.getCode();
//        final String name = model.getName();
//        final String issue = model.getIssue();
//
//        holder.txt_Code.setText(code);
//        holder.txt_Name.setText(name);
//        holder.txt_Issue.setText(issue);
//
//        // Xử lý sự kiện click nút xóa
//        holder.img_Delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int currentPosition = holder.getAdapterPosition();
//                if (currentPosition != RecyclerView.NO_POSITION) {
//                    Device deviceToDelete = arrayList.get(currentPosition);
//                    // Xóa từ cơ sở dữ liệu
//                    boolean deleted = databaseHelper.deleteDevice(deviceToDelete.getIdCode());
//                    if (deleted) {
//                        // Xóa item từ danh sách
//                        arrayList.remove(currentPosition);
//                        // Thông báo cho RecyclerView về việc xóa item
//                        notifyItemRemoved(currentPosition);
//                        notifyItemRangeChanged(currentPosition, arrayList.size());
//
//                        // Show toast message
//                        showToast("Device deleted");
//                    } else {
//                        showToast("Failed to delete");
//                    }
//                }
//            }
//        });
//    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        Device model = arrayList.get(position);
        final String idCode = model.getIdCode(); // Lấy ID để xóa
        final String code = model.getCode();
        final String name = model.getName();
        final String issue = model.getIssue();

        holder.txt_Code.setText(code);
        holder.txt_Name.setText(name);
        holder.txt_Issue.setText(issue);

        // Xử lý sự kiện click nút xóa
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
                                    // Nếu nhấn "OK", thực hiện xóa
                                    Device deviceToDelete = arrayList.get(currentPosition);
                                    // Xóa từ cơ sở dữ liệu
                                    boolean deleted = databaseHelper.deleteDevice(deviceToDelete.getIdCode());
                                    if (deleted) {
                                        // Xóa item từ danh sách
                                        arrayList.remove(currentPosition);
                                        // Thông báo cho RecyclerView về việc xóa item
                                        notifyItemRemoved(currentPosition);
                                        notifyItemRangeChanged(currentPosition, arrayList.size());

                                        // Hiển thị thông báo
                                        showToast("The device removed.");
                                    } else {
                                        showToast("Delete not successful.");
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Nếu nhấn "Hủy", đóng hộp thoại
                                    dialog.dismiss();
                                }
                            })
                            .show(); // Hiển thị hộp thoại
                }
            }
        });
    }


    // Method to show toast messages
    private void showToast(String message) {
        // Cancel previous toast if it exists
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
        private TextView txt_Code, txt_Name, txt_Issue;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img_photo = itemView.findViewById(R.id.image_shop);
            txt_Code = itemView.findViewById(R.id.txt_Code);
            txt_Name = itemView.findViewById(R.id.txt_Name);
            txt_Issue = itemView.findViewById(R.id.txt_Issue);
            img_Edit = itemView.findViewById(R.id.btn_Edit_Row);
            img_Delete = itemView.findViewById(R.id.btn_Delete_Row);
        }
    }
}


//package com.example.qrcodenew;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
//
//    private Context context;
//    private ArrayList<Device> arrayList;
//    private DatabaseHelper databaseHelper;
//
//    public Adapter(Context context, ArrayList<Device> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//        databaseHelper = new DatabaseHelper(context);
//    }
//
//    @NonNull
//    @Override
//    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
//        return new Holder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull Holder holder, final int position) {
//        Device model = arrayList.get(position);
//        final String idCode = model.getIdCode();
//        final String code = model.getCode();
//        final String name = model.getName();
//        final String issue = model.getIssue();
//
//        holder.txt_Code.setText(code);
//        holder.txt_Name.setText(name);
//        holder.txt_Issue.setText(issue);
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class Holder extends RecyclerView.ViewHolder {
//
//        private ImageView img_photo;
//        private ImageButton img_Edit;
//        private TextView txt_Code, txt_Name, txt_Issue;
//
//        public Holder(@NonNull View itemView) {
//            super(itemView);
//            img_photo = itemView.findViewById(R.id.image_shop);
//            txt_Code = itemView.findViewById(R.id.txt_Code);
//            txt_Name = itemView.findViewById(R.id.txt_Name);
//            txt_Issue = itemView.findViewById(R.id.txt_Issue);
//            img_Edit = itemView.findViewById(R.id.btn_Edit_Row);
//        }
//    }
//}
