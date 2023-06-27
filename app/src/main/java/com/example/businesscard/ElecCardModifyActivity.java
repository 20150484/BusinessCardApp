package com.example.businesscard;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;

public class ElecCardModifyActivity extends AppCompatActivity {
    private String username,company,depart,address,position,
            phoneNum,companyNum,fax,email,homepage;
    private EditText edit_name,edit_company,edit_depart,edit_address,edit_position,edit_phoneNum,
            edit_companyNum,edit_fax,edit_email,edit_homepage;
    LoginActivity.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    private MaterialButton button_signup,button_udpate;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_elec_mycard_register_modify);

        edit_name = findViewById(R.id.Modify_myUsername);
        edit_company = findViewById(R.id.modify_myCompanyName);
        edit_depart = findViewById(R.id.modify_myDepartment);
        edit_address = findViewById(R.id.modify_myAddress);
        edit_position = findViewById(R.id.modify_myPosition);
        edit_phoneNum = findViewById(R.id.modify_myPhoneNumber);
        edit_companyNum = findViewById(R.id.modify_myCompanyPhoneNumber);
        edit_fax = findViewById(R.id.modify_myFax);
        edit_email = findViewById(R.id.modify_myEmail);
        edit_homepage = findViewById(R.id.modify_myHomepage);
        button_signup = findViewById(R.id.modify_mySignup_btn);
        button_udpate = findViewById(R.id.modify_myUpdate_btn);
        myDBHelper = new LoginActivity.myDBHelper(ElecCardModifyActivity.this);
        myDBHelper.onUpgrade(sqlDB,0,1);
        sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        if(sqlDB.rawQuery("SELECT * FROM MyCard;", null)!= null) {
            cursor = sqlDB.rawQuery("SELECT * FROM MyCard;", null);
            while (cursor.moveToNext()) {
                if (!(cursor.getCount() == 0)) {
                    username = cursor.getString(cursor.getColumnIndex("UserName"));
                    company = cursor.getString(cursor.getColumnIndex("Company"));
                    depart = cursor.getString(cursor.getColumnIndex("Department"));
                    address = cursor.getString(cursor.getColumnIndex("Address"));
                    position = cursor.getString(cursor.getColumnIndex("Position"));
                    phoneNum = cursor.getString(cursor.getColumnIndex("Mobile"));
                    companyNum = cursor.getString(cursor.getColumnIndex("Tel"));
                    fax = cursor.getString(cursor.getColumnIndex("Fax"));
                    email = cursor.getString(cursor.getColumnIndex("Email"));
                    homepage = cursor.getString(cursor.getColumnIndex("Homepage"));
                    edit_name.setText(username);
                    edit_company.setText(company);
                    edit_depart.setText(depart);
                    edit_address.setText(address);
                    edit_position.setText(position);
                    edit_phoneNum.setText(phoneNum);
                    edit_companyNum.setText(companyNum);
                    edit_fax.setText(fax);
                    edit_email.setText(email);
                    edit_homepage.setText(homepage);
                }else{
                    break;
                }
            }
        }
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    Toast.makeText(ElecCardModifyActivity.this, "이름,휴대전화번호,주소,이메일은 필수입력사항입니다.", Toast.LENGTH_LONG).show();
                } else {
                    sqlDB.execSQL("INSERT INTO MyCard VALUES"+"("+"'"+username+"',"+"'"+company+"',"+"'"+depart+"',"+"'"+address+"',"
                            +"'"+position+"',"+"'"+phoneNum+"',"+"'"+companyNum+"',"+"'"+fax+"',"+"'"+email+"',"+"'"+homepage+"')");
                    Toast.makeText(ElecCardModifyActivity.this, "전자명함 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }

            }

        });

        button_udpate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                if(email.equals("")) {
                    Toast.makeText(ElecCardModifyActivity.this, "이메일은 필수입력사항입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    sqlDB.execSQL("DELETE FROM MyCard WHERE Email = " + "'" + email + "'");
                    sqlDB.execSQL("INSERT INTO MyCard VALUES" + "(" + "'" + username + "'," + "'" + company + "'," + "'" + depart + "'," + "'" + address + "',"
                            + "'" + position + "'," + "'" + phoneNum + "'," + "'" + companyNum + "'," + "'" + fax + "'," + "'" + email + "'," + "'" + homepage + "')");
                    Toast.makeText(ElecCardModifyActivity.this, "전자명함이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}