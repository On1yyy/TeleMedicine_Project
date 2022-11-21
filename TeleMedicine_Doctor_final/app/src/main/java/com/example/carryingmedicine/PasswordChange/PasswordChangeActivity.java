package com.example.carryingmedicine.PasswordChange;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.R;

import com.example.carryingmedicine.login.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.carryingmedicine.login.LoginActivity.doctorID;


public class PasswordChangeActivity extends AppCompatActivity {

    private EditText edit_change, edit_change2;
    private TextView insert_text, insert_text2;
    private Button btn_change, btn_change2;
    Password_Request password_request;
    private String userPass2;
    private String userPass;

    Response.Listener<String> responseListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change_activity);
        edit_change = findViewById(R.id.edit_change);
        insert_text = findViewById(R.id.insert_text);

        //비밀번호 확인
        CheckPassword();//비밀 번호 확인 수행

    }
///------비밀번호 확인-----------
    public void CheckPassword(){
        btn_change = findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "아이디잘받아오네"+userID, Toast.LENGTH_SHORT).show();
                userPass = edit_change.getText().toString();
                responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.optBoolean("success");
                            if (success)//확인 성공시
                            {
                                DoVisible();// ----- 비밀번호 변경화면 표시
                                DoChange(); // ------- 비밀번호 변경

                            } else {//실패시
                                Toast.makeText(getApplicationContext(), "일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                LoginRequest loginRequest = new LoginRequest(doctorID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PasswordChangeActivity.this);
                queue.add(loginRequest);
            }
        });
    }
    ////실행시 비밀번호 변경전 안보이게, 비밀번호 변경화면 출력
    public void DoVisible(){
        Toast.makeText(getApplicationContext(), "비밀번호가 일치합니다!", Toast.LENGTH_SHORT).show();
        btn_change2 = findViewById(R.id.btn_change2);
        insert_text2 = findViewById(R.id.insert_text2);
        edit_change2 = findViewById(R.id.edit_change2);
        //비밀번호 변경하기 기능
        insert_text2.setVisibility(TextView.VISIBLE);
        edit_change2.setVisibility(EditText.VISIBLE);
        btn_change2.setVisibility(Button.VISIBLE);
        //비밀번호 변경전 전부 안보이게
        btn_change.setVisibility(Button.INVISIBLE);
        insert_text.setVisibility(TextView.INVISIBLE);
        edit_change.setVisibility(EditText.INVISIBLE);
    }
///////////////////*비밀번호 변경 수행***********/
    public void DoChange(){
        btn_change2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) { // getActivity를 쓸것..
                userPass2 = edit_change2.getText().toString();
                responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success2 = jsonObject.getBoolean("success2");
                            if (success2) {//변경시 이전화면으로
                                //Toast.makeText(getApplicationContext(), userPass2, Toast.LENGTH_SHORT).show();
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("log_tag", "parssing  error " + e.toString());
                        }
                    }
                };//버튼눌렀을때
                password_request = new Password_Request(doctorID, userPass2, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PasswordChangeActivity.this);
                queue.add(password_request);

            }
        });
    }
}
