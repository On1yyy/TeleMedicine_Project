package com.example.carryingmedicine.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    //서버 URL 설정(Php파일 연동)
    final static private String URL ="http://ghksdh587.dothome.co.kr/Login_doctor.php";
    private Map<String, String> map;

    public LoginRequest(String userID, String userPass,Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("doctorID",userID);
        map.put("doctorPass",userPass);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
