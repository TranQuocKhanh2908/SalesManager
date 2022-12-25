package com.example.projectandroid.HelperClasses.Product.ListProduct;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroid.HelperClasses.Product.ListTypeProduct.ListTypeProductHelperClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MProduct.ListProduct.DetailProduct;
import com.example.projectandroid.User.MShopping.ListBill.DetailBill;

import java.util.ArrayList;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ListProductViewHolder> implements Filterable {

    private Context context;
    int singleData;
    ArrayList<ListProductHelperClass> listProductHelperClassArrayList;
    ArrayList<ListProductHelperClass> oldListProductHelperClassArrayList;
    SQLiteDatabase SQLdb;

    public ListProductAdapter(Context context, int singleData, ArrayList<ListProductHelperClass> listProductHelperClassArrayList, SQLiteDatabase SQLdb) {
        this.context = context;
        this.singleData = singleData;
        this.listProductHelperClassArrayList = listProductHelperClassArrayList;
        this.oldListProductHelperClassArrayList = listProductHelperClassArrayList;
        this.SQLdb = SQLdb;
    }

    @NonNull
    @Override
    public ListProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater inflater = LayoutInflater.from(context);
       View view = inflater.inflate(R.layout.list_product_card_desgin, null);
       return new ListProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListProductViewHolder holder, int position) {

        final ListProductHelperClass listProductHelperClass = listProductHelperClassArrayList.get(position);
        byte[] image = listProductHelperClass.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.TextNProduct.setText(listProductHelperClass.getName());
            holder.TextQProduct.setText(listProductHelperClass.getQuality());
            holder.ImageProduct.setImageBitmap(bitmap);

            holder.DetailProduct_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer product_ID = listProductHelperClass.getIdProduct();
                    Intent intent = new Intent(context, DetailProduct.class);
                    intent.putExtra("Id_Product", String.valueOf(product_ID));
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return listProductHelperClassArrayList.size();
    }

    public class ListProductViewHolder extends RecyclerView.ViewHolder{

        ImageView ImageProduct,DetailProduct_btn;
        TextView TextNProduct,TextQProduct;

        public ListProductViewHolder(@NonNull View itemView) {
            super(itemView);

            DetailProduct_btn = itemView.findViewById(R.id.detail_Product);
            TextNProduct = itemView.findViewById(R.id.Txv_NameProduct);
            TextQProduct = itemView.findViewById(R.id.Txv_QualityProduct);
            ImageProduct = itemView.findViewById(R.id.img_Product);

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    listProductHelperClassArrayList = oldListProductHelperClassArrayList;
                }else{

                    ArrayList<ListProductHelperClass> listSearch = new ArrayList<>();
                    for(ListProductHelperClass listProductHelperClass : oldListProductHelperClassArrayList ){
                        if(listProductHelperClass.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            listSearch.add(listProductHelperClass);
                        }
                    }

                    listProductHelperClassArrayList = listSearch;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listProductHelperClassArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listProductHelperClassArrayList = (ArrayList<ListProductHelperClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
