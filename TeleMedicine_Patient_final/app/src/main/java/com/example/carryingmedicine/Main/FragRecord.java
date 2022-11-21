package com.example.carryingmedicine.Main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.Chat.ChatMainActivity;
import com.example.carryingmedicine.Clinic.ClinicData;
import com.example.carryingmedicine.Clinic.Clinic_List_Main;
import com.example.carryingmedicine.Clinic.ReservationRequest;
import com.example.carryingmedicine.News.Item;
import com.example.carryingmedicine.News.MyAdapter;
import com.example.carryingmedicine.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Nullable;


public class FragRecord extends Fragment{
    private View view;
    private TextView tv_id, tv_pass,et_name, name_sibal,time;
    private BottomNavigationView bottomNavigationView;//바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    //private ImageButton btn_call;
    private ImageButton btn_chat;
    private ImageButton btn_init;
    private ImageButton btn_health;
    private ImageButton btn_prescribe;
    private FragRecord fragHome;
    public static String userID,doctorID,doctorName,now_disease,reservation_date,reservation_number,reservation_time,userName;
    ArrayList<ClinicData> mArrayList=new ArrayList<ClinicData>();
    Response.Listener<String> responseListener;
    private Button btn_clinic;
    private static Handler mHandler ;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);

    //리사이클뷰
    RecyclerView recyclerView;
    ArrayList<Item> items= new ArrayList<>();
    MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_record,container,false);
        btn_clinic = view.findViewById(R.id.btn_clinic);
        time= view.findViewById(R.id.time);
        name_sibal = view.findViewById(R.id.name_sibal);
        name_sibal.setText(MainActivity.userName+"님");
        ///예약현황 보기 및 진료
        mArrayList.clear();
        ing_time();
        StartClinic();///////////////진료시작하기
        return view;
    }
    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ진료하기(Clinic)ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    public void StartClinic(){
        btn_clinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), Clinic_List_Main.class);
                responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("test");
                            mArrayList.clear();

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject item = jsonArray.getJSONObject(i);
                                reservation_number = item.getString("reservation_number");
                                userName = item.getString("userName");
                                doctorName = item.getString("doctorName");
                                userID = item.getString("userID");
                                doctorID = item.getString("doctorID");
                                reservation_date = item.getString("reservation_date");
                                reservation_time = item.getString("reservation_time");
                                now_disease = item.getString("now_disease");
                                mArrayList.add(new ClinicData(reservation_number,userName,doctorName,userID,doctorID,reservation_date,reservation_time,now_disease));
                            }

                            intent1.putExtra("userID", userID);                           // 아이디값
                            intent1.putExtra("userName", userName);
                            intent1.putParcelableArrayListExtra("ArrayList", mArrayList); //어레이리스트 객체의 값을 넘김
                            startActivity(intent1);
                        } catch (
                                Exception e) {
                            e.printStackTrace();
                        }
                    }

                };
                ReservationRequest reservationRequest = new ReservationRequest(responseListener);
                RequestQueue queue= Volley.newRequestQueue(getActivity());
                queue.add(reservationRequest);
            }
        });
    }

    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ시간출력ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    private void ing_time()
    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                long now = System.currentTimeMillis();
                Date dateNow = new Date(now);
                String strTime = sdf.format(dateNow);
                time.setText(strTime) ;
            }
        } ;

        Runnable run = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(0);
                }
            }
            //}
        };
        Thread t = new Thread(run);
        t.start();
    }
}
