package com.example.carryingmedicine.Main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.example.carryingmedicine.R;


import static com.example.carryingmedicine.login.LoginActivity.doctorBirth;
import static com.example.carryingmedicine.login.LoginActivity.doctorClass;
import static com.example.carryingmedicine.login.LoginActivity.doctorHospital;
import static com.example.carryingmedicine.login.LoginActivity.doctorHospital_addres;
import static com.example.carryingmedicine.login.LoginActivity.doctorHospital_number;
import static com.example.carryingmedicine.login.LoginActivity.doctorID;
import static com.example.carryingmedicine.login.LoginActivity.doctorLicense;
import static com.example.carryingmedicine.login.LoginActivity.doctorName;
import static com.example.carryingmedicine.login.LoginActivity.doctorSchool;

public class PersonalActivity extends AppCompatActivity {

    private TextView personalID, personalName, personalBirth, personalLicense, personalHospital, hospitalNumber, hospitalAddress, personalClass, personalSchool;
    private Button checkButton;

    Response.Listener<String> responseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity);
///////*개인정보 띄우기*/////////////
        SetPersonalInfo();
////////*확인 완료
        checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    public void SetPersonalInfo(){
        personalID = findViewById(R.id.personalID);
        personalName = findViewById(R.id.personalName);
        personalBirth = findViewById(R.id.personalBirth);
        personalLicense = findViewById(R.id.personalLicense);
        personalHospital = findViewById(R.id.personalHospital);
        hospitalNumber = findViewById(R.id.hospitalNumber);
        hospitalAddress = findViewById(R.id.hospitalAddress);
        personalClass = findViewById(R.id.personalClass);
        personalSchool = findViewById(R.id.personalSchool);

        personalID.setText(doctorID);
        personalName.setText(doctorName);
        personalBirth.setText(doctorBirth);
        personalLicense.setText(doctorLicense+"");
        personalHospital.setText(doctorHospital);
        hospitalNumber.setText(doctorHospital_number);
        hospitalAddress.setText(doctorHospital_addres);
        personalClass.setText(doctorClass);
        personalSchool.setText(doctorSchool);

    }

}