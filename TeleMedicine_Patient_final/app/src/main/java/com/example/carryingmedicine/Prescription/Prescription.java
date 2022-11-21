package com.example.carryingmedicine.Prescription;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.carryingmedicine.Register.RegisterActivity;
import com.example.carryingmedicine.login.LoginActivity;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;


import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.Clinic.Clinic;
import com.example.carryingmedicine.Clinic.Clinic_List_Main;
import com.example.carryingmedicine.ClinicDetail.ApplyCancelRequest;
import com.example.carryingmedicine.ClinicDetail.ClinicDtail;
import com.example.carryingmedicine.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.carryingmedicine.ClinicDetail.ClinicDtail.Reservation_number;


public class Prescription extends AppCompatActivity {
    private ConstraintLayout container;
    private TextView  pe_date, pe_name, pe_birth, pe_hospital, pe_number, pe_doctor, pre_medicine1,pre_medicine2,pre_m1,pre_m2,pe_disease;
    private Clinic clinic;
    private Button captureButton;
    ClinicDtail CD = (ClinicDtail) ClinicDtail.Clinic_detail;
    private String userID, userName, doctorID, doctorName, reservation_date, doctorHospital_number,userBirth,doctorHospital,
            userDisease,medicineOne,medicineTwo,use_m_one,use_m_two;


    Response.Listener<String> responseListener;
    PrescriptionSelect prescriptionSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_activity);
        GetSelect();    // 디비에서 불러온 정보 저장
        GetINfo();        //기본 정보들 작성할 필요 없이 셋팅
        PreDelete();                    //예약 수정 및 삭제
        captureButton = (Button)findViewById(R.id.btn_prescription);
    }


    /********************처방전 디비에서 삭제****************************/
    public void PreDelete() {
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                container = (ConstraintLayout) findViewById(R.id.m_container);
                Bitmap bm = Bitmap.createBitmap(container.getWidth(), container.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bm);
                Drawable bgDrawable = container.getBackground();
                if (bgDrawable != null) {
                    bgDrawable.draw(canvas);
                } else {
                    canvas.drawColor(Color.WHITE);
                }
                container.draw(canvas);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());

                    Document document = new Document();
                    Object dirpath = Environment.getExternalStorageDirectory().toString();

                    PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/newPDF.pdf"));
                    document.open();

                    Image image = Image.getInstance(f.toString());
                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / image.getWidth()) * 100;
                    image.scalePercent(scaler);
                    image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
                    document.add(image);
                    document.close();
                    Toast.makeText(getApplicationContext(), "PDF 파일 저장성공", Toast.LENGTH_SHORT).show();

                    //f.delete();


                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.optBoolean("success");
                                if (success)
                                {
                                    Toast.makeText(getApplicationContext(), "처방전 삭제를 완료하였습니다.", Toast.LENGTH_SHORT).show();
/////////예약도 함께 삭제--------------------------------------------------------------(굳이 안에 있을 필요 없음)
                                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                boolean success = jsonObject.optBoolean("success");
                                                if (success)
                                                {
                                                    CD.finish();
                                                    finish();
                                                    Toast.makeText(getApplicationContext(), "예약 삭제를 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    ApplyCancelRequest applyCancelRequest = new ApplyCancelRequest(Reservation_number,responseListener);
                                    RequestQueue queue = Volley.newRequestQueue(Prescription.this);
                                    queue.add(applyCancelRequest);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    ///////처방전 삭제
                    PrescriptionCancelRequest prescriptionCancelRequest = new PrescriptionCancelRequest(responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Prescription.this);
                    queue.add(prescriptionCancelRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
    /*------------------------------------정보 불러오기------------*/
    public void GetSelect(){
        Intent intent = getIntent();
        //intent.putExtra("userID", userID); //비밀번호
        reservation_date = intent.getStringExtra("reservation_date");
        userID = intent.getStringExtra("userID");
        userName = intent.getStringExtra("userName");
        userBirth = intent.getStringExtra("userBirth");
        doctorID = intent.getStringExtra("doctorID");
        doctorHospital = intent.getStringExtra("doctorHospital");
        doctorHospital_number = intent.getStringExtra("doctorHospital_number");
        userDisease = intent.getStringExtra("userDisease");
        doctorName = intent.getStringExtra("doctorName");
        medicineOne = intent.getStringExtra("medicineOne");
        medicineTwo = intent.getStringExtra("medicineTwo");
        use_m_one = intent.getStringExtra("use_m_one");
        use_m_two = intent.getStringExtra("use_m_two");
    }

    public void GetINfo() {
        /*정보 셋팅*/

        captureButton = findViewById(R.id.btn_prescription);
        pe_date = findViewById(R.id.pe_date);//1
        pe_date.setText(reservation_date);
        pe_disease = findViewById(R.id.pe_disease);//2
        pe_disease.setText(userDisease);
        pe_name = findViewById(R.id.pe_name);//3
        pe_name.setText(userName);
        pe_birth = findViewById(R.id.pe_birth);//4
        pe_birth.setText(userBirth);
        pe_hospital = findViewById(R.id.pe_hospital);//5
        pe_hospital.setText(doctorHospital);
        pe_number = findViewById(R.id.pe_number);//6
        pe_number.setText(doctorHospital_number);
        pe_doctor = findViewById(R.id.pe_doctor);//7
        pe_doctor.setText(doctorName);
        pre_m1 = findViewById(R.id.pre_m1);//8
        pre_m1.setText(use_m_one);
        pre_m2 = findViewById(R.id.pre_m2);//9
        pre_m2.setText(use_m_two);
        pre_medicine1 = findViewById(R.id.pre_medicine1);//10
        pre_medicine1.setText(medicineOne);
        pre_medicine2 = findViewById(R.id.pre_medicine2);//11
        pre_medicine2.setText(medicineTwo);
    }

}