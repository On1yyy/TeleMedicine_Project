package com.example.carryingmedicine.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carryingmedicine.News.Item;
import com.example.carryingmedicine.News.MyAdapter;
import com.example.carryingmedicine.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tv_id, tv_pass,et_name;
    private BottomNavigationView bottomNavigationView;//바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragHome fragHome;
    private FragRecord fragRecord;
    private FragInformation fragInformation;
    public static String userID,userName,userBirth,userPass;
    public MainActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Intent intent = getIntent(); // 로그인에서 넘겨온값을 get으로 받음
        userName = intent.getStringExtra("userName"); //로그인에서 유저네임 값을 받음
        userID = intent.getStringExtra("userID"); //로그인에서 유저네임 값을 받음
        userBirth = intent.getStringExtra("userBirth");
        userPass = intent.getStringExtra("userPass");
/// 바텀 네비게이션 뷰
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_record:
                        setFrag(1);
                        break;
                    case R.id.action_information:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });
        fragHome = new FragHome();
        fragHome.user_ID(userName,userID);
        fragInformation = new FragInformation();
        fragRecord = new FragRecord();
        setFrag(0);
    }
//바텀네비게이션##########################################################################################################
    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n)
        {
            case 0:
                ft.replace(R.id.Main_Frame,fragHome);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.Main_Frame,fragRecord);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.Main_Frame,fragInformation);
                ft.commit();
                break;
        }
    }

}

