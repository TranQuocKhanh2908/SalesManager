package com.example.projectandroid.HelperClasses.Shopping.ListPromotion;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroid.HelperClasses.Shopping.ListBill.ListBillHelperClass;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MShopping.ListPromotion.DetailPromotion;

import java.util.ArrayList;

public class ListPromotionAdapter extends  RecyclerView.Adapter<ListPromotionAdapter.ListPromotionViewHolder> implements Filterable {

    private Context context;
    int singleData;
    ArrayList<ListPromotionHelperClass> listPromotionHelperClassArrayList;
    ArrayList<ListPromotionHelperClass> oldListPromotionHelperClassArrayList;
    SQLiteDatabase SQLdb;

    public ListPromotionAdapter(Context context, int singleData, ArrayList<ListPromotionHelperClass> listPromotionHelperClassArrayList, SQLiteDatabase SQLdb) {
        this.context = context;
        this.singleData = singleData;
        this.listPromotionHelperClassArrayList = listPromotionHelperClassArrayList;
        this.oldListPromotionHelperClassArrayList = listPromotionHelperClassArrayList;
        this.SQLdb = SQLdb;
    }

    @NonNull
    @Override
    public ListPromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_promotion_card_desgin,null);
        return new ListPromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPromotionViewHolder holder, int position) {

        final ListPromotionHelperClass listPromotionHelperClass = listPromotionHelperClassArrayList.get(position);
        byte[] image = listPromotionHelperClass.getImagePromotion();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.TextNPromotion.setText(listPromotionHelperClass.getNamePromotion());
            holder.TextPPromotion.setText(listPromotionHelperClass.getPresentPromotion());
            holder.TextSPromotion.setText(listPromotionHelperClass.getStartDayPromotion());
            holder.TextEPromotion.setText(listPromotionHelperClass.getEndDayPromotion());
            holder.ImagePromotion.setImageBitmap(bitmap);

            holder.DetailPromotion_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer promotion_ID = listPromotionHelperClass.getIdPromotion();
                    Intent intent = new Intent(context, DetailPromotion.class);
                    intent.putExtra("Id_Promotion", promotion_ID);
                    context.startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return listPromotionHelperClassArrayList.size();
    }

    public class ListPromotionViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout DetailPromotion_btn;
        ImageView ImagePromotion;
        TextView TextPPromotion, TextSPromotion, TextEPromotion, TextNPromotion;

        public ListPromotionViewHolder(@NonNull View itemView) {
            super(itemView);

            DetailPromotion_btn = itemView.findViewById(R.id.Detail_promotion);
            ImagePromotion = itemView.findViewById(R.id.img_Promotion);
            TextNPromotion = itemView.findViewById(R.id.Txv_nameProduct);
            TextPPromotion = itemView.findViewById(R.id.Txv_percentPromotion);
            TextSPromotion = itemView.findViewById(R.id.Txv_startDayPromotion);
            TextEPromotion = itemView.findViewById(R.id.Txv_endDayPromotion);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    listPromotionHelperClassArrayList = oldListPromotionHelperClassArrayList;
                }else{

                    ArrayList<ListPromotionHelperClass> listSearch = new ArrayList<>();
                    for(ListPromotionHelperClass listPromotionHelperClass : oldListPromotionHelperClassArrayList ){
                        if(listPromotionHelperClass.getNamePromotion().toLowerCase().contains(strSearch.toLowerCase())){
                            listSearch.add(listPromotionHelperClass);
                        }
                    }

                    listPromotionHelperClassArrayList = listSearch;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listPromotionHelperClassArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listPromotionHelperClassArrayList = (ArrayList<ListPromotionHelperClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
