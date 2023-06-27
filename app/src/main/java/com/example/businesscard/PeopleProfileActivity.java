package com.example.businesscard;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.net.URI;

public class PeopleProfileActivity extends AppCompatActivity {
    LoginActivity.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    String phoneNo;
    String table = "CardModel";
    String sms = "전자명함 이미지 입니다.";
    TextView textView_name;
    TextView textView_company;
    TextView textView_depart;
    TextView textView_address;
    TextView textView_position;
    TextView textView_phone;
    TextView textView_comPhone;
    TextView textView_fax;
    TextView textView_email;
    TextView textView_homepage;
    private String username,company,depart,address,position,
            phoneNum,companyNum,fax,email,homepage;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_profile);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            this.username = extras.getString("username");
            this.company = extras.getString("company");
            this.depart = extras.getString("depart");
            this.address = extras.getString("address");
            this.position = extras.getString("position");
            this.phoneNum = extras.getString("phoneNum");
            this.companyNum = extras.getString("companyNum");
            this.fax = extras.getString("fax");
            this.email = extras.getString("email");
            this.homepage = extras.getString("homepage");
        }

        Intent mail_intent = new Intent(Intent.ACTION_SEND);
        mail_intent .setType("*/*");
        LinearLayout layout_email = findViewById(R.id.linearlayoutEmail);
        LinearLayout layout_text = findViewById(R.id.linearLayoutPhone);
        textView_name = findViewById(R.id.textView_profile_name);
        textView_company = findViewById(R.id.textView_profile_company);
        textView_depart = findViewById(R.id.textView_profile_depart);
        textView_address = findViewById(R.id.textView_profile_address);
        textView_position = findViewById(R.id.textView_profile_position);
        textView_phone = findViewById(R.id.textView_profile_phone);
        textView_comPhone = findViewById(R.id.textView_profile_comPhone);
        textView_fax = findViewById(R.id.textView_profile_fax);
        textView_email = findViewById(R.id.textView_profile_email);
        textView_homepage = findViewById(R.id.textView_profile_homepage);
        textView_name.setText(username);
        textView_company.setText(company);
        textView_depart.setText(depart);
        textView_address.setText(address);
        textView_position.setText(position);
        textView_phone.setText(phoneNum);
        textView_comPhone.setText(companyNum);
        textView_fax.setText(fax);
        textView_email.setText(email);
        textView_homepage.setText(homepage);


        layout_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d("여기", "문자메시지");
                    mail_intent.putExtra(Intent.EXTRA_EMAIL, "tmakdlf2482@naver.com");
                    startActivity(mail_intent);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        layout_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendMMSIntent(sms);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
    private void sendMMSIntent(String message) {
        Uri uri = Uri.parse("sms: " + phoneNo);
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
        sendIntent.putExtra("subject", "MMS TEST");
        sendIntent.putExtra("sms_body", message);
        startActivity(sendIntent);
        finish();
    }
}