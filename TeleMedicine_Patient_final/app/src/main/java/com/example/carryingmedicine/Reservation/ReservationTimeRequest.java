package com.example.carryingmedicine.Reservation;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReservationTimeRequest extends StringRequest {
    //서버 URL 설정(Php파일 연동)
    final static private String URL ="http://ghksdh587.dothome.co.kr/Reservation_time_get.php";
    private Map<String, String> map;

    public ReservationTimeRequest(String doctorID, String reservation_date, String userID, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("doctorID",doctorID);
        map.put("reservation_date",reservation_date);
        map.put("userID",userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
