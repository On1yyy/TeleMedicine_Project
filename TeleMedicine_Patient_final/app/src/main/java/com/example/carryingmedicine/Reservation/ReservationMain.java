package com.example.carryingmedicine.Reservation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.DoctorDetail.DoctorDtail;
import com.example.carryingmedicine.Doctorlist.Doctor_List_Main;
import com.example.carryingmedicine.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class ReservationMain extends AppCompatActivity {

    private TextView re_title, re_now_title, re_time_title;
    private EditText re_now_insert, re_calendar_text;
    private RadioGroup re_time_group;
    private RadioButton t_9, t_9_3, t_10, t_10_3, t_11, t_11_3, t_12, t_12_3,
                        t_13, t_13_3, t_14, t_14_3, t_15, t_15_3, t_16, t_16_3;
    private Button re_check_but, re_apply_but, re_calendar_but;

    String userID, userName, doctorID, doctorName, radio_result, starDate;
    Dialog dialog;
    Response.Listener<String> responseListener;
    ArrayList<String> mArrayList = new ArrayList<String>();
    DoctorDtail DA = (DoctorDtail) DoctorDtail.Doctor_detail;
    Doctor_List_Main DL = (Doctor_List_Main) Doctor_List_Main.Doctor_list_main;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_activity);
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");
        doctorID = intent.getStringExtra("doctorID");
        doctorName = intent.getStringExtra("doctorName");
       // System.out.println(userName + userID + doctorID + doctorName + "dkdkdkfflflflfldkfldfkdlfd");
        Id_find();  //모든 아이디 찾기
        re_apply_but.setEnabled(false); //초기화면에서 신청하기버튼 비활성화
        calendar_set(); //캘린더 설정
        time_check(); //시간 체크
        radio_set(); //시간 설정
        reservation_apply(); //신청하기

    }



    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 캘린더 버튼 처리 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void calendar_set()
    {
        re_calendar_but.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                re_apply_but.setEnabled(false);     //신청하기 버튼을 비활성화

                Calendar now = Calendar.getInstance();
                Calendar maxData = Calendar.getInstance();
                Calendar minData = Calendar.getInstance();
                int y = now.get(Calendar.YEAR);
                int m = now.get(Calendar.MONTH);
                int d = now.get(Calendar.DAY_OF_MONTH);
                dialog = new DatePickerDialog(ReservationMain.this, dateSetListener, y, m, d);  // date 셋리스너는 맨밑에 정의되어있음.
                minData.set(y, m, d+1); //오늘을 기준으로 내일부터 예약가능, 오늘을 기준으로 과거는 모두 선택 불가
                maxData.set(y,m,d+14); //2주후 까지 예약을 제한
                ((DatePickerDialog) dialog).getDatePicker().setMinDate(minData.getTimeInMillis()); // 과거 날짜 선택불가
                ((DatePickerDialog) dialog).getDatePicker().setMaxDate(maxData.getTimeInMillis()); // 현재날짜로부터 2주후 까지 선택가능
                dialog.show();
            }

        });
    }

    DatePickerDialog.OnDateSetListener dateSetListener =new DatePickerDialog.OnDateSetListener() { //ok버튼을 눌렀을때
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            starDate = String.format("%d-%02d-%02d",year, month+1, dayOfMonth); //선택한 날짜를 String형으로 받음
            Calendar check = Calendar.getInstance();
            check.set(year,month,dayOfMonth); //선택된 날짜값으로, 주말인지 아닌지 체크하기위해 캘린더를 선언하고 값을 설정

            if(check.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY||check.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY){
                //선택된 날짜값이 토요일이나, 일요일이면 그 날짜를 선택못하게 제한
                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationMain.this);
                dialog = builder.setMessage("선택 날짜는 주말입니다. 평일을 선택해주세요")
                        .setNegativeButton("OK", null)
                        .create();
                dialog.show();
            }
            else { //만약 주말이 아닌 평일이이라면
                re_calendar_text.setText(starDate); //해당날짜를 텍스트로 표시하고
                for(int i = 0; i <16 ;i++) {        // 모든 라디오버튼을 활성화로 변환
                    re_time_group.getChildAt(i).setEnabled(true);
                }

            }
        }
    };

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ시간확인 버튼처리ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ */
    private void time_check()
    {

            re_check_but.setOnClickListener(new View.OnClickListener() {
                @Override
                    public void onClick(View view) {
                    if (re_calendar_text.length() > 0) {     //시간체크 시 날짜를 선택하였을때만 다음을 실행
                        responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                re_apply_but.setEnabled(true);                 //신청하기버튼 활성화
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("reser_t"); //해당 의사와 해당 날짜의 시간값을 받음
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject item = jsonArray.getJSONObject(i);
                                    String reservation_time = item.getString("reservation_time");
                                    radio_but_enable(reservation_time);            //받아온 이미 예약되어있는 해당 시간을 비활성화
                                    System.out.println(reservation_time);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest(doctorID, starDate,userID, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ReservationMain.this);
                    queue.add(reservationTimeRequest);
                } else //날짜를 선택하지 않았다면 날짜가 선택되지 않았다고 문구를 띄움
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ReservationMain.this);
                        dialog = builder.setMessage("예약할 날짜가 선택되지 않았습니다.")
                                .setNegativeButton("OK", null)
                                .create();
                        dialog.show();
                    }
            }
        });


    }
    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ신청하기 버튼을 누르는 클릭 이벤트 처리ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void reservation_apply()
    {
        re_apply_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radio_result.length()>0) {                           //예약 시간값이 비었는지 확인, 안비었다면

                    String c_t = re_calendar_text.getText().toString(); // 예약 날짜값 String으로 변환
                    String de=re_now_insert.getText().toString();       // 앓는질환 String으로 변환

                    AlertDialog.Builder builder = new AlertDialog.Builder(ReservationMain.this);  //Dialog창 띄우기 위함
                    dialog = builder.setMessage("선택하신 날짜는 : " + c_t + "\n" + //Dialog 메시지 값
                            "선택하신 시간은 : "+radio_result+"\n"
                            +" 예약자 : " +userName+"\n"+
                            "담당 의사 :" +doctorName+"" +
                            "\n"+" 예약하시겠습니까?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() { //확인버튼 클릭시
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                boolean success = jsonObject.getBoolean("success");
                                                if (success)     //정상적으로 삽입 쿼리가 실행되면 success를 받고 메인으로 화면전환
                                                {
                                                    Toast.makeText(getApplicationContext(), "예약되었습니다.", Toast.LENGTH_SHORT).show();
                                                    DA.finish();
                                                    DL.finish();
                                                    finish(); //메인창으로 돌아가기
                                                }
                                            }
                                            catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    ReservationApplyRequest reservationApplyRequest
                                            = new ReservationApplyRequest(userName, doctorName, userID,doctorID,c_t,
                                            radio_result,de,responseListener2);
                                    RequestQueue queue = Volley.newRequestQueue(ReservationMain.this);
                                    queue.add(reservationApplyRequest);
                                }
                            })
                            .setNegativeButton("NO", null) // 아니오버튼을 누를 시
                            .create();
                    dialog.show();
                }
                else  //예약 시간값이 비었는지 확인하여 비었다면 선택하라고 다이얼로그를 띄움
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReservationMain.this);
                    dialog = builder.setMessage("예약 시간을 선택해주세요")
                            .setNegativeButton("OK", null)
                            .create();
                    dialog.show();
                }
            }
        });


    }


    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ이미 예약된 시간값들의 라디오버튼을 비활성화ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void radio_but_enable(String time)
    {
        switch(time)
        {
            case "09:00:00" : re_time_group.getChildAt(0).setEnabled(false); break;
            case "09:30:00" : re_time_group.getChildAt(1).setEnabled(false);break;
            case "10:00:00" : re_time_group.getChildAt(2).setEnabled(false);break;
            case "10:30:00" : re_time_group.getChildAt(3).setEnabled(false);break;
            case "11:00:00" : re_time_group.getChildAt(4).setEnabled(false);break;
            case "11:30:00" : re_time_group.getChildAt(5).setEnabled(false);break;
            case "12:00:00" : re_time_group.getChildAt(6).setEnabled(false);break;
            case "12:30:00" : re_time_group.getChildAt(7).setEnabled(false);break;
            case "13:00:00" : re_time_group.getChildAt(8).setEnabled(false);break;
            case "13:30:00" : re_time_group.getChildAt(9).setEnabled(false);break;
            case "14:00:00" : re_time_group.getChildAt(10).setEnabled(false);break;
            case "14:30:00" : re_time_group.getChildAt(11).setEnabled(false);break;
            case "15:00:00" : re_time_group.getChildAt(12).setEnabled(false);break;
            case "15:30:00" : re_time_group.getChildAt(13).setEnabled(false);break;
            case "16:00:00" : re_time_group.getChildAt(14).setEnabled(false);break;
            case "16:30:00" : re_time_group.getChildAt(15).setEnabled(false);break;
            default: break;
        }
    }
    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ라디오버튼 선택 시 시간값을 저장ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void radio_set()
    {
        re_time_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId== R.id.t_9) { radio_result="9:00:00";}
                else if(checkedId== R.id.t_9_3) { radio_result="9:30:00"; }
                else if(checkedId== R.id.t_10) { radio_result="10:00:00"; }
                else if(checkedId== R.id.t_10_3) { radio_result="10:30:00"; }
                else if(checkedId== R.id.t_11) { radio_result="11:00:00"; }
                else if(checkedId== R.id.t_11_3) { radio_result="11:30:00"; }
                else if(checkedId== R.id.t_12) { radio_result="12:00:00"; }
                else if(checkedId== R.id.t_12_3) { radio_result="12:30:00"; }
                else if(checkedId== R.id.t_13) { radio_result="13:00:00"; }
                else if(checkedId== R.id.t_13_3) { radio_result="13:30:00"; }
                else if(checkedId== R.id.t_14) { radio_result="14:00:00"; }
                else if(checkedId== R.id.t_14_3) { radio_result="14:30:00"; }
                else if(checkedId== R.id.t_15) { radio_result="15:00:00"; }
                else if(checkedId== R.id.t_15_3) { radio_result="15:30:00"; }
                else if(checkedId== R.id.t_16) { radio_result="16:00:00"; }
                else if(checkedId== R.id.t_16_3) { radio_result="16:30:00"; }

            }
        });
    }

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ모든 아이디 값 찾기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void Id_find()
    {
        //TextView
        re_title =findViewById(R.id.re_title);
        re_now_title =findViewById(R.id.re_now_title);
        re_time_title =findViewById(R.id.re_time_title);
        //EditText
        re_now_insert =findViewById(R.id.re_now_insert);
        re_calendar_text =findViewById(R.id.re_calendar_text);
        //Radio Group
        re_time_group =findViewById(R.id.re_time_group);
        //Radio Button
        t_9 =findViewById(R.id.t_9);t_9_3 =findViewById(R.id.t_9_3);
        t_10 =findViewById(R.id.t_10);t_10_3 =findViewById(R.id.t_10_3);
        t_11 =findViewById(R.id.t_11);t_11_3 =findViewById(R.id.t_11_3);
        t_12 =findViewById(R.id.t_12);t_12_3 =findViewById(R.id.t_12_3);
        t_13 =findViewById(R.id.t_13);t_13_3 =findViewById(R.id.t_13_3);
        t_14 =findViewById(R.id.t_14);t_14_3 =findViewById(R.id.t_14_3);
        t_15 =findViewById(R.id.t_15);t_15_3 =findViewById(R.id.t_15_3);
        t_16 =findViewById(R.id.t_16);t_16_3 =findViewById(R.id.t_16_3);
        //boutton
        re_calendar_but=findViewById(R.id.re_calendar_but);
        re_check_but =findViewById(R.id.re_check_but);
        re_apply_but =findViewById(R.id.re_apply_but);
        re_calendar_text.setEnabled(false);
    }

}