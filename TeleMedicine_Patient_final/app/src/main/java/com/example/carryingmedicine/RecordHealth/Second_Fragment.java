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


public class Second_Fragment extends Fragment {
    private View view;
    private static String username,userid;
    private TextView second_name_text,family_medical_history_text,past_medical_history_text,surgical_history_text;
    private TextView family_medical_history_ex,past_medical_history_ex,surgical_history_ex;
    public EditText family_medical_history_insert,past_medical_history_text_insert,surgical_history_text_insert;
    private Button second_next;



    String past_v,family_v,surgical_v;
    public String first_kidney2,first_weight2;

    public void user_N_ID(String name, String id)
    {
        this.username = name;
        this.userid = id;
    }
    public Second_Fragment(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.record_second, container,false);

        second_name_text=view.findViewById(R.id.second_name_text2); // 맨위 텍스트 뷰
        second_name_text.setText(this.username+"님");

        family_medical_history_text=view.findViewById(R.id.family_medical_history_text2);// 집안 병력
        past_medical_history_text=view.findViewById(R.id.past_medical_history_text2); // 과거 병력
        surgical_history_text=view.findViewById(R.id.surgical_history_text2);//수술경력

        //past_medical_history_ex=view.findViewById(R.id.past_medical_history_ex2);
        //family_medical_history_ex=view.findViewById(R.id.family_medical_history_ex2);
        //surgical_history_ex=view.findViewById(R.id.surgical_history_ex2);

        //입력창
        past_medical_history_text_insert=view.findViewById(R.id.past_medical_history_insert2);
        family_medical_history_insert=view.findViewById(R.id.family_medical_history_insert2);
        surgical_history_text_insert=view.findViewById(R.id.surgical_history_insert2);

        if(getArguments()!=null)
        {
            first_kidney2=getArguments().getString("kidney");
            first_weight2=getArguments().getString("weight");
        }

        second_next =view.findViewById(R.id.second_next);


        second_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("family",family_medical_history_insert.getText().toString());
                bundle.putString("past",past_medical_history_text_insert.getText().toString());
                bundle.putString("surgical",surgical_history_text_insert.getText().toString());
                bundle.putString("kidney_2",first_kidney2);
                bundle.putString("weight_2",first_weight2);

                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                Third_Fragment third_fragment=new Third_Fragment();
                third_fragment.setArguments(bundle);
                transaction.replace(R.id.frameLayout, third_fragment).commit();
            }
        });


        return view;
    }




}