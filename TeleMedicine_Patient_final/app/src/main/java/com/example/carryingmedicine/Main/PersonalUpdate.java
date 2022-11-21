/*package com.example.carryingmedicine.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.carryingmedicine.R;
import com.example.carryingmedicine.RecordHealth.First_Fragment;
import com.example.carryingmedicine.RecordHealth.InfomationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.carryingmedicine.Main.PersonalHealthActivity.userDrug;


public class PersonalUpdate extends AppCompatActivity {

    private EditText personal_height, personal_weight, personal_past, personal_family, personal_surgical, personal_drug, personal_drink
            , personal_smoke;
    private Button personal_check;
    InformationUpdate informationUpdate;


    First_Fragment myFragment;
    Response.Listener<String> responseListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalhealth);
        personal_height = findViewById(R.id.personal_height);
        personal_weight = findViewById(R.id.personal_weight);
        personal_past = findViewById(R.id.personal_past);
        personal_family = findViewById(R.id.personal_family);
        personal_surgical = findViewById(R.id.personal_surgical);
        personal_drug = findViewById(R.id.personal_drug);
        personal_drug.setText(userDrug);
        personal_drink = findViewById(R.id.personal_drink);
        personal_smoke = findViewById(R.id.personal_smoke);
        personal_check=findViewById(R.id.personal_check);


        TextView healthTextView =findViewById(R.id.healthTextView);
        healthTextView.setText("건강정보 수정하기");
        personal_check.setText("수정 완료");

        String userID = MainActivity.userID;
        personal_check.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                personal_check=findViewById(R.id.personal_check);
                personal_check.setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View v) {

                        responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Toast.makeText(getApplicationContext(), "여기 어디입니까", Toast.LENGTH_SHORT).show();
                                    boolean success3 = jsonObject.getBoolean("success3");
                                    if (success3) {
                                        Toast.makeText(getApplicationContext(), personal_surgical.getText().toString(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PersonalUpdate.this, MainActivity.class);
                                        startActivity(intent);
                                        //finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // 서버로 volley를 이용해서 요청을 함
                            }
                        };

                        String person_height = personal_height.getText().toString();
                        String person_weight = personal_weight.getText().toString();

                        informationUpdate = new InformationUpdate(personal_drug.getText().toString(), userID, responseListener);
                        RequestQueue queue= Volley.newRequestQueue(PersonalUpdate.this);
                        queue.add(informationUpdate);
                    }
                });
            }
        });



    }
}
*/
