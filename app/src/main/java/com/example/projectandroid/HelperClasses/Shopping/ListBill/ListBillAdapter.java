package com.example.projectandroid.HelperClasses.Shopping.ListBill;

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

import com.example.projectandroid.HelperClasses.Product.ListHistoryImportProduct.ListHistoryImportProductHelperClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MShopping.ListBill.DetailBill;

import java.util.ArrayList;

public class ListBillAdapter extends RecyclerView.Adapter<ListBillAdapter.ListBillViewHolder> implements Filterable {

    private Context context;
    int singleData;
    ArrayList<ListBillHelperClass> listBillHelperClassArrayList;
    ArrayList<ListBillHelperClass> oldListBillHelperClassArrayList;
    SQLiteDatabase SQLdb;
    SqlDatabaseHelper db;

    public ListBillAdapter(Context context, int singleData, ArrayList<ListBillHelperClass> listBillHelperClassArrayList, SQLiteDatabase SQLdb) {
        this.context = context;
        this.listBillHelperClassArrayList = listBillHelperClassArrayList;
        this.oldListBillHelperClassArrayList = listBillHelperClassArrayList;
        this.singleData = singleData;
        this.SQLdb = SQLdb;
    }

    @NonNull
    @Override
    public ListBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_bill_card_desgin,null);
        return new ListBillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBillViewHolder holder, int position) {
        final ListBillHelperClass listBillHelperClass = listBillHelperClassArrayList.get(position);
        byte[] image = listBillHelperClass.getImageBill();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.TextNBill.setText(listBillHelperClass.getNameBill());
            holder.TextQuaBill.setText(listBillHelperClass.getQualityBill());
            holder.TextCreBill.setText(listBillHelperClass.getCreateDayBill());
            holder.ImageBill.setImageBitmap(bitmap);

            holder.DetailBill_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer bill_ID = listBillHelperClass.getIdBill();
                    Intent intent = new Intent(context, DetailBill.class);
                    intent.putExtra("Id_Bill", bill_ID);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return listBillHelperClassArrayList.size();
    }

    public class ListBillViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout DetailBill_btn;
        ImageView ImageBill;
        TextView TextNBill, TextCreBill, TextQuaBill;

        public ListBillViewHolder(@NonNull View itemView) {
            super(itemView);

            DetailBill_btn = itemView.findViewById(R.id.Detail_bill);
            ImageBill = itemView.findViewById(R.id.img_Bill);
            TextNBill = itemView.findViewById(R.id.Txv_NameBill);
            TextCreBill = itemView.findViewById(R.id.Txv_CreateDay);
            TextQuaBill = itemView.findViewById(R.id.Txv_Quality);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    listBillHelperClassArrayList = oldListBillHelperClassArrayList;
                }else{

                    ArrayList<ListBillHelperClass> listSearch = new ArrayList<>();
                    for(ListBillHelperClass listBillHelperClass : oldListBillHelperClassArrayList ){
                        if(listBillHelperClass.getNameBill().toLowerCase().contains(strSearch.toLowerCase())){
                            listSearch.add(listBillHelperClass);
                        }
                    }

                    listBillHelperClassArrayList = listSearch;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listBillHelperClassArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listBillHelperClassArrayList = (ArrayList<ListBillHelperClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

