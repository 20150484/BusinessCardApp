package com.example.businesscard;

import static android.content.Intent.ACTION_VIEW;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class QRCode extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private String text;
    private CardModel cardModel = new CardModel();
    LoginActivity.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    private String username,company,depart,address,position,
            phoneNum,companyNum,fax,email,homepage;
    String table = "MyCard";
    String str;
    String codeurl;
//    String str = "BEGIN:VCARD\n" + "VERSION:3.0\r\n" +
//            "N:" + "박민재" + "\r\n" +
//            "ORG:" + "네이버" + "\r\n" +
//            "TITLE:" + "123." + "\r\n" +
//            "TEL:" + "11" + "\r\n" +
//            "URL:" + "naver.com" + "\r\n" +
//            "EMAIL:" + "rhdwnd04@gmail.com" + "\r\n" +
//            "ADR:" + "11" + "\r\n" +
//            "NOTE:" + "11" + "\r\n" +
//            "END:VCARD\r\n";



    @SuppressLint({"MissingInflatedId", "Range"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor cursor;
        myDBHelper = new LoginActivity.myDBHelper(this);
        sqlDB = myDBHelper.getWritableDatabase();
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
                    cardModel = new CardModel(username,company,depart,address,position,phoneNum,companyNum,fax,email,homepage);
                }
            }
        }
        str = ModelToString(cardModel);
        try {
            codeurl = new String(str.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_create_qr);

        imageView = (ImageView) findViewById(R.id.qrcode);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            BitMatrix bitMatrix = multiFormatWriter.encode(codeurl, BarcodeFormat.QR_CODE,400,400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        }catch (Exception e){}
    }
    public String ModelToString(CardModel model){
        String str = "BEGIN:VCARD\n" + "VERSION:3.0\r\n" +
                "N:" + model.name + "\r\n" +
                "ORG:" + model.company + "\r\n" +
                "TITLE:" + model.mobile + "\r\n" +
                "TEL:" + model.tel + "\r\n" +
                "URL:" + model.homepage + "\r\n" +
                "EMAIL:" + model.email + "\r\n" +
                "ADR:" + model.address + "\r\n" +
                "NOTE:" + " " + "\r\n" +
                "END:VCARD\r\n";;
        return str;
    }
}