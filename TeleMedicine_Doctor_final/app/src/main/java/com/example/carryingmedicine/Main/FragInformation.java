package com.example.carryingmedicine.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carryingmedicine.News.Item;
import com.example.carryingmedicine.News.MyAdapter;
import com.example.carryingmedicine.PasswordChange.PasswordChangeActivity;
import com.example.carryingmedicine.R;
import com.example.carryingmedicine.login.LoginActivity;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class FragInformation extends Fragment{
    private View view;
    public static String userID;
    public static String userName;
    private TextView text_name;


    //리사이클뷰
    RecyclerView recyclerView;
    ArrayList<Item> items= new ArrayList<>();
    MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_information,container,false);
        text_name = view.findViewById(R.id.text_name);
        text_name.setText(LoginActivity.doctorName);


////이미지버튼 넘겨주기

        // 개인정보
        Button btn_init = view.findViewById(R.id.btn_init);
        btn_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                startActivity(intent);
            }
        });
        //비밀번호 변경
        Button btn_passChange = view.findViewById(R.id.btn_passChange);
        btn_passChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PasswordChangeActivity.class);
                startActivity(intent);
            }
        });
        //로그아웃
        Button btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        //건강정보
        Button btn_health = view.findViewById(R.id.btn_health);
        btn_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
