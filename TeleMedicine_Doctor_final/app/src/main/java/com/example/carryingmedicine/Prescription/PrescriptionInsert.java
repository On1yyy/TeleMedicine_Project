package com.example.carryingmedicine.Prescription;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PrescriptionInsert extends StringRequest {
    //서버 URL 설정(Php파일 연동)
    final static private String URL = "http://ghksdh587.dothome.co.kr/Prescription.php";
    private Map<String, String> map;
    public PrescriptionInsert(String reservation_date, String userID, String userName, String userBirth,
                              String doctorID, String doctorHospital, String doctorHospital_number, String userDisease,
                              String doctorName, String medicineOne, String medicineTwo, String use_m_one, String use_m_two, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("reservation_date",reservation_date);
        map.put("userID",userID);
        map.put("userName",userName);
        map.put("userBirth",userBirth);
        map.put("doctorID",doctorID);
        map.put("doctorHospital",doctorHospital);
        map.put("doctorHospital_number",doctorHospital_number);
        map.put("userDisease",userDisease);
        map.put("doctorName",doctorName);
        map.put("medicineOne",medicineOne);
        map.put("medicineTwo",medicineTwo);
        map.put("use_m_one",use_m_one);
        map.put("use_m_two",use_m_two);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
