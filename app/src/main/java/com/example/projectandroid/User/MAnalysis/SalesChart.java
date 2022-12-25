package com.example.projectandroid.User.MAnalysis;

import static com.example.projectandroid.User.DashBoard.idUser;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.example.projectandroid.HelperClasses.SqlLite.SqlDatabaseHelper;
import com.example.projectandroid.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SalesChart extends AppCompatActivity {

    BarChart barChart_sales;

    ArrayList<String> itemYear;
    AutoCompleteTextView SelectYear;
    ArrayAdapter adapterItemYear;

    ImageView backBtn;

    SqlDatabaseHelper db;

    ArrayList<BarEntry> barEntryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_chart);

        db = new SqlDatabaseHelper(this);

        barChart_sales = findViewById(R.id.bart_char_sales);
        SelectYear = findViewById(R.id.sales_select_year);
        backBtn = findViewById(R.id.back_btn);

        SelectYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setData();
                barChar();
            }
        });

        barChart_sales.setNoDataText("Vui Lòng Chọn Năm \nĐể Hiển Thị Dữ Liệu");
        barChart_sales.setNoDataTextColor(Color.parseColor("#FF9800"));

        fillSelectYear();
        backBtn();
    }

    private void backBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalesChart.super.onBackPressed();
            }
        });

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
            float gTotal = 0;
            for (int i = Integer.parseInt(sYear); i<= Integer.parseInt(eYear); i++){

                Integer Date = i;

                Cursor cursor = db.readDataBarSales_Chart(Integer.valueOf(idUser), Date);
                if(cursor.getCount() == 0){
                    barEntryArrayList.add(new BarEntry(i, 0));
                }else{
                    while(cursor.moveToNext()){
                        String strSplit[] = cursor.getString(0).split("/");
                        gYear = strSplit[2];
                        String gPrice = cursor.getString(1);
                        gTotal += Float.parseFloat(gPrice);
                    }
                    barEntryArrayList.add(new BarEntry(Integer.parseInt(gYear), gTotal));
                }
            }
        }
    }

    private void barChar() {

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList,"Tổng Tiền Bán Được (VNĐ) x 1000");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(14f);

        BarData barData = new BarData(barDataSet);
        barChart_sales.setData(barData);

        barChart_sales.setDrawBorders(true);
        barChart_sales.setBorderColor(Color.parseColor("#FF9800"));
        barChart_sales.getDescription().setText("Doanh Số");
        barChart_sales.getDescription().setTextSize(15);
        barChart_sales.getDescription().setTextColor(Color.parseColor("#FF9800"));
        barChart_sales.animateY(2000);
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
}