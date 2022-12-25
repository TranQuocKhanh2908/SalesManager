package com.example.projectandroid;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class ProgessLoading extends Dialog {

    public ProgessLoading(@NonNull Context context){
        super(context);

        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_loading_dialog,null);
        setContentView(view);

    }
}
