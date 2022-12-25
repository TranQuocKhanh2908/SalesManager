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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GrowthChart extends AppCompatActivity {

    LineChart lineChart_Growth;

    ArrayList<String> itemYear;
    AutoCompleteTextView SelectYear;
    ArrayAdapter adapterItemYear;

    ImageView backBtn;

    SqlDatabaseHelper db;

    ArrayList<Entry> entryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_growth_chart);

        db = new SqlDatabaseHelper(this);

        lineChart_Growth = findViewById(R.id.line_char_growth);
        SelectYear = findViewById(R.id.growth_select_year);
        backBtn = findViewById(R.id.back_btn);

        SelectYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setData();
                barChar();
            }
        });

        fillSelectYear();
        backBtn();

        lineChart_Growth.setNoDataText("Vui Lòng Chọn Năm \nĐể Hiển Thị Dữ Liệu");
        lineChart_Growth.setNoDataTextColor(Color.parseColor("#FF9800"));
    }

    private void backBtn() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GrowthChart.super.onBackPressed();
            }
        });

    }

    private void setData() {

        entryArrayList = new ArrayList<>();

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

                Cursor cursor = db.readDataLineGrowth_Chart(Integer.valueOf(idUser), Date);
                if(cursor.getCount() == 0){
                    entryArrayList.add(new BarEntry(i, 0));
                }else{
                    while(cursor.moveToNext()){
                        String strSplit[] = cursor.getString(0).split("/");
                        gYear = strSplit[2];
                        String gPrice = cursor.getString(1);
                        gTotal += Float.parseFloat(gPrice);
                    }
                    entryArrayList.add(new BarEntry(Integer.parseInt(gYear), gTotal));
                }
            }
        }
    }

    private void barChar() {

        LineDataSet lineDataSet = new LineDataSet(entryArrayList,"Mức Tăng Trưởng Qua Từng Năm");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(15f);

        LineData lineData = new LineData(lineDataSet);
        lineChart_Growth.setData(lineData);

        lineChart_Growth.setDrawBorders(true);
        lineChart_Growth.setBorderColor(Color.parseColor("#FF9800"));
        lineChart_Growth.getDescription().setText("Biểu Đồ Tăng Trưởng");
        lineChart_Growth.getDescription().setTextSize(15);
        lineChart_Growth.getDescription().setTextColor(Color.parseColor("#FF9800"));
        lineChart_Growth.animateY(2000);
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