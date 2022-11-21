package com.example.carryingmedicine.ClinicDetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.Prescription.Birth_check;
import com.example.carryingmedicine.Prescription.Prescription;
import com.example.carryingmedicine.R;

import org.json.JSONException;
import org.json.JSONObject;


public class PatientHealthActivity extends AppCompatActivity {

    private TextView personal_height, personal_weight, personal_past, personal_family, personal_surgical, personal_drug, personal_drink
            , personal_smoke, healthTextView;
    private Button personal_check;

    private String userID,userName;
    Response.Listener<String> responseListener;


    public static String userPast_history,userFamily_history,userSurgical_hisory,userDrug,userDrink,userSmoke;
    public static int userHeight,userWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patienthealth_activity);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");
        /***건강정보 띄우기*////////////////////////
        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success3 = jsonObject.optBoolean("success3");
                    if (success3)//로그인 성공시
                    {
                        //디비에서 정보를 불러와서 에디트텍스트에 삽입
                        userHeight = jsonObject.getInt("userHeight"); //대소문자 구별해라꼭
                        userWeight = jsonObject.getInt("userWeight"); // 뒤에 패스워드 적은건 php 이름그대로 가져와서 사용해야함
                        userPast_history = jsonObject.getString("userPast_history");//첫번째 로그인
                        userFamily_history = jsonObject.getString("userFamily_history");
                        userSurgical_hisory = jsonObject.getString("userSurgical_hisory");
                        userDrug = jsonObject.getString("userDrug");
                        userDrink = jsonObject.getString("userDrink");
                        userSmoke = jsonObject.getString("userSmoke");
                        //userID = jsonObject.getString("userID");
                        id_find_set();
                        prescription_Start();


                    } else {//실패시
                        Toast.makeText(getApplicationContext(), "환자건강기록 로딩에 실패하였습니다..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        PatientHealthRequest personalHealthRequest = new PatientHealthRequest(userID, responseListener);
        RequestQueue queue= Volley.newRequestQueue(PatientHealthActivity.this);
        queue.add(personalHealthRequest);


    }
    private void id_find_set() {

        healthTextView = findViewById(R.id.healthTextView);
        healthTextView.setText(userName+" 환자 건강정보");
        personal_height = findViewById(R.id.personal_height);
        personal_height.setText(userHeight+"");
        personal_weight = findViewById(R.id.personal_weight);
        personal_weight.setText(userWeight+"");
        personal_past = findViewById(R.id.personal_past);
        personal_past.setText(userPast_history);
        personal_family = findViewById(R.id.personal_family);
        personal_family.setText(userFamily_history);
        personal_surgical = findViewById(R.id.personal_surgical);
        personal_surgical.setText(userSurgical_hisory);
        personal_drug = findViewById(R.id.personal_drug);
        personal_drug.setText(userDrug);
        personal_drink = findViewById(R.id.personal_drink);
        personal_drink.setText(userDrink);
        personal_smoke = findViewById(R.id.personal_smoke);
        personal_smoke.setText(userSmoke);
    }
    //확인버튼
    private void prescription_Start()
    {
        personal_check=findViewById(R.id.personal_check);
        personal_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}