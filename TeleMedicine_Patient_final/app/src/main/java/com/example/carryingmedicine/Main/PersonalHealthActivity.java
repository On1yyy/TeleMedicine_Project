package com.example.carryingmedicine.Main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.example.carryingmedicine.R;
import com.example.carryingmedicine.RecordHealth.First_Fragment;

import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



public class PersonalHealthActivity extends AppCompatActivity {

    private EditText personal_height, personal_weight, personal_past, personal_family, personal_surgical, personal_drug, personal_drink
            , personal_smoke;
    private Button personal_check;
    InformationUpdate informationUpdate;


    First_Fragment myFragment;
    Response.Listener<String> responseListener;

    public static String userPast_history,userFamily_history,userSurgical_hisory,userDrug,userDrink,userSmoke;
    public static int userHeight,userWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalhealth_activity);

        String userID = MainActivity.userID;





        //Toast.makeText(getApplicationContext(), "아이디잘받아오네"+userID, Toast.LENGTH_SHORT).show();

        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    boolean success3 = jsonObject.optBoolean("success3");
                    //Toast.makeText(getApplicationContext(), "여기 들어옴~!!", Toast.LENGTH_SHORT).show();
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
                        String userID = jsonObject.getString("userID");


                        /*Intent intent = new Intent(PersonalHealthActivity.this, PersonalHealthActivity.class); // 화면전화?
                        intent.putExtra("userDrink", userDrink); // 이름 값
                        startActivity(intent);*/

                        //Toast.makeText(getApplicationContext(), userDrink, Toast.LENGTH_SHORT).show();

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
                        personal_check=findViewById(R.id.personal_check);

                        personal_check.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v) {
                                personal_check=findViewById(R.id.personal_check);
                                personal_check.setOnClickListener(new View.OnClickListener()
                                {
                                    public void onClick(View v) {
                                        //Intent intent = new Intent(PersonalHealthActivity.this, PersonalUpdate.class);
                                        //startActivity(intent);
                                        responseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    boolean success3 = jsonObject.getBoolean("success3");
                                                    if (success3) {
                                                        Toast.makeText(getApplicationContext(), "변경/확인을 완료하였습니다!", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                                        return;
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                // 서버로 volley를 이용해서 요청을 함
                                            }
                                        };



                                        informationUpdate = new InformationUpdate(Integer.parseInt(personal_height.getText().toString()), Integer.parseInt(personal_weight.getText().toString())                                                , personal_past.getText().toString(), personal_family.getText().toString(), personal_surgical.getText().toString(), personal_drug.getText().toString()
                                                , personal_drink.getText().toString(), personal_smoke.getText().toString(),
                                                responseListener);
                                        RequestQueue queue= Volley.newRequestQueue(PersonalHealthActivity.this);
                                        queue.add(informationUpdate);


                                    }
                                });
                            }
                        });




                    } else {//실패시
                        Toast.makeText(getApplicationContext(), "건강기록 로딩에 실패하였습니다..", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    //Toast.makeText(getApplicationContext(), "여기 들어옴~!!", Toast.LENGTH_SHORT).show();
                }

            }
        };

        PersonalHealthRequest personalHealthRequest = new PersonalHealthRequest(userID, responseListener);
        RequestQueue queue= Volley.newRequestQueue(PersonalHealthActivity.this);
        queue.add(personalHealthRequest);

    }

}