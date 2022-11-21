package com.example.carryingmedicine.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.ClinicDetail.AlarmForeService;
import com.example.carryingmedicine.Main.MainActivity;
import com.example.carryingmedicine.R;
import com.example.carryingmedicine.RecordHealth.First_Fragment;
import com.example.carryingmedicine.RecordHealth.HealthRecord;
import com.example.carryingmedicine.Register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText log_id,log_pass;
    private Button btn_register,btn_Login;
    private ImageButton Al_can;

    First_Fragment myFragment;
    Response.Listener<String> responseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        log_id=findViewById(R.id.log_id);
        log_pass=findViewById(R.id.log_pass);
        btn_Login = findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.doctor_info_but);
        Al_can= findViewById(R.id.Al_can);
        Al_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Service 끔",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, AlarmForeService.class);
                stopService(intent);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID=log_id.getText().toString();
                String userPass=log_pass.getText().toString();
                //Toast.makeText(getApplicationContext(), "아이디잘받아오네"+userID, Toast.LENGTH_SHORT).show();


                responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.optBoolean("success");
                            if(success)//로그인 성공시
                            {
                                String userID = jsonObject.getString("userID"); //대소문자 구별해라꼭
                                String userPass = jsonObject.getString("userPassword"); // 뒤에 패스워드 적은건 php 이름그대로 가져와서 사용해야함
                                int first_login = jsonObject.getInt("first_login");//첫번째 로그인
                                String userName= jsonObject.getString("userName");
                                String userBirth = jsonObject.getString("userBirth");



                                if(first_login==0) {   // 최초가입시 건강정보 입력
                                    Intent intent = new Intent(LoginActivity.this, HealthRecord.class); // 화면전화?
                                    intent.putExtra("userName", userName); // 이름 값
                                    intent.putExtra("userID",userID); // 아이디값
                                    startActivity(intent);
                                }
                                else  //최초가입이 아닐 시 바로 메인으로 진입
                                {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class); // 화면전화?
                                    Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    intent.putExtra("userID", userID);
                                    intent.putExtra("userPassword", userPass); // 이름 값
                                    intent.putExtra("userName", userName); // 이름 값
                                    intent.putExtra("userBirth", userBirth);

                                    startActivity(intent);
                                }

                            }
                            else{//실패시
                                Toast.makeText(getApplicationContext(),"로그인에 실패하였습니다..",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };


                LoginRequest loginRequest = new LoginRequest(userID,userPass,responseListener);
                RequestQueue queue= Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

    }

}