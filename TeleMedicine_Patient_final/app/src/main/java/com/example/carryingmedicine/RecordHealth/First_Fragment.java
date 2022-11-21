package com.example.carryingmedicine.RecordHealth;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.carryingmedicine.R;

public class First_Fragment extends Fragment {
    private static String username;
    private static String userid;


    private View view;
    private TextView kidney,first_name_text,weight;
    private static EditText kidney_insert,weight_insert;
    private Button first_next;

    private static First_Fragment ff;

    private String kidney_v,weight_v;


    public First_Fragment(){}

    public void user_N_ID(String name, String id)
    {
        this.username = name;
        this.userid = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.record_first, container,false);

        first_name_text = view.findViewById(R.id.change_text);

        kidney=view.findViewById(R.id.insert_text);
        kidney_insert=view.findViewById(R.id.edit_change);

        weight=view.findViewById(R.id.weight_text2);
        weight_insert=view.findViewById(R.id.weight_insert2);
        first_next = view.findViewById(R.id.btn_change);

        first_name_text.setText(this.username+"님 안녕하세요!");// 텍스트값에 표시

        first_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("kidney",kidney_insert.getText().toString());
                bundle.putString("weight",weight_insert.getText().toString());
                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                Second_Fragment second_fragment=new Second_Fragment();
                second_fragment.setArguments(bundle);
                transaction.replace(R.id.frameLayout, second_fragment).commit();
            }
        });




        return view;

    }


}