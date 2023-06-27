package com.example.businesscard;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

public class ScanRegister extends AppCompatActivity {
    private String username,company,depart,address,position,
            phoneNum,companyNum,fax,email,homepage;
    private MaterialButton button_signup;
    LoginActivity.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.fragment_elec_card_register_modify);

        myDBHelper = new LoginActivity.myDBHelper(this);
        myDBHelper.onUpgrade(sqlDB,0,1);
        sqlDB = myDBHelper.getWritableDatabase();

        EditText edit_name = findViewById(R.id.modify_username);
        EditText edit_company = findViewById(R.id.modify_companyName);
        EditText edit_depart = findViewById(R.id.modify_department);
        EditText edit_address = findViewById(R.id.modify_address);
        EditText edit_position = findViewById(R.id.modify_position);
        EditText edit_phoneNum = findViewById(R.id.modify_phoneNumber);
        EditText edit_companyNum = findViewById(R.id.modify_companyPhoneNumber);
        EditText edit_fax = findViewById(R.id.modify_fax);
        EditText edit_email = findViewById(R.id.modify_email);
        EditText edit_homepage = findViewById(R.id.modify_homepage);

        edit_name.setText(username);
        Log.d("여기3",username);
        edit_company.setText(company);
        edit_depart.setText(depart);
        edit_address.setText(address);
        edit_position.setText(position);
        edit_phoneNum.setText(phoneNum);
        edit_companyNum.setText(companyNum);
        edit_fax.setText(fax);
        edit_email.setText(email);
        edit_homepage.setText(homepage);


        button_signup = findViewById(R.id.modify_update_btn);
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    username = edit_name.getText().toString();

                    company = edit_company.getText().toString();
                    depart = edit_depart.getText().toString();
                    address = edit_address.getText().toString();
                    position = edit_position.getText().toString();
                    phoneNum = edit_phoneNum.getText().toString();
                    companyNum = edit_companyNum.getText().toString();
                    fax = edit_fax.getText().toString();
                    email = edit_email.getText().toString();
                    homepage = edit_homepage.getText().toString();
                    if (username.equals("") || phoneNum.equals("") || email.equals("") || address.equals("")) {
                        Toast.makeText(ScanRegister.this, "이름,휴대전화번호,주소,이메일은 필수입력사항입니다.", Toast.LENGTH_LONG).show();
                    } else {
                        sqlDB.execSQL("INSERT INTO CardModel VALUES"+"("+"'"+username+"',"+"'"+company+"',"+"'"+depart+"',"+"'"+address+"',"
                                +"'"+position+"',"+"'"+phoneNum+"',"+"'"+companyNum+"',"+"'"+fax+"',"+"'"+email+"',"+"'"+homepage+"')");
                        Toast.makeText(ScanRegister.this, "전자명함 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        });

    }
}