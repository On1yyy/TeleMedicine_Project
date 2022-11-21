package com.example.carryingmedicine.ClinicDetail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.AppRTC.ConnectActivity;
import com.example.carryingmedicine.Chat.ChatMainActivity;
import com.example.carryingmedicine.Clinic.Clinic;
import com.example.carryingmedicine.Clinic.Clinic_List_Main;
import com.example.carryingmedicine.Prescription.Birth_check;
import com.example.carryingmedicine.Prescription.Prescription;
import com.example.carryingmedicine.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.carryingmedicine.login.LoginActivity.doctorHospital_number;

public class ClinicDtail extends AppCompatActivity {

    private TextView res_number, res_patient, res_doctor, res_date, res_time, res_disease;
    private ImageView detail_dimage;
    private Button detail_reservation_but, cancel_reservation, btn_patinet;
    private String userID,userName,doctorID,doctorName,reservation_date,doctorHospital_number;
    private String userBirth;
    Response.Listener<String> responseListener;

    private Clinic clinic;
    Dialog dialog;
    public static Activity Clinic_detail;
    Clinic_List_Main CL = (Clinic_List_Main) Clinic_List_Main.Clinic_List_Main;
    ImageButton btn_video_call, btn_chatting, btn_prescription;

    //화면구성요소
    private TextView  res_pID, res_dID,now_time;

    ToggleButton test_but;
    //  ServiceThread serviceThread;
    //화면 종료를 위함

    // 값을 받기 위함
    public static String doctor_ID;
    public static int pre_num,Reservation_number;

    //시계설정과 버튼 온/오프를 위함
    Date nowTimeDate;
    public static String reservationDateString ;
    Date date1,reservationDate,date3;
    private static Handler mHandler ;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    Dialog dialog2;

    int flag = 0;
    int flag2 = 0;
    long df;


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_detail_activity);
        Clinic_detail = ClinicDtail.this; //피니쉬 하기위함

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");
        doctorName = intent.getStringExtra("doctorName");
        doctorID = intent.getStringExtra("doctorID");
        reservation_date = intent.getStringExtra("reservation_date");
        doctorHospital_number = intent.getStringExtra("doctorHospital_number");
        clinic = (Clinic) intent.getSerializableExtra("selectClinic");

        Reservation_number = Integer.parseInt(clinic.getReservation_number());
        reservationDateString = clinic.getReservation_date()+" "+clinic.getReservation_time();
        //test_but.setEnabled(false); //초기화면에서 버튼을 실행못하게 비활성화

        try { //실험용
            date3=sdf.parse("2020-11-25 02:40:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }



        id_find_set(); //아이디 찾기
        ing_time();
        reservation_but_control();
        Catting_Start(); // 채팅 시작 버튼
        apply_cancel(); // 취소버튼 클릭시
        Video_Start(); // 영상통화 버튼
        SelectBirth(); //생년월일 가져오기
        prescription_Start(); //처방전 시작버튼
        patient_Start();//환자 상세정보


    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ처방전 시작버튼   ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void prescription_Start()
    {
        btn_prescription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1) {
                    Intent intent = new Intent(ClinicDtail.this, Prescription.class);
                    intent.putExtra("doctorID", clinic.getDoctorID());
                    intent.putExtra("doctorName", clinic.getDoctorName());
                    intent.putExtra("userID", clinic.getUserID());
                    intent.putExtra("userName", clinic.getUserName());
                    intent.putExtra("reservation_date", clinic.getReservation_date());
                    intent.putExtra("doctorHospital_number", doctorHospital_number);
                    intent.putExtra("userBirth", userBirth);
                    startActivity(intent);
                }
                else if(flag ==2 )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClinicDtail.this);
                    dialog2 = builder.setMessage("예약시간이 아닙니다 시간을 확인해주세요."+"\n"+"고객님의 예약시간 :" +reservationDateString)
                            .setNegativeButton("OK", null)
                            .create();
                    dialog2.show();
                }
                else if(flag ==3 )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClinicDtail.this);
                    dialog2 = builder.setMessage("예약시간이 지났습니다 예약취소를 해주세요.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog2.show();
                }
            }
        });

    }
    /**********************환자 상세정보 -----------------------------------------*/
    private void patient_Start()
    {
        btn_patinet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClinicDtail.this, PatientHealthActivity.class);
                intent.putExtra("userID", clinic.getUserID());
                intent.putExtra("userName", clinic.getUserName());
                startActivity(intent);
            }
        });

    }

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ채팅 시작 버튼  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void Catting_Start() {
        //채팅

        btn_chatting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1)
                {
                    Intent intent = new Intent(ClinicDtail.this, ChatMainActivity.class);
                    intent.putExtra("reservation_number", clinic.getReservation_number());
                    startActivity(intent);
                }
                else if(flag ==2 )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClinicDtail.this);
                    dialog2 = builder.setMessage("예약시간이 아닙니다 시간을 확인해주세요."+"\n\n"+"고객님의 예약시간 :" +reservationDateString)
                            .setNegativeButton("OK", null)
                            .create();
                    dialog2.show();
                }
                else if(flag ==3 )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ClinicDtail.this);
                    dialog2 = builder.setMessage("예약시간이 지났습니다 예약취소를 해주세요.")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog2.show();
                }
            }
        });
    }


    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ영상통화 시작 버튼  ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void Video_Start() {
        btn_video_call.setOnClickListener(new OnClickListener() {
            @Override

                public void onClick(View v) {
                    if(flag==1)
                    {
                        Intent intent = new Intent(ClinicDtail.this, ConnectActivity.class);
                        intent.putExtra("reservation_number", clinic.getReservation_number());
                        startActivity(intent);
                    }
                    else if(flag ==2 )
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ClinicDtail.this);
                        dialog2 = builder.setMessage("예약시간이 아닙니다 시간을 확인해주세요."+"\n"+"고객님의 예약시간 :" +reservationDateString)
                                .setNegativeButton("OK", null)
                                .create();
                        dialog2.show();
                    }
                    else if(flag ==3 )
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ClinicDtail.this);
                        dialog2 = builder.setMessage("예약시간이 지났습니다 예약취소를 해주세요.")
                                .setNegativeButton("OK", null)
                                .create();
                        dialog2.show();
                    }

                }
        });
    }
    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ버튼 제어 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void reservation_but_control()
    {
        now_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    String now_time_str = String.valueOf(editable); //현재 시간을 String으로 변경
                    nowTimeDate = sdf.parse(now_time_str); //현재시간을 Date에 넣음
                    reservationDate= sdf.parse(reservationDateString);  //예약시간을 Date로 넣음
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long duration = date3.getTime() - nowTimeDate.getTime(); //date3에 예약시간 넣기// 두시간값들의 차이값을 구함
                df= duration/1000 ;    //차이값의 second(초) 구하기
                // serviceThread.set_time(df);
                if(df<0) //만약 차이가나는 초가 0초일때, 즉 예약된시간이 되었을때
                {
                    if(df <(-1800))           //30분뒤에는 못하게 설정
                    { flag=3; }
                    else
                    { flag=1; }
                }
                else
                { flag=2; }


                //   System.out.println(nowTimeDate);
                // System.out.println(date3);
                System.out.println(df+"aa");
            }

        });
    }
    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ예약 취소버튼 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void apply_cancel()
    {

        //예약취소
        cancel_reservation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ClinicDtail.this);  //Dialog창 띄우기 위함
                dialog=builder.setMessage("해당 예약을 정말 취소하시겠습니까?").
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.optBoolean("success");
                                            if (success)
                                            {
                                                CL.finish();
                                                finish();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                int Reservation_number = Integer.parseInt(clinic.getReservation_number());
                                ApplyCancelRequest applyCancelRequest = new ApplyCancelRequest(Reservation_number,responseListener);
                                RequestQueue queue = Volley.newRequestQueue(ClinicDtail.this);
                                queue.add(applyCancelRequest);
                            }
                        }).setNegativeButton("NO", null) // 아니오버튼을 누를 시
                        .create();
                dialog.show();
            }
        });
    }

    private void ing_time()
    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                long now = System.currentTimeMillis();
                Date dateNow = new Date(now);
                String strTime = sdf.format(dateNow);
                now_time.setText(strTime) ;
            }
        } ;

        Runnable run = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
            //}
        };
        Thread t = new Thread(run);
        t.start();
    }

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ아이디 찾기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void id_find_set() {
        res_number = findViewById(R.id.res_number);
        res_number.setText(clinic.getReservation_number());
        res_patient = findViewById(R.id.res_patient);
        res_patient.setText(clinic.getUserName());
        res_doctor = findViewById(R.id.res_doctor);
        res_doctor.setText(clinic.getDoctorName());
        res_time = findViewById(R.id.res_time);
        res_time.setText(clinic.getReservation_time());
        res_date = findViewById(R.id.res_date);
        res_date.setText(clinic.getReservation_date());
        res_disease = findViewById(R.id.res_disease);
        res_disease.setText(clinic.getNow_disease());
        cancel_reservation = findViewById(R.id.cancel_reservation);

        btn_video_call = findViewById(R.id.btn_video_call);
        btn_chatting = findViewById(R.id.btn_chatting);
        btn_prescription = findViewById(R.id.btn_prescription);
        btn_patinet = findViewById(R.id.detail_patient);

        now_time = findViewById(R.id.now_time);
        detail_reservation_but=findViewById(R.id.detail_reservation_but);



    }
    public void SelectBirth() {
        /*유저 생년월일 가져오기(건강정보에 없음)*/
        responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        userBirth = jsonObject.getString("userBirth");
                        String userName = jsonObject.getString("userName");
                        String birth = userBirth;
                        Toast.makeText(getApplicationContext(), "생년월일 가져오기 성공\n"+userBirth, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "닉네임  : ."+userName, Toast.LENGTH_SHORT).show();


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
        Birth_check birth_check = new Birth_check(clinic.getUserID(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ClinicDtail.this);
        queue.add(birth_check);
    }
}



