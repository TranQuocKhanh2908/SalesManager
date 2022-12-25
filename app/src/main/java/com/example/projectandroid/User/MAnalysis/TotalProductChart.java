package com.example.projectandroid.User.MAnalysis;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.projectandroid.User.DashBoard.idUser;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TotalProductChart extends AppCompatActivity {

    BarChart BarChart_quality;

    ArrayList<String> itemYear;
    AutoCompleteTextView SelectYear;
    ArrayAdapter adapterItemYear;

    ArrayList<String> itemTypeProduct;
    AutoCompleteTextView SelectTypeProduct;
    ArrayAdapter adapterItemTypeProduct;

    ImageView backBtn;

    SqlDatabaseHelper db;

    String idTypeProduct;

    ArrayList<BarEntry> barEntryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_product_chart);

        db = new SqlDatabaseHelper(this);

        SelectYear = findViewById(R.id.quality_select_year);
        SelectTypeProduct = findViewById(R.id.quality_typeProduct_select);
        backBtn = findViewById(R.id.back_btn);
        BarChart_quality = findViewById(R.id.Bart_char_quality);

        SelectTypeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SelectYear.getText().toString().isEmpty()){
                    Toast.makeText(TotalProductChart.this, "Vui Lòng Chọn Năm Trước", Toast.LENGTH_SHORT).show();
                    SelectYear.requestFocus();
                }else{
                    SelectTypeProduct.requestFocus();
                }
            }
        });

        SelectTypeProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = db.getTypeProductID_Chart(SelectTypeProduct.getText().toString(), Integer.valueOf(idUser));
                if(cursor.getCount() == 0){

                }else{
                    while (cursor.moveToNext()){
                        idTypeProduct = cursor.getString(0);
                    }
                }
                setData();
                barChar();
            }
        });

        SelectYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SelectTypeProduct.requestFocus();
            }
        });

        BarChart_quality.setNoDataText("Vui Lòng Chọn Năm Và Loại Sản Phẩm\nĐể Hiển Thị Dữ Liệu");
        BarChart_quality.setNoDataTextColor(Color.parseColor("#FF9800"));

        fillSelectYear();
        fillSelectTypeProduct();
        backBtn();
    }

    private void barChar() {

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"Số Lượng Hàng Bán Được");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(14f);

        BarData barData = new BarData(barDataSet);
        BarChart_quality.setData(barData);


        BarChart_quality.setDrawBorders(true);
        BarChart_quality.setBorderColor(Color.parseColor("#FF9800"));
        BarChart_quality.getDescription().setText("Số Lượng");
        BarChart_quality.getDescription().setTextSize(15);
        BarChart_quality.getDescription().setTextColor(Color.parseColor("#FF9800"));
        BarChart_quality.animateY(2000);
    }

    private void setData() {

        barEntryArrayList = new ArrayList<>();

        String gDate = SelectYear.getText().toString();
        if(gDate.isEmpty()){

        }else{
            String strSplitGYear[] = gDate.split("-");
            String sYear = strSplitGYear[0];
            String eYear = strSplitGYear[1];
            String gYear = null;
            Integer gTotal = 0;
            for (int i = Integer.parseInt(sYear); i<= Integer.parseInt(eYear); i++){

                Integer Date = i;

                Cursor cursor = db.readDataBarQuality_Chart(Integer.valueOf(idUser), Date, idTypeProduct);
                if(cursor.getCount() == 0){
                    barEntryArrayList.add(new BarEntry(i, 0));
                }else{
                    while(cursor.moveToNext()){
                        String strSplit[] = cursor.getString(0).split("/");
                        gYear = strSplit[2];
                        String gQuality = cursor.getString(1);
                        gTotal += Integer.parseInt(gQuality);
                    }
                    barEntryArrayList.add(new BarEntry(Integer.parseInt(gYear), gTotal));
                }
            }
        }
    }

    private void fillSelectTypeProduct() {

        itemTypeProduct = new ArrayList<>();
        Cursor cursor = db.readTypeProductQuality_Chart(Integer.valueOf(idUser));
        if(cursor.getCount() == 0){

        }else{
            while (cursor.moveToNext()){

                itemTypeProduct.add(cursor.getString(0));

            }
            adapterItemTypeProduct = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu,itemTypeProduct);
            SelectTypeProduct.setAdapter(adapterItemTypeProduct);
        }

    }

    private void fillSelectYear() {

        Date dateAndTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        String gCDay = dateFormat.format(dateAndTime);
        Integer CDay = Integer.parseInt(gCDay);

        itemYear = new ArrayList<>();
        for (int i = 2014; i<=CDay; i++){
            i = i + 4;
            String secYear = String.valueOf(i + 4);
            itemYear.add(i + "-" + secYear);
        }

        adapterItemYear = new ArrayAdapter<String>(this, R.layout.list_item_dropmenu,itemYear);
        SelectYear.setAdapter(adapterItemYear);

    }

    private void backBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TotalProductChart.super.onBackPressed();
            }
        });

    }

}