package com.example.businesscard;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


import static com.example.businesscard.OCRGeneralAPIDemo.OcrGeneralAPIDemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ScanNameCard extends AppCompatActivity implements View.OnClickListener {
    String username,company,depart,address,position,phone,comPhone,fax,email,homepage;
    final String TAG = getClass().getSimpleName();
    ImageView imageView;
    Button cameraBtn, albumBtn, registerBtn;

    static CardModel cardModel= new CardModel();
    Uri uri;
    final static int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_papar_card_scan);

        // 레이아웃과 변수 연결
        imageView = findViewById(R.id.imageView);
        cameraBtn = findViewById(R.id.button);
        albumBtn = findViewById(R.id.button2);
        registerBtn =findViewById(R.id.button_scan_register);
        // 카메라 버튼에 리스터 추가
        cameraBtn.setOnClickListener(this::onClick);
        albumBtn.setOnClickListener(this::onClickAl);

        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanNameCard.this, ScanRegister.class);
                intent.putExtra("username", cardModel.getName());
                intent.putExtra("company",cardModel.getCompany());
                intent.putExtra("depart",cardModel.getDepartment());
                Log.d("여기2",cardModel.getName());
                intent.putExtra("address",cardModel.getAddress());
                intent.putExtra("position",cardModel.getPosition());
                intent.putExtra("phone",cardModel.getMobile());
                intent.putExtra("comPhone",cardModel.getTel());
                intent.putExtra("fax",cardModel.getFax());
                intent.putExtra("email",cardModel.getEmail());
                intent.putExtra("homepage",cardModel.getHomepage());
                startActivity(intent);
            }
        });
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    // 버튼 onClick리스터 처리부분
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button:
                // 카메라 앱을 여는 소스
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,TAKE_PICTURE);
                break;
        }
    }

    public void onClickAl(View v){
        switch(v.getId()){
            case R.id.button2:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);
                break;
        }
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if( result.getResultCode() == RESULT_OK && result.getData() !=null) {
                        uri = result.getData().getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                            imageView.setImageBitmap(bitmap);
                            String base64 = bitmapToBase64(bitmap);
                            new Thread(()->{
                                cardModel = OcrGeneralAPIDemo(base64);
                            }).start();
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    // 카메라로 촬영한 영상을 가져오는 부분
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Bitmap bitmap;
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && intent.hasExtra("data")) {
                    bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                        String base64 = bitmapToBase64(bitmap);
                        new Thread(()->{
                            cardModel = OcrGeneralAPIDemo(base64);
                        }).start();
//                      Log.i(TAG, base64);
                    }
                }

                break;
        }
    }

    public String bitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        // 인코딩된 ByteStream을 String으로 획득
        byte[] image = byteArrayOutputStream.toByteArray();
        String byteStream = Base64.encodeToString(image, 0);
        return byteStream;
    }
}