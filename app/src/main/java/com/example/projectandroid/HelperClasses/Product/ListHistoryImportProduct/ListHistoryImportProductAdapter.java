package com.example.projectandroid.HelperClasses.Product.ListHistoryImportProduct;

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

import com.example.projectandroid.HelperClasses.Product.ListTypeProduct.ListTypeProductHelperClass;
import com.example.projectandroid.HelperClasses.Shopping.ListBill.ListBillAdapter;
import com.example.projectandroid.HelperClasses.Shopping.ListBill.ListBillHelperClass;
import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MProduct.ListHistoryImportProduct.DetailHistoryImportProduct;
import com.example.projectandroid.User.MShopping.ListBill.DetailBill;

import java.util.ArrayList;

public class ListHistoryImportProductAdapter extends RecyclerView.Adapter<ListHistoryImportProductAdapter.ListHistoryImportProductViewHolder> implements Filterable {

    private Context context;
    int singleData;
    ArrayList<ListHistoryImportProductHelperClass> listHistoryImportProductHelperClassArrayList;
    ArrayList<ListHistoryImportProductHelperClass> oldListHistoryImportProductHelperClassArrayList;
    SQLiteDatabase SQLdb;

    public ListHistoryImportProductAdapter(Context context, int singleData, ArrayList<ListHistoryImportProductHelperClass> listHistoryImportProductHelperClassArrayList, SQLiteDatabase SQLdb) {
        this.context = context;
        this.singleData = singleData;
        this.listHistoryImportProductHelperClassArrayList = listHistoryImportProductHelperClassArrayList;
        this.oldListHistoryImportProductHelperClassArrayList = listHistoryImportProductHelperClassArrayList;
        this.SQLdb = SQLdb;
    }

    @NonNull
    @Override
    public ListHistoryImportProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_history_import_product_card_desgin,null);
        return new ListHistoryImportProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHistoryImportProductViewHolder holder, int position) {
        final ListHistoryImportProductHelperClass listHistoryImportProductHelperClass = listHistoryImportProductHelperClassArrayList.get(position);
        byte[] image = listHistoryImportProductHelperClass.getImageImportProduct();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            holder.TextNImportProduct.setText(listHistoryImportProductHelperClass.getNameImportProduct());
            holder.TextCreImportProduct.setText(listHistoryImportProductHelperClass.getCreateDayImportProduct());
            holder.TextOldQuaImportProduct.setText(listHistoryImportProductHelperClass.getOldQualityImportProduct());
            holder.TextNewQuaImportProduct.setText(listHistoryImportProductHelperClass.getNewQualityImportProduct());
            holder.ImageImportProduct.setImageBitmap(bitmap);

            holder.DetailImportProduct_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer importProduct_ID = listHistoryImportProductHelperClass.getIdBImportProduct();
                    Intent intent = new Intent(context, DetailHistoryImportProduct.class);
                    intent.putExtra("Id_Import_Product", importProduct_ID);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return listHistoryImportProductHelperClassArrayList.size();
    }

    public class ListHistoryImportProductViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout DetailImportProduct_btn;
        ImageView ImageImportProduct;
        TextView TextNImportProduct, TextCreImportProduct, TextOldQuaImportProduct, TextNewQuaImportProduct;

        public ListHistoryImportProductViewHolder(@NonNull View itemView) {
            super(itemView);

            DetailImportProduct_btn = itemView.findViewById(R.id.detail_import_product);
            ImageImportProduct = itemView.findViewById(R.id.img_import_product);
            TextNImportProduct = itemView.findViewById(R.id.Txv_nameProduct_import_product);
            TextCreImportProduct = itemView.findViewById(R.id.Txv_importDay_import_product);
            TextOldQuaImportProduct = itemView.findViewById(R.id.Txv_oldQuality_import_product);
            TextNewQuaImportProduct = itemView.findViewById(R.id.Txv_newQuality_import_product);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    listHistoryImportProductHelperClassArrayList = oldListHistoryImportProductHelperClassArrayList;
                }else{

                    ArrayList<ListHistoryImportProductHelperClass> listSearch = new ArrayList<>();
                    for(ListHistoryImportProductHelperClass listHistoryImportProductHelperClass : oldListHistoryImportProductHelperClassArrayList ){
                        if(listHistoryImportProductHelperClass.getNameImportProduct().toLowerCase().contains(strSearch.toLowerCase())){
                            listSearch.add(listHistoryImportProductHelperClass);
                        }
                    }

                    listHistoryImportProductHelperClassArrayList = listSearch;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listHistoryImportProductHelperClassArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listHistoryImportProductHelperClassArrayList = (ArrayList<ListHistoryImportProductHelperClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
