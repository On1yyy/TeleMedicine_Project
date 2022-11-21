package com.example.carryingmedicine.Prescription;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.carryingmedicine.ClinicDetail.ClinicDtail;

import java.util.HashMap;
import java.util.Map;

public class PrescriptionSelect extends StringRequest {
    //서버 URL 설정(Php파일 연동)
    final static private String URL = "http://ghksdh587.dothome.co.kr/prescription_select.php";
    private Map<String, String> map;
    public PrescriptionSelect(String userID,  Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userID",userID);
        map.put("doctorID", ClinicDtail.doctor_ID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
