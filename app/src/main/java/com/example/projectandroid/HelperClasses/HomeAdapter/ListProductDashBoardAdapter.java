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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroid.R;
import com.example.projectandroid.User.MProduct.ListProduct.DetailProduct;

import java.util.ArrayList;

public class ListProductDashBoardAdapter extends RecyclerView.Adapter<ListProductDashBoardAdapter.ListProductDashBoardViewHolder> {

    private Context context;
    int singleData;
    ArrayList<ListProductDashBoardHelperClass> listProductDashBoardHelperClasses;
    SQLiteDatabase SQLdb;

    public ListProductDashBoardAdapter(Context context, int singleData, ArrayList<ListProductDashBoardHelperClass> listProductDashBoardHelperClasses, SQLiteDatabase SQLdb) {
        this.context = context;
        this.singleData = singleData;
        this.listProductDashBoardHelperClasses = listProductDashBoardHelperClasses;
        this.SQLdb = SQLdb;
    }

    @NonNull
    @Override
    public ListProductDashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_product_dashboard_card_design, null);
        return new ListProductDashBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListProductDashBoardViewHolder holder, int position) {
        final ListProductDashBoardHelperClass listProductDashBoardHelperClass = listProductDashBoardHelperClasses.get(position);
        byte[] image = listProductDashBoardHelperClass.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.nameProduct.setText(listProductDashBoardHelperClass.getName());
        holder.qualityProduct.setText(listProductDashBoardHelperClass.getQuality());
        holder.imageProduct.setImageBitmap(bitmap);

        holder.detailProductDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer product_ID = listProductDashBoardHelperClass.getIdProduct();
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra("Id_Product", String.valueOf(product_ID));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProductDashBoardHelperClasses.size();
    }

    public static class ListProductDashBoardViewHolder extends RecyclerView.ViewHolder{

        LinearLayout detailProductDashboard;
        ImageView imageProduct;
        TextView nameProduct, qualityProduct;

        public ListProductDashBoardViewHolder(@NonNull View itemView) {
            super(itemView);

            detailProductDashboard = itemView.findViewById(R.id.detail_product_dashboard);
            imageProduct = itemView.findViewById(R.id.product_image_dashboard);
            nameProduct = itemView.findViewById(R.id.Product_Name_dashboard);
            qualityProduct = itemView.findViewById(R.id.Product_Quality_dashboard);
        }
    }
}
