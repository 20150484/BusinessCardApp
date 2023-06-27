package com.example.businesscard;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class ElecCardRegisterAndModifyFragment extends Fragment {
    private String username,company,depart,address,position,
            phoneNum,companyNum,fax,email,homepage;

    LoginActivity.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    private MaterialButton button_signup,button_update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_elec_card_register_modify, container, false);


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDBHelper = new LoginActivity.myDBHelper(getContext());
        myDBHelper.onUpgrade(sqlDB,0,1);
        sqlDB = myDBHelper.getWritableDatabase();

        EditText edit_name = view.findViewById(R.id.modify_username);
        EditText edit_company = view.findViewById(R.id.modify_companyName);
        EditText edit_depart = view.findViewById(R.id.modify_department);
        EditText edit_address = view.findViewById(R.id.modify_address);
        EditText edit_position = view.findViewById(R.id.modify_position);
        EditText edit_phoneNum = view.findViewById(R.id.modify_phoneNumber);
        EditText edit_companyNum = view.findViewById(R.id.modify_companyPhoneNumber);
        EditText edit_fax = view.findViewById(R.id.modify_fax);
        EditText edit_email = view.findViewById(R.id.modify_email);
        EditText edit_homepage = view.findViewById(R.id.modify_homepage);


        button_signup = view.findViewById(R.id.modify_signup_btn);
        button_update = view.findViewById(R.id.modify_update_btn);
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
                        Toast.makeText(getActivity(), "이름,휴대전화번호,주소,이메일은 필수입력사항입니다.", Toast.LENGTH_LONG).show();
                    } else {
                        sqlDB.execSQL("INSERT INTO CardModel VALUES"+"("+"'"+username+"',"+"'"+company+"',"+"'"+depart+"',"+"'"+address+"',"
                                +"'"+position+"',"+"'"+phoneNum+"',"+"'"+companyNum+"',"+"'"+fax+"',"+"'"+email+"',"+"'"+homepage+"')");
                        Toast.makeText(getActivity(), "전자명함 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

        });

        button_update.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(getContext(), "이메일은 필수입력사항입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    sqlDB.execSQL("DELETE FROM CardModel WHERE Email = " + "'" + email + "'");
                    sqlDB.execSQL("INSERT INTO CardModel VALUES" + "(" + "'" + username + "'," + "'" + company + "'," + "'" + depart + "'," + "'" + address + "',"
                            + "'" + position + "'," + "'" + phoneNum + "'," + "'" + companyNum + "'," + "'" + fax + "'," + "'" + email + "'," + "'" + homepage + "')");
                    Toast.makeText(getContext(), "전자명함이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}