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
import com.example.projectandroid.User.MShopping.ListPromotion.DetailPromotion;

import java.util.ArrayList;

public class ListPromotionDashBoardAdapter extends RecyclerView.Adapter<ListPromotionDashBoardAdapter.ListPromotionDashBoardViewHolder> {

    private Context context;
    int singleData;
    ArrayList<ListPromotionDashBoardHelperClass> listPromotionDashBoardHelperClassArrayList;
    SQLiteDatabase SQLdb;

    public ListPromotionDashBoardAdapter(Context context, int singleData, ArrayList<ListPromotionDashBoardHelperClass> listPromotionDashBoardHelperClassArrayList, SQLiteDatabase SQLdb) {
        this.context = context;
        this.singleData = singleData;
        this.listPromotionDashBoardHelperClassArrayList = listPromotionDashBoardHelperClassArrayList;
        this.SQLdb = SQLdb;
    }

    @NonNull
    @Override
    public ListPromotionDashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_promotion_dashboard_card_design, null);
        return new ListPromotionDashBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPromotionDashBoardViewHolder holder, int position) {
        final ListPromotionDashBoardHelperClass listPromotionDashBoardHelperClass = listPromotionDashBoardHelperClassArrayList.get(position);
        byte[] image = listPromotionDashBoardHelperClass.getImage_promotion();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageProduct.setImageBitmap(bitmap);
        holder.nameProduct.setText(listPromotionDashBoardHelperClass.getName_promotion());
        holder.startDay.setText(listPromotionDashBoardHelperClass.getStartDay_promotion());
        holder.endDay.setText(listPromotionDashBoardHelperClass.getEndDay_promotion());

        holder.detailPromotionDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer promotion_ID = listPromotionDashBoardHelperClass.getIdPromotion();
                Intent intent = new Intent(context, DetailPromotion.class);
                intent.putExtra("Id_Promotion", promotion_ID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPromotionDashBoardHelperClassArrayList.size();
    }

    public static class ListPromotionDashBoardViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout detailPromotionDashboard;
        ImageView imageProduct;
        TextView nameProduct, startDay, endDay;

        public ListPromotionDashBoardViewHolder(@NonNull View itemView) {
            super(itemView);

            detailPromotionDashboard = itemView.findViewById(R.id.detail_promotion_dashboard);
            imageProduct = itemView.findViewById(R.id.promotion_image);
            nameProduct = itemView.findViewById(R.id.promotion_name);
            startDay = itemView.findViewById(R.id.promotion_startDay);
            endDay = itemView.findViewById(R.id.promotion_endDay);
        }
    }
}
