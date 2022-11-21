package com.example.carryingmedicine.Doctorlist;

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

import com.example.carryingmedicine.DoctorDetail.DoctorDtail;
import com.example.carryingmedicine.R;

import java.util.ArrayList;
import java.util.List;

public class Doctor_List_Main extends AppCompatActivity implements OnDoctorItemClickListener {


    ArrayList<DoctorData> mArrayList; //진료창에 들어오면 닥터 테이블의 정보를 받기위한 객체 어레이리스트
    private String userID, userName;  // 유저의 이름과 아이디를 받기위한 변수
    List<Doctor> exampleList = new ArrayList<>();  // mArrayList를 받아 저장할 의사 리스트
    DoctorAdapter adapter;
    RecyclerView recyclerView;                    //리사이클뷰
    EditText doctor_se;                           // 검색 에딧트 생성
    OnDoctorItemClickListener listener;          //검색이벤트의 클릭이벤트를 처리하기위한 listener
    public static Activity Doctor_list_main;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Doctor_list_main= Doctor_List_Main.this;
        setContentView(R.layout.doctor_main_activity);
        fillList();          //exampleList의 내용을채움
        setUpRecyclerView(); // 어댑터와 연결

        doctor_se = findViewById(R.id.doctor_se); // 에딧트 텍스트의 검색기능
        doctor_se.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) { //에딧트 박스의 변화값에따라 필터를 거쳐 검색을 실시
                adapter.getFilter().filter(editable); }});

        adapter.setOnItemClickListener(new OnDoctorItemClickListener() { //카드뷰중 하나를 고를시 선택 이벤트
            @Override
            public void onItemClick(DoctorAdapter.ViewHolder holder, View view, int position) {
                Doctor item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨 : " + item.getName(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Doctor_List_Main.this, DoctorDtail.class);
                intent.putExtra("selectDoctor", item);
                intent.putExtra("userID", userID);
                intent.putExtra("userName", userName);
                startActivity(intent);
                mArrayList.clear();
            }
        });
    }

    public void fillList()
    {
        mArrayList = new ArrayList<>();
        mArrayList.clear();
        Intent intent = getIntent(); // 로그인에서 넘겨온값을 get으로 받음
        mArrayList = intent.getParcelableArrayListExtra("ArrayList");  //닥터테이블의 정보를 메인창에서 어레이리스트로 값을 받음
        userID = intent.getStringExtra("userID");                     //로그인에서 유저네임 값을 받음
        userName = intent.getStringExtra("userName");

       // System.out.println(mArrayList);
        for (int i = 0; i < mArrayList.size(); i++) //어레이 리스트의 크기를 찾고 그값만큼 의사리스트를 생성
        {
            String id = mArrayList.get(i).getDoctor_id();
            String name = mArrayList.get(i).getDoctor_Name();
            String Birth = mArrayList.get(i).getDoctor_Birth();
            String School = mArrayList.get(i).getDoctor_School();
            String H_name = mArrayList.get(i).getDoctor_Hospital();
            String H_number = mArrayList.get(i).getDoctor_Hospital_Number();
            String H_address = mArrayList.get(i).getDoctor_Hospital_Adress();
            String major = mArrayList.get(i).getDoctor_Class();
            exampleList.add(new Doctor(id,name,Birth,School,H_name,H_number,H_address,major));  //받아온 값을 넣음.
        }
    }

    public void setUpRecyclerView() // 리사이클러뷰 어댑터 생성 및 연결
    {
        recyclerView = findViewById(R.id.D_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        adapter = new DoctorAdapter(exampleList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {   // 메뉴버튼의 검색을 위한 함수
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.doctor_search,menu);  //생성해놓은 메뉴 찾음
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
    public void onItemClick(DoctorAdapter.ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }
    /*@Override
    public void onBackPressed() {            //뒤로가기 버튼 처리
        Intent intent = new Intent(Doctor_List_Main.this, MainActivity.class);
        //intent.putExtra("userName", userName);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }*/

}



