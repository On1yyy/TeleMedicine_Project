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
import android.widget.CompoundButton;
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
import com.example.carryingmedicine.Prescription.Prescription;
import com.example.carryingmedicine.Prescription.PrescriptionSelect;
import com.example.carryingmedicine.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClinicDtail extends AppCompatActivity {

    //화면구성요소
    private TextView res_number, res_patient, res_doctor, res_date, res_time, res_disease,now_time;
    private ImageView detail_dimage;
    private Button detail_reservation_but, cancel_reservation;
    Dialog dialog;
    ImageButton btn_video_call, btn_chatting, btn_prescription;
    ToggleButton test_but;
    //  ServiceThread serviceThread;
    //화면 종료를 위함
    public static Activity Clinic_detail;
    Clinic_List_Main CL = (Clinic_List_Main) Clinic_List_Main.Clinic_List_Main;

    // 값을 받기 위함
    String userID, userName;
    private Clinic clinic;
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

    Response.Listener<String> responseListener;







    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_detail_activity);
        Clinic_detail = ClinicDtail.this; //피니쉬 하기위함

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");                     //로그인에서 유저네임 값을 받음
        userName = intent.getStringExtra("userName");
        clinic = (Clinic) intent.getSerializableExtra("selectClinic");
        doctor_ID = clinic.getDoctorID();
        Reservation_number = Integer.parseInt(clinic.getReservation_number());
        reservationDateString = clinic.getReservation_date()+" "+clinic.getReservation_time();
        //test_but.setEnabled(false); //초기화면에서 버튼을 실행못하게 비활성화

        try { //실험용
            date3=sdf.parse("2020-11-25 02:37:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        id_find_set(); //아이디 찾기

        reservation_but_control(); //버튼제어
        Catting_Start(); // 채팅 시작 버튼
        apply_cancel(); // 취소버튼 클릭시
        Video_Start(); // 영상통화 버튼
        prescription_Start(); //처방전 시작버튼
        ing_time();  //시간출력
        ToggleButton_test(); //알림버튼

        //확인버튼
        detail_reservation_but.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ처방전 시작버튼   ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void prescription_Start()
    {
        btn_prescription.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1)
                {
                    responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    int pre_num= jsonObject.getInt("pre_num");
                                    String reservation_date = jsonObject.getString("reservation_date");
                                    String userID = jsonObject.getString("userID");
                                    String userName = jsonObject.getString("userName");
                                    String userBirth = jsonObject.getString("userBirth");
                                    String doctorID = jsonObject.getString("doctorID");
                                    String doctorHospital = jsonObject.getString("doctorHospital");
                                    String doctorHospital_number = jsonObject.getString("doctorHospital_number");
                                    String userDisease = jsonObject.getString("userDisease");
                                    String doctorName = jsonObject.getString("doctorName");
                                    String medicineOne = jsonObject.getString("medicineOne");
                                    String medicineTwo = jsonObject.getString("medicineTwo");
                                    String use_m_one = jsonObject.getString("use_m_one");
                                    String use_m_two = jsonObject.getString("use_m_two");
                                    Intent intent = new Intent(ClinicDtail.this, Prescription.class);

                                    intent.putExtra("pre_num", pre_num);
                                    intent.putExtra("reservation_date", reservation_date);
                                    intent.putExtra("userID", userID);
                                    intent.putExtra("userName", userName);
                                    intent.putExtra("userBirth", userBirth);
                                    intent.putExtra("doctorID", doctorID);
                                    intent.putExtra("doctorHospital", doctorHospital);
                                    intent.putExtra("doctorHospital_number", doctorHospital_number);
                                    intent.putExtra("userDisease", userDisease);
                                    intent.putExtra("doctorName", doctorName);
                                    intent.putExtra("medicineOne", medicineOne);
                                    intent.putExtra("medicineTwo", medicineTwo);
                                    intent.putExtra("use_m_one", use_m_one);
                                    intent.putExtra("use_m_two", use_m_two);

                                    startActivity(intent);
                                }
                                else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ClinicDtail.this);
                                    dialog2 = builder.setMessage("주치의가 처방전을 아직 작성하지 않았습니다. 다시 시도해 주세요")
                                            .setNegativeButton("OK", null)
                                            .create();
                                    dialog2.show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };
                    PrescriptionSelect prescriptionSelect = new PrescriptionSelect(userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ClinicDtail.this);
                    queue.add(prescriptionSelect);
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


    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ예약시간에 따른 버튼 제어ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
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

                if(flag2==1 && df == 30)
                {
                    Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClinicDtail.this,AlarmService.class);
                    startService(intent);
                }


                //   System.out.println(nowTimeDate);
                // System.out.println(date3);
                System.out.println(df+"aa");
            }

        });
    }


    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ시간출력ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
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


    /*알림버튼*/
    private void ToggleButton_test()
    {
        test_but.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    flag2=1;
                    Toast.makeText(getApplicationContext(),"Service 시작",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClinicDtail.this, AlarmForeService.class);
                    startService(intent);
                    // startForegroundService(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Service 끔",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ClinicDtail.this,AlarmForeService.class);
                    stopService(intent);
                }
            }
        });
    }

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ아이디 찾기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void id_find_set() {


        res_number = findViewById(R.id.res_number);
        res_number.setText(clinic.getReservation_number());
        res_patient = findViewById(R.id.res_patient);
        res_patient.setText(clinic.getUserName());
        res_doctor = findViewById(R.id.res_doctor);
        res_doctor.setText(clinic.getDoctorName());
        //res_pID = findViewById(R.id.res_pID);
        //res_pID.setText(clinic.getUserID());
        //res_dID = findViewById(R.id.res_dID);
        //res_dID.setText(clinic.getDoctorID());
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
        now_time = findViewById(R.id.now_time);
        test_but = findViewById(R.id.test_but);
        detail_reservation_but=findViewById(R.id.detail_reservation_but);

    }

}



