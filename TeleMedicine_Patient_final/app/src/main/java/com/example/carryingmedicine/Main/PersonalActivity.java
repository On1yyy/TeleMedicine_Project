package com.example.carryingmedicine.Main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.example.carryingmedicine.R;
import com.example.carryingmedicine.RecordHealth.First_Fragment;

import static com.example.carryingmedicine.Main.MainActivity.userBirth;
import static com.example.carryingmedicine.Main.MainActivity.userID;
import static com.example.carryingmedicine.Main.MainActivity.userName;

public class PersonalActivity extends AppCompatActivity {

    private TextView personalID, personalName, personalBirth;//, textInfo;
    private Button checkButton;

    First_Fragment myFragment;
    Response.Listener<String> responseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity);

        personalID = findViewById(R.id.personalID);
        personalName = findViewById(R.id.personalName);
        personalBirth = findViewById(R.id.personalBirth);
        //textInfo = findViewById(R.id.textInfo);

        personalID.setText(userID);
        personalName.setText(userName);
        personalBirth.setText(userBirth);
        //textInfo.setText("처방전 작성 및 보험처리를 위해서는 개인정보가 필요합니다");


        checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}