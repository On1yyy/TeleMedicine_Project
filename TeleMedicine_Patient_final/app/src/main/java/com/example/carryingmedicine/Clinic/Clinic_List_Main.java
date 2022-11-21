package com.example.carryingmedicine.Clinic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carryingmedicine.ClinicDetail.ClinicDtail;
import com.example.carryingmedicine.R;

import java.util.ArrayList;
import java.util.List;

public class Clinic_List_Main extends AppCompatActivity implements OnClinicItemClickListener
{
    ArrayList<ClinicData> mArrayList;
    private String userID,userName;
    public static Activity Clinic_List_Main;
    RecyclerView recyclerView;
    EditText clinic_se;
    ClinicAdapter adapter;
    OnClinicItemClickListener listener;
    List<Clinic> exampleList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Clinic_List_Main= com.example.carryingmedicine.Clinic.Clinic_List_Main.this;
        setContentView(R.layout.clinic_main_activity);
        fillList();
        setUpRecyclerView();

        clinic_se = findViewById(R.id.clinic_se); // 에딧트 텍스트의 검색기능
        clinic_se.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) { //에딧트 박스의 변화값에따라 필터를 거쳐 검색을 실시
                adapter.getFilter().filter(editable); }});

        adapter.setOnItemClickListener(new OnClinicItemClickListener() {
            @Override
            public void onItemClick(ClinicAdapter.ViewHolder holder, View view, int position) {
                Clinic item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨 : " + item.getDoctorName(), Toast.LENGTH_LONG).show();

                Intent intent= new Intent(com.example.carryingmedicine.Clinic.Clinic_List_Main.this, ClinicDtail.class);
                intent.putExtra("selectClinic", item);
                intent.putExtra("userID", userID);
                intent.putExtra("userName", userName);

                startActivity(intent);
                mArrayList.clear();
            }
        });
    }
    public void fillList(){
        mArrayList=new ArrayList<>();
        mArrayList.clear();
        Intent intent = getIntent(); // 로그인에서 넘겨온값을 get으로 받음
        mArrayList = intent.getParcelableArrayListExtra("ArrayList");  //닥터의 어레이리스트 값 받음
        userID = intent.getStringExtra("userID"); //로그인에서 유저네임 값을 받음
        userName = intent.getStringExtra("userName"); //로그인에서 유저네임 값을 받음

        for(int i =0 ; i < mArrayList.size(); i++) //어레이 리스트의 크기를 찾고 그값만큼 의사리스트를 생성
        {
            String reservation_number= mArrayList.get(i).getReservation_number();
            String userName = mArrayList.get(i).getUserName();
            String doctorName = mArrayList.get(i).getDoctorName();
            String userID = mArrayList.get(i).getUserID();
            String doctorID = mArrayList.get(i).getDoctorID();
            String reservation_date = mArrayList.get(i).getReservation_date();
            String reservation_time= mArrayList.get(i).getReservation_time();
            String now_disease = mArrayList.get(i).getNow_disease();
            exampleList.add(new Clinic(reservation_number,userName,doctorName,userID,doctorID,reservation_date,reservation_time,now_disease));
        }

    }
    public void setUpRecyclerView() // 리사이클러뷰 어댑터 생성 및 연결
    {
        recyclerView =findViewById(R.id.C_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        adapter = new ClinicAdapter(exampleList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {   // 메뉴버튼의 검색을 위한 함수
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.clinic_search,menu);  //생성해놓은 메뉴 찾음
        MenuItem searchItem = menu.findItem(R.id.search); // search 버튼 찾음
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { // 메뉴의 서치의 실시간 반응에따라 필터로하여 원하는 정보를 검색
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //메뉴의 설정 버튼
        int id = item.getItemId();
        if(id== R.id.action_settings)
        {
            Toast.makeText(this,"설정",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(ClinicAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

}
