package com.example.projectandroid.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.projectandroid.HelperClasses.OnBoarding.OnBoardingItem;
import com.example.projectandroid.HelperClasses.OnBoarding.OnboardingAdapter;
import com.example.projectandroid.R;
import com.example.projectandroid.SessionManager;
import com.example.projectandroid.common.LoginSignUp.StartUpScreen;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OnBoarding extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnBoardingAction, skipBtn;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_on_boarding);

        sessionManager = new SessionManager(this);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnBoardingAction = findViewById(R.id.buttonOnboardingAction);
        skipBtn = findViewById(R.id.skipOnboardingAction);


        setupOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnBoardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onboardingViewPager.getCurrentItem() + 1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                }else{
                    startActivity(new Intent(getApplicationContext(), StartUpScreen.class));
                    finish();
                    sessionManager.setSecTime(true);
                }
            }
        });

        SkipOnBoarding();

    }

    private void SkipOnBoarding() {

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StartUpScreen.class));
                finish();
                sessionManager.setSecTime(true);
            }
        });

    }

    private void setupOnboardingItems(){

        List<OnBoardingItem> onBoardingItems = new ArrayList<>();

        OnBoardingItem first = new OnBoardingItem();
        first.setTitle("Quản Lý Sản Phẩm Của Bạn");
        first.setDescription("Ứng Dụng Giúp Bạn Quản Lý Bán Hàng Một Cách Tốt Hơn, Tránh Nhưng Sai Sót Trong Quá Trình Bán Hàng Của Bạn");
        first.setImage(R.drawable.on_boarding_1);

        OnBoardingItem second = new OnBoardingItem();
        second.setTitle("Tính Toán Tất Cả");
        second.setDescription("Ứng Dụng Giúp Bạn Tính Toán Hầu Như Tất Cả Những Gì Cần Tính Toán, Bạn Không Cần Phải Tính Toán Gì Nhiều");
        second.setImage(R.drawable.on_boarding_2);

        OnBoardingItem third = new OnBoardingItem();
        third.setTitle("Bảo Mật Thông Tin");
        third.setDescription("Thông Tin Sản Phẩm Và Thông Tin Cá Nhân Của Bạn Được Bảo Mật Chắc Chắn Với Các Phương Thức Như Xác Thực Qua Email, Bảo Mật Bằng Sinh Trắc Học");
        third.setImage(R.drawable.on_boarding_3);

        OnBoardingItem four = new OnBoardingItem();
        four.setTitle("Cuối Cùng");
        four.setDescription("Chúc Bạn Quản Lý Bán Hàng Thành Công Với Ứng Dụng Của Chúng Tôi. Nếu Có Bất Cứ Lỗi Nào Trong Quá Trình Sử Dụng, Vui Lòng Gửi Về Email : 22000330@lttc.edu.vn");
        four.setImage(R.drawable.on_boarding_4);

        onBoardingItems.add(first);
        onBoardingItems.add(second);
        onBoardingItems.add(third);
        onBoardingItems.add(four);

        onboardingAdapter = new OnboardingAdapter(onBoardingItems);

    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for(int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_active)
                );
            }else{
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_inactive)
                );
            }
        }
        if(index == onboardingAdapter.getItemCount() - 1){
            Animation Anim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            buttonOnBoardingAction.setText("Bắt Đầu");
            skipBtn.setVisibility(View.INVISIBLE);
            skipBtn.setAnimation(Anim);
        }else{
            buttonOnBoardingAction.setText("Tiếp Tục");
                skipBtn.setVisibility(View.VISIBLE);
        }
    }
}