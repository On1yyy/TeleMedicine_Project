package com.example.carryingmedicine.RecordHealth;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.Main.MainActivity;
import com.example.carryingmedicine.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Third_Fragment extends Fragment {
    private TextView third_text,medicine_drugs_text,alcohol_text,smoke_text;
    private TextView medicine_drugs_ex;
    private EditText medicine_drugs_insert,text2;

    private RadioGroup alcohol_group,smoke_group;
    private RadioButton alcohol_no,alcohol_2_3,alcohol_5,nosmoke,half,smoke_many;

    private Button finish_but;
    private View view;
    public HealthRecord he;
    private static String smoke_result,alcohol_result;
    private static String username1,userid1;
    private static String test;
    public static String result;
    private static String transfer_past,transfer_family,transfer_surgical,transfer_kidney,transfer_weight; //세컨드에서 전해져온 값
    InfomationRequest infomationRequest;
    Record_check_Request record_check_request;
    Response.Listener<String> responseListener;
    int transfer_kidney_i;
    int transfer_weight_i;
    private String medicine_i;

    public Third_Fragment(){}

    public void user_N_ID(String aname, String aid)
    {
        this.username1 = aname;
        this.userid1 = aid;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.record_third, container, false);
        third_text=view.findViewById(R.id.third_name_text);
        third_text.setText(this.username1+"님의");

        medicine_drugs_text=view.findViewById(R.id.medicine_drugs_text2); //약물복용
        alcohol_text=view.findViewById(R.id.alcohol_text2);             // 알코올
        smoke_text=view.findViewById(R.id.smoke_text2);                // 흡연
        //medicine_drugs_ex=view.findViewById(R.id.medicine_drugs_ex2);
        medicine_drugs_insert=view.findViewById(R.id.medicine_drugs_insert2);

        alcohol_group = view.findViewById(R.id.alcohol_group);
        smoke_group=view.findViewById(R.id.smoke_group);


        alcohol_no =view.findViewById(R.id.alcohol_no);
        alcohol_2_3 =view.findViewById(R.id.alcohol_2_3);
        alcohol_5=view.findViewById(R.id.alcohol_5);
        nosmoke =view.findViewById(R.id.no_smoke);
        half=view.findViewById(R.id.half);
        smoke_many=view.findViewById(R.id.smoke_many);

        alcohol_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.alcohol_no)
                {
                    Toast.makeText(getActivity(),"마시지 않음 선택", Toast.LENGTH_SHORT).show();
                    alcohol_result=alcohol_no.getText().toString();
                }
                else if(checkedId==R.id.alcohol_2_3)
                {
                    Toast.makeText(getActivity()," 주 2~3회 선택 ", Toast.LENGTH_SHORT).show();
                    alcohol_result=alcohol_2_3.getText().toString();
                }
                else if(checkedId==R.id.alcohol_5)
                {
                    Toast.makeText(getActivity(),"주 5회 이상 선택", Toast.LENGTH_SHORT).show();
                    alcohol_result=alcohol_5.getText().toString();
                }
            }
        });

        smoke_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.no_smoke)
                {
                    Toast.makeText(getActivity(),"흡연하지 않음 선택 ", Toast.LENGTH_SHORT).show();
                    smoke_result = nosmoke.getText().toString();
                }
                else if(checkedId==R.id.half)
                {
                    Toast.makeText(getActivity()," 하루 반갑 선택 ", Toast.LENGTH_SHORT).show();
                    smoke_result = half.getText().toString();
                }
                else if(checkedId==R.id.smoke_many)
                {
                    Toast.makeText(getActivity(),"하루 한갑 이상 선택", Toast.LENGTH_SHORT).show();
                    smoke_result = smoke_many.getText().toString();
                }
            }
        });


        if(getArguments()!=null)
        {
            transfer_kidney=getArguments().getString("kidney_2").trim();
            transfer_weight=getArguments().getString("weight_2").trim();
            transfer_past=getArguments().getString("past");
            transfer_family=getArguments().getString("family");
            transfer_surgical=getArguments().getString("surgical");
        }


        finish_but=view.findViewById(R.id.finishbutton2); //완료 버튼

        finish_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // getActivity를 쓸것..
                transfer_kidney_i = Integer.parseInt(transfer_kidney);
                transfer_weight_i= Integer.parseInt(transfer_weight);
                medicine_i= medicine_drugs_insert.getText().toString();

                if( medicine_i.length()>0 &&smoke_result.length()>0&& alcohol_result.length()>0 ) {
                    responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {
                                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                    intent.putExtra("userID", userid1); //비밀번호
                                    intent.putExtra("userName", username1);
                                    startActivity(intent);
                                    getActivity().finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("log_tag", "parssing  error " + e.toString());
                            }
                        }

                    };//버튼눌렀을때

                    infomationRequest = new InfomationRequest(transfer_kidney_i,transfer_weight_i
                            , transfer_past, transfer_family, transfer_surgical,medicine_i
                            , alcohol_result, smoke_result, userid1,
                            responseListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(infomationRequest);

                    record_check_request = new Record_check_Request(userid1,1,responseListener);
                    //  RequestQueue queue2 = Volley.newRequestQueue(getActivity());
                    queue.add(record_check_request);

                }
                else
                {
                    Toast.makeText(getActivity(),"빈 공간을 입력하거나 선택하세요", Toast.LENGTH_SHORT).show();
                }
            }//이 위에 구문은 firstfragment와 연동하는것이다.

        });

        return view;
    }





}