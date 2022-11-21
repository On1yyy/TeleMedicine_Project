package com.example.carryingmedicine.Main;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PersonalHealthRequest extends StringRequest {
    //서버 URL 설정(Php파일 연동)
    final static private String URL ="http://ghksdh587.dothome.co.kr/Information.php";
    private Map<String, String> map;

    public PersonalHealthRequest(String userID, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userID",userID);
    }

    @Override
    protected Map getParams() throws AuthFailureError {
        return map;
    }
}
