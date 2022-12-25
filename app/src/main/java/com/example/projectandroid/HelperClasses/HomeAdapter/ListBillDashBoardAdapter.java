package com.example.projectandroid.HelperClasses.HomeAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroid.R;
import com.example.projectandroid.User.MProduct.ListProduct.DetailProduct;
import com.example.projectandroid.User.MShopping.ListBill.DetailBill;

import java.util.ArrayList;

public class ListBillDashBoardAdapter extends RecyclerView.Adapter<ListBillDashBoardAdapter.ListBillDashBoardViewHolder> {

    private Context context;
    int singleData;
    ArrayList<ListBillDashBoardHelperClass> listBillDashBoardHelperClassArrayList;
    SQLiteDatabase SQLdb;

    public ListBillDashBoardAdapter(Context context, int singleData, ArrayList<ListBillDashBoardHelperClass> listBillDashBoardHelperClassArrayList, SQLiteDatabase SQLdb) {
        this.context = context;
        this.singleData = singleData;
        this.listBillDashBoardHelperClassArrayList = listBillDashBoardHelperClassArrayList;
        this.SQLdb = SQLdb;
    }

    @NonNull
    @Override
    public ListBillDashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_bill_dashboard_card_design, null);
        return new ListBillDashBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBillDashBoardViewHolder holder, int position) {
        final ListBillDashBoardHelperClass listBillDashBoardHelperClass = listBillDashBoardHelperClassArrayList.get(position);
        byte[] image = listBillDashBoardHelperClass.getImageBill();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageProduct.setImageBitmap(bitmap);
        holder.nameProduct.setText(listBillDashBoardHelperClass.getNameBill());
        holder.createDay.setText(listBillDashBoardHelperClass.getCreateDay());
        holder.createTime.setText(listBillDashBoardHelperClass.getCreateTime());

        holder.detailBillDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer bill_ID = listBillDashBoardHelperClass.getIdBill();
                Intent intent = new Intent(context, DetailBill.class);
                intent.putExtra("Id_Bill", bill_ID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBillDashBoardHelperClassArrayList.size();
    }

    public static class ListBillDashBoardViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout detailBillDashboard;
        ImageView imageProduct;
        TextView nameProduct, createDay, createTime;

        public ListBillDashBoardViewHolder(@NonNull View itemView) {
            super(itemView);

            detailBillDashboard = itemView.findViewById(R.id.bill_detail_dashboard_btn);
            imageProduct = itemView.findViewById(R.id.bill_product_image);
            nameProduct = itemView.findViewById(R.id.bill_product_name);
            createDay = itemView.findViewById(R.id.bill_CreateDay);
            createTime = itemView.findViewById(R.id.bill_CreateTime);
        }
    }
}
