package com.example.projectandroid.HelperClasses.Product.ListTypeProduct;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.example.projectandroid.User.MProduct.ListProduct.DetailProduct;
import com.example.projectandroid.User.MProduct.ListTypeProduct.UpdateTypeProduct;

import java.util.ArrayList;
import java.util.List;

public class ListTypeProductAdapter extends RecyclerView.Adapter<ListTypeProductAdapter.ListTypeProductViewHolder> implements Filterable {

    private Context context;
    ArrayList<ListTypeProductHelperClass> listTypeProductHelperClassArrayList;
    ArrayList<ListTypeProductHelperClass> oldListTypeProductHelperClassArrayList;
    int singleData;
    SQLiteDatabase SQLdb;

    public ListTypeProductAdapter(Context context, int singleData, ArrayList<ListTypeProductHelperClass> listTypeProductHelperClassArrayList, SQLiteDatabase SQLdb) {
        this.context = context;
        this.singleData = singleData;
        this.listTypeProductHelperClassArrayList = listTypeProductHelperClassArrayList;
        this.oldListTypeProductHelperClassArrayList = listTypeProductHelperClassArrayList;
        this.SQLdb = SQLdb;
    }

    @NonNull
    @Override
    public ListTypeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_type_product_card_desgin, null,false);
        return new ListTypeProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTypeProductAdapter.ListTypeProductViewHolder holder, int position) {

        final ListTypeProductHelperClass listTypeProductHelperClass = listTypeProductHelperClassArrayList.get(position);
        holder.TextNTypeProduct.setText(listTypeProductHelperClass.getNameTypeProduct());
        holder.TextDTypeProduct.setText(listTypeProductHelperClass.getDescTypeProduct());
        holder.configBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer typeProduct_ID = listTypeProductHelperClass.getIdTypeProduct();
                Intent intent = new Intent(context, UpdateTypeProduct.class);
                intent.putExtra("id_typeProduct", typeProduct_ID);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listTypeProductHelperClassArrayList.size();
    }

    public class ListTypeProductViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout configBtn;
        TextView TextNTypeProduct,TextDTypeProduct;

        public ListTypeProductViewHolder(@NonNull View itemView){
            super(itemView);

            configBtn = itemView.findViewById(R.id.config_btn_type_product);
            TextNTypeProduct = itemView.findViewById(R.id.Txv_TypeNameProduct);
            TextDTypeProduct = itemView.findViewById(R.id.Txv_DescTypeProduct);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    listTypeProductHelperClassArrayList = oldListTypeProductHelperClassArrayList;
                }else{

                    ArrayList<ListTypeProductHelperClass> listSearch = new ArrayList<>();
                    for(ListTypeProductHelperClass listTypeProductHelperClass : oldListTypeProductHelperClassArrayList ){
                        if(listTypeProductHelperClass.getNameTypeProduct().toLowerCase().contains(strSearch.toLowerCase())){
                            listSearch.add(listTypeProductHelperClass);
                        }
                    }

                    listTypeProductHelperClassArrayList = listSearch;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listTypeProductHelperClassArrayList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listTypeProductHelperClassArrayList = (ArrayList<ListTypeProductHelperClass>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
