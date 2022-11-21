package com.example.carryingmedicine.Doctorlist;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DoctorRequest extends StringRequest {
    //서버 URL 설정(Php파일 연동)
    final static private String URL = "http://ghksdh587.dothome.co.kr/Doctor_list.php";
    private Map<String, String> map;

    public DoctorRequest(Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
       // map.put("userName",name);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
