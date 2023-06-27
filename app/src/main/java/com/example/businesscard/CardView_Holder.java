package com.example.businesscard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.businesscard.CardView_ItemAdapter.OnItemClickEventListener;

public class CardView_Holder extends RecyclerView.ViewHolder {

    TextView txt_name;
    TextView txt_email;
    RecyclerView recyclerView;
    public static int VIEW_TYPE = R.layout.item_show;
    LoginActivity.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    private String username,company,depart,address,position,
            phoneNum,companyNum,fax,email,homepage;
    private String table = "CardModel";
    TextView textView;

    @SuppressLint("Range")
    public CardView_Holder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.recycler_view);
        txt_name = itemView.findViewById(R.id.text_view_name);
        txt_email = itemView.findViewById(R.id.text_view_email);
        myDBHelper = new LoginActivity.myDBHelper(itemView.getContext());
        myDBHelper.onUpgrade(sqlDB,0,1);
        sqlDB = myDBHelper.getWritableDatabase();



        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cursor;
                if(sqlDB.rawQuery("SELECT * FROM "+table+";", null)!= null) {
                    cursor = sqlDB.rawQuery("SELECT * FROM " + table + ";", null);
                    while (cursor.moveToNext()) {
                        if (!txt_email.getText().toString().equals(cursor.getString(cursor.getColumnIndex("Email")))) {
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
                        }
                    }
                }
                Context context = view.getContext();
                Intent intent = new Intent(context, PeopleProfileActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("company",company);
                intent.putExtra("depart",depart);

                intent.putExtra("address",address);
                intent.putExtra("position",position);
                intent.putExtra("phone",phoneNum);
                intent.putExtra("comPhone",companyNum);
                intent.putExtra("fax",fax);
                intent.putExtra("email",email);
                intent.putExtra("homepage",homepage);
                context.startActivity(intent);

            }
        });


    }
    public  void setItem(CardView_Item item){
        txt_name.setText(item.getName());
        txt_email.setText(item.getEmail());
    }

//    public CardView_Holder(@NonNull View itemView) {
//        super(itemView);
//        name = itemView.findViewById(R.id.text_view_name);
//        email = itemView.findViewById(R.id.text_view_email);
//
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context context = view.getContext();
//
//                Intent intent = new Intent(context, PeopleProfileActivity.class);
//
//                context.startActivity(intent);
//            }
//        });
//
////        itemView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    final int position = getAbsoluteAdapterPosition();
////                    if (position != RecyclerView.NO_POSITION) {
////                        itemClickEventListener.onItemClick(view, position);
////                    }
////                }
////        });
}