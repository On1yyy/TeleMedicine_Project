package com.example.carryingmedicine.Register;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.R;
import com.example.carryingmedicine.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static com.example.carryingmedicine.login.LoginActivity.doctorID;
//확인3

public class RegisterActivity extends AppCompatActivity {
    private EditText et_id,et_pass,et_name,et_date, et_license, et_hospital, et_hNumber, et_school, et_address, et_class;
    private Button et_ok,but_calender,id_check;
    RegisterCondition registerCondition =new RegisterCondition(this);  //맨위에 선언해주기..

    Dialog dialog;
    private AlertDialog aDialog; //메시지박스 띄우기위한 다이얼로그
    private boolean id_validate = false;
    int first_login = 0;
    Response.Listener<String> responseListener;
    RegisterRequest registerRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        et_id=findViewById(R.id.et_id);
        et_pass=findViewById(R.id.et_pass);
        et_name=findViewById(R.id.et_name);
        et_license = findViewById(R.id.et_license);
        et_date = findViewById(R.id.et_date);
        et_hospital = findViewById(R.id.et_hospital);
        et_hNumber = findViewById(R.id.et_hNumber);
        et_school = findViewById(R.id.et_school);
        et_address = findViewById(R.id.et_address);
        et_class = findViewById(R.id.et_class);
        et_date.setEnabled(false);

        et_ok = findViewById(R.id.et_ok); // 확인 버튼
        but_calender= findViewById(R.id.but_calender); //날짜 버튼
        id_check=findViewById(R.id.id_check);

        but_calender.setOnClickListener(new View.OnClickListener() { //캘린더 설정
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                Calendar maxData= Calendar.getInstance();
                int y = now.get(Calendar.YEAR);
                int m = now.get(Calendar.MONTH);
                int d = now.get(Calendar.DAY_OF_MONTH);

                dialog = new DatePickerDialog(RegisterActivity.this,dateSetListener,y,m,d);  // date 셋리스너는 맨밑에 정의되어있음.
                maxData.set(y,m,d);
                ((DatePickerDialog) dialog).getDatePicker().setMaxDate(maxData.getTimeInMillis()); // 미래 날짜 선택불가
                dialog.show();


            }
        });

        //아디 체크 버튼 클릭시 수행
        id_check.setOnClickListener(new View.OnClickListener() {
            String userID = et_id.getText().toString();
            @Override
            public void onClick(View v) {
                userID = et_id.getText().toString();
                if (userID.length() > 0) {
                        if (id_validate) {
                            return;
                        }
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    aDialog = builder.setMessage("공백입니다.")
                            .setNegativeButton("OK", null)
                            .create();
                    aDialog.show();
                    return;

                }


                if(registerCondition.user_id_check(userID)) {
                    responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    aDialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                            .setPositiveButton("OK", null)
                                            .create();
                                    aDialog.show();
                                    et_id.setEnabled(false);//아이디값을 바꿀 수 없도록 함
                                    id_validate = true;//검증완료
                                    id_check.setBackgroundColor(getResources().getColor(R.color.colorGray));
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    aDialog = builder.setMessage("이미 사용하고 있는 아이디 입니다.")
                                            .setNegativeButton("OK", null)
                                            .create();
                                    aDialog.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(validateRequest);

                }
            }



            });


        //회원가입 버튼 클릭 시 수행
        et_ok.setOnClickListener(new View.OnClickListener(){ //버튼눌렀을때
            @Override
            public void onClick(View view) {
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                int userLicense = Integer.parseInt(et_license.getText().toString());
                String userBirth = et_date.getText().toString();

                String userSchool = et_school.getText().toString();
                String userHospital = et_hospital.getText().toString();
                String userHospital_number = et_hNumber.getText().toString();
                String userHospital_address = et_address.getText().toString();
                String userClass = et_class.getText().toString();

                //ID 중복체크를 했는지 확인함
                if(!id_validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("중복체크를 진행해주세요")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                    return;
                }

               //EditText에 현재 입력되어 있는 값을 가져온다
                if( userID.length()>0&&userPass.length()>0 &&userName.length()>0&&userBirth.length()>0&&userHospital.length()>0&&userClass.length()>0&&userSchool.length()>0&&userHospital_address.length()>0) {
                    if(registerCondition.user_pass_check(userPass, userID)) //비밀번호 형식 체크
                    {
                        if(registerCondition.user_name_check(userName)){
                        responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) {
                                        Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class); // 화면전화?
                                        startActivity(intent);
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
                        registerRequest = new RegisterRequest(userID, userPass, userName,userLicense, userBirth,userSchool,userHospital,userHospital_number,userHospital_address,userClass,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);
                        }
                    }
                }

                else {//실패시
                    Toast.makeText(getApplicationContext(), "빈칸을 등록해 주십시오 ", Toast.LENGTH_SHORT).show();
                    return;
                }

            }


        });
    }

    DatePickerDialog.OnDateSetListener dateSetListener =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String starDate = String.format("%d-%02d-%02d",year, month+1, dayOfMonth);
            et_date.setText((starDate));
        }
    };

  
}