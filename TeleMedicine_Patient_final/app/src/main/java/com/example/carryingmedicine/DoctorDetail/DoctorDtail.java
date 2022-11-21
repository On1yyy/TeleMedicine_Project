package com.example.carryingmedicine.DoctorDetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carryingmedicine.Doctorlist.Doctor;
import com.example.carryingmedicine.R;
import com.example.carryingmedicine.Reservation.ReservationMain;

public class DoctorDtail extends AppCompatActivity {

    private TextView detail_dname, detail_birth,detail_hospitalname,dtail_Hadress,dtail_Hnumber,dtail_Major,detail_school;
    private ImageView detail_dimage;
    private Button detail_reservation;
    String userID,userName;
    private Doctor doctor;

    public static Activity Doctor_detail;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_detail_activity);
        Doctor_detail= DoctorDtail.this;

        Intent intent=getIntent();
        userID = intent.getStringExtra("userID");                     //로그인에서 유저네임 값을 받음
        userName = intent.getStringExtra("userName");

        doctor = (Doctor) intent.getSerializableExtra("selectDoctor");
        //System.out.println(doctor.()+userID+userName+"gdgdgdgdgdgdgdgddggd");
        id_find_set(); //각 값에 대임


        detail_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DoctorDtail.this, ReservationMain.class);
                intent1.putExtra("userID", userID); //비밀번호
                intent1.putExtra("doctorID", doctor.getId());
                intent1.putExtra("userName", userName);
                intent1.putExtra("doctorName", doctor.getName());
                startActivity(intent1);
            }
        });



    }
    private void id_find_set()
    {
        detail_dname = findViewById(R.id.detail_dname);
        detail_dname.setText(doctor.getName());

        detail_birth = findViewById(R.id.detail_birth);
        detail_birth.setText(doctor.getBirth());

        detail_hospitalname = findViewById(R.id.detail_hospitalname);
        detail_hospitalname.setText(doctor.getHospital_name());

        dtail_Hadress = findViewById(R.id.dtail_Hadress);
        dtail_Hadress.setText(doctor.getHospital_address());

        dtail_Hnumber = findViewById(R.id.dtail_Hnumber);
        dtail_Hnumber.setText(doctor.getHospital_number());

        dtail_Major = findViewById(R.id.dtail_major);
        dtail_Major.setText(doctor.getMajor());

        detail_dimage = findViewById(R.id.detail_dimage);
        detail_reservation = findViewById(R.id.detail_reservation);

        detail_school =findViewById(R.id.detail_school);
        detail_school.setText(doctor.getSchool());
        detail_reservation = findViewById(R.id.detail_reservation);
    }
}
