package com.example.carryingmedicine.Register;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    //서버 URL 설정(Php파일 연동)
    final static private String URL = "http://ghksdh587.dothome.co.kr/Register_doctor.php";
    private Map<String, String> map;

    public RegisterRequest(String userID, String userPassword, String userName,int doctorLicense, String userBirth, String doctorSchool,
                           String doctorHospital,String doctorHospital_num, String doctorHospital_address, String doctorClass ,Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("doctorID",userID);
        map.put("doctorPass",userPassword);
        map.put("doctorName",userName);
        map.put("doctorLicense",doctorLicense+"");
        map.put("doctorBirth",userBirth);
        map.put("doctorSchool",doctorSchool);
        map.put("doctorHospital",doctorHospital);
        map.put("doctorHospital_number",doctorHospital_num);
        map.put("doctorHospital_address",doctorHospital_address);
        map.put("doctorClass",doctorClass);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
