package com.example.businesscard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfileFragment extends Fragment {
    private View view;
    private ImageButton imageBtn_elec_card_modify;
    private TextView txt_username,txt_company,txt_depart,txt_address,txt_position,txt_phoneNum
            ,txt_companyNum,txt_fax,txt_email,txt_homepage;
    LoginActivity.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    private String username,company,depart,address,position,
            phoneNum,companyNum,fax,email,homepage;
    String table = "MyCard";
    public MyProfileFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        imageBtn_elec_card_modify = (ImageButton) view.findViewById(R.id.elec_card_modify);

        imageBtn_elec_card_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ElecCardModifyActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    @SuppressLint("Range")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username = "";
        company = "";
        depart = "";
        address = "";
        position = "";
        phoneNum = "";
        companyNum = "";
        fax = "";
        email = "";
        homepage = "";
        txt_username = view.findViewById(R.id.textView_profile_name);
        txt_company = view.findViewById(R.id.textView_profile_company);
        txt_depart = view.findViewById(R.id.textView_profile_depart);
        txt_address = view.findViewById(R.id.textView_profile_address);
        txt_position = view.findViewById(R.id.textView_profile_position);
        txt_phoneNum = view.findViewById(R.id.textView_profile_phone);
        txt_companyNum = view.findViewById(R.id.textView_profile_comPhone);
        txt_fax = view.findViewById(R.id.textView_profile_fax);
        txt_email = view.findViewById(R.id.textView_profile_email);
        txt_homepage = view.findViewById(R.id.textView_profile_homepage);
        myDBHelper = new LoginActivity.myDBHelper(getContext());
        myDBHelper.onUpgrade(sqlDB,0,1);
        sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;

        if(sqlDB.rawQuery("SELECT * FROM "+table+";", null)!= null) {
            cursor = sqlDB.rawQuery("SELECT * FROM " + table + ";", null);
            while (cursor.moveToNext()) {
                if (cursor.getCount() == 0) {
                    break;
                } else {
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
                    txt_username.setText(username);
                    txt_company.setText(company);
                    txt_depart.setText(depart);
                    txt_address.setText(address);
                    txt_position.setText(position);
                    txt_phoneNum.setText(phoneNum);
                    txt_companyNum.setText(companyNum);
                    txt_fax.setText(fax);
                    txt_email.setText(email);
                    txt_homepage.setText(homepage);

                }
            }
        }
    }

}