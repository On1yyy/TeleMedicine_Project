package com.example.carryingmedicine.Reservation;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ReservationApplyRequest extends StringRequest {
    //서버 URL 설정(Php파일 연동)
    final static private String URL ="http://ghksdh587.dothome.co.kr/Reservation_apply.php";
    private Map<String, String> map;

    public ReservationApplyRequest(String userName, String doctorName,
                                   String userID,String doctorID,
                                   String reservation_date,String reservation_time,
                                   String now_disease, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        map = new HashMap<>();
        map.put("userName",userName);
        map.put("doctorName",doctorName);
        map.put("userID",userID);
        map.put("doctorID",doctorID);
        map.put("reservation_date",reservation_date);
        map.put("reservation_time",reservation_time);
        map.put("now_disease",now_disease);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
