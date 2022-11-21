package com.example.carryingmedicine.Main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.AppRTC.ConnectActivity;
import com.example.carryingmedicine.Chat.ChatMainActivity;
import com.example.carryingmedicine.Clinic.ClinicData;
import com.example.carryingmedicine.Clinic.Clinic_List_Main;
import com.example.carryingmedicine.Clinic.ReservationRequest;

import com.example.carryingmedicine.News.Item;
import com.example.carryingmedicine.News.MyAdapter;
import com.example.carryingmedicine.R;
import com.example.carryingmedicine.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.annotation.Nullable;

import static com.example.carryingmedicine.login.LoginActivity.doctorName;

public class FragHome extends Fragment{
    private View view;
    private static String username;
    private static String userid;
    private TextView text_name;

    public static String userID,doctorID,doctorName,now_disease,reservation_date,reservation_number,reservation_time,userName;
    ArrayList<ClinicData> mArrayList=new ArrayList<ClinicData>();
    Response.Listener<String> responseListener;
    //리사이클뷰
    RecyclerView recyclerView;
    ArrayList<Item> items= new ArrayList<>();
    MyAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag_home,container,false);
// 뉴스 리사이클러
        recyclerView=view.findViewById(R.id.recycler);
        adapter=new MyAdapter(items,getContext());
        recyclerView.setAdapter(adapter);
        //리사이클러의 배치관리자 설정
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //대량의 데이터 추가작업
        readRss();
// 사용자 이름 표시
        text_name = view.findViewById(R.id.text_name);
        text_name.setText("안녕하세요  "+ LoginActivity.doctorName + "님");
///예약현황 보기 및 진료
        StartClinic();
        return view;
    }
//**********************************진료 시작********************************************************/
    public void StartClinic(){
        mArrayList.clear();
        Button doctor_info_but = view.findViewById(R.id.doctor_info_but); // 진료하기 버튼
        doctor_info_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                            Intent intent1 = new Intent(getActivity(), Clinic_List_Main.class);
                            intent1.putExtra("userID", userID);                           // 아이디값
                            intent1.putExtra("userName", userName);
                            intent1.putExtra("doctorName",doctorName);
                            intent1.putExtra("doctor_id",doctorID);
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

    //리사이클뷰###############################################################################################
    void readRss(){

        try {
            URL url = new URL("http://rss.hankyung.com/new/news_main.xml");

            //스트림역할하여 데이터 읽어오기 : 인터넷 작업은 반드시 퍼미션 작성해야함.
            //Network작업은 반드시 별도의 Thread만 할 수 있다.
            //별도의 Thread 객체 생성
            RssFeedTask task= new RssFeedTask();
            task.execute(url); //doInBackground()메소드가 발동[thread의 start()와 같은 역할]
        } catch (MalformedURLException e) { e.printStackTrace();}

    }// readRss Method ..

    //이너 클래스
    class RssFeedTask extends AsyncTask<URL, Void, String> {

        //Thread의 run()메소드와 같은 역할
        @Override
        protected String doInBackground(URL... urls) { //...는 여러개를 받는 의미, 만약 task.execute(url, url2, url3); 보내면 urls[3]로 받는다.
            //전달받은 URL 객체
            URL url= urls[0];

            //해임달(URL)에게 무지개로드(Stream) 열도록..
            try {
                InputStream is= url.openStream();

                //읽어온 xml를 파싱(분석)해주는 객체 생성
                XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
                XmlPullParser xpp= factory.newPullParser();

                //utf-8은 한글도 읽어오기 위한 인코딩 방식
                xpp.setInput(is, "utf-8");
                int eventType= xpp.getEventType();

                Item item= null;
                String tagName= null;

                while (eventType != XmlPullParser.END_DOCUMENT){
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tagName=xpp.getName();

                            if(tagName.equals("item")){
                                item= new Item();
                            }else if(tagName.equals("title")){
                                xpp.next();
                                if(item!=null) item.setTitle(xpp.getText());
                            }else if(tagName.equals("link")){
                                xpp.next();
                                if(item!=null) item.setLink(xpp.getText());
                            }else if(tagName.equals("description")){
                                xpp.next();
                                if(item!=null) item.setDesc(xpp.getText());
                            }else if(tagName.equals("image")){
                                xpp.next();
                                if(item!=null) item.setImgUrl(xpp.getText());
                            }else if(tagName.equals("pubDate")){
                                xpp.next();
                                if(item!=null) item.setDate(xpp.getText());
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            tagName=xpp.getName();
                            if(tagName.equals("item")){
                                //읽어온 기사 한개를 대량의 데이터에 추가
                                items.add(item);
                                item=null;

                                //리사이클러의 아답터에게 데이터가
                                //변경되었다는 것을 통지
                                publishProgress();

                            }
                            break;
                    }
                    eventType= xpp.next();
                }//while

                //파싱 작업이 완료되었다!!
                //테스트로 Toastㄹ 보여주기, 단 별도 스레드는
                //UI작업이 불가! 그래서 runOnUiThread()를 이용했었음.
                //이 UI작업을 하는 별도의 메소드로
                //결과를 리턴하는 방식을 사용

            } catch (IOException e) {e.printStackTrace();} catch (XmlPullParserException e) {e.printStackTrace();}

            return "파싱종료"; // 리턴 값은 onPostExecute(String s) s에 전달된다.
        }//doIBackground()
        //doInbackground 작업도중에 프로그레스메소드를 호출하면 자동으로 실행되는 메소드
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //이곳에서도 유아이 변경작업가능
            adapter.notifyItemInserted(items.size());
        }

        //doInBackground메소드가 종료된 후
        //UI작업을 위해 자동 실행되는 메소드
        //runOnUiThread()와 비슷한 역할
        @Override
        protected void onPostExecute(String s) { //매개 변수 s에 들어오는 값음 doIBackground()의 return 값이다.
            super.onPostExecute(s);

            //리사이클러에서 보여주는 데이터를 가진 아답터에게 데이터가 변경되었다고 공지
            //adapter.notifyDataSetChanged();

            //이 메소드 안에서는 UI변경 작업 가능
            Toast.makeText(getActivity(), s+":"+items.size(), Toast.LENGTH_SHORT).show();
        }
    }//RssFeedTask class

}
