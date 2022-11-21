package com.example.carryingmedicine.Prescription;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.Clinic.Clinic;
import com.example.carryingmedicine.ClinicDetail.PatientHealthActivity;
import com.example.carryingmedicine.R;
import com.example.carryingmedicine.Register.RegisterActivity;
import com.example.carryingmedicine.Register.RegisterRequest;
import com.example.carryingmedicine.Register.ValidateRequest;
import com.example.carryingmedicine.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

//import static com.example.carryingmedicine.ClinicDetail.ClinicDtail.birth;
import static com.example.carryingmedicine.login.LoginActivity.doctorHospital;

public class Prescription extends AppCompatActivity {

    private TextView  pe_date, pe_name, pe_birth, pe_hospital, pe_number, pe_doctor;
    private EditText pre_medicine1, pre_medicine2, pre_m1, pre_m2, pe_disease;
    private Button btn_prescription;

    private String userID, userName, doctorID, doctorName, reservation_date,userBirth;


    Response.Listener<String> responseListener;
    PrescriptionInsert prescriptionInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_activity);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");
        doctorName = intent.getStringExtra("doctorName");
        doctorID = intent.getStringExtra("doctorID");
        reservation_date = intent.getStringExtra("reservation_date");
        userBirth = intent.getStringExtra("userBirth");

        GetINfo();        //기본 정보들 작성할 필요 없이 셋팅
        PreInsert();   //디비에 저장
    }


    /********************처방전 디비에 저장****************************/
    public void PreInsert() {


        btn_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pre_disease = pe_disease.getText().toString();
                String p_medicine1 = pre_medicine1.getText().toString();
                String p_m1 = pre_m1.getText().toString();
                String p_m2 = pre_m2.getText().toString();
                String p_medicine2 = pre_medicine2.getText().toString();
                responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(getApplicationContext(), "처방전 작성을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                finish();//뒤로 돌아가기기
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                PrescriptionInsert prescriptionInsert = new PrescriptionInsert(reservation_date, userID, userName, userBirth, doctorID, doctorHospital,
                        LoginActivity.doctorHospital_number, pre_disease, doctorName, p_medicine1, p_medicine2, p_m1, p_m2, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Prescription.this);
                queue.add(prescriptionInsert);
            }
        });


    }


    public void GetINfo() {
        /*정보 셋팅*/
        btn_prescription = findViewById(R.id.btn_prescription);
        pe_date = findViewById(R.id.pe_date);
        pe_date.setText(reservation_date);
        pe_name = findViewById(R.id.pe_name);
        pe_name.setText(userName);
        pe_birth = findViewById(R.id.pe_birth);
        pe_birth.setText(userBirth);
        pe_hospital = findViewById(R.id.pe_hospital);
        pe_hospital.setText(doctorHospital);
        pe_number = findViewById(R.id.pe_number);
        pe_number.setText(LoginActivity.doctorHospital_number);
        pe_doctor = findViewById(R.id.pe_doctor);
        pe_doctor.setText(doctorName);
        pe_disease = findViewById(R.id.pe_disease);
        pre_m1 = findViewById(R.id.pre_m1);
        pre_m2 = findViewById(R.id.pre_m2);
        pre_medicine1 = findViewById(R.id.pre_medicine1);
        pre_medicine2 = findViewById(R.id.pre_medicine2);
    }

}
