package com.example.businesscard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemberDropActivity extends AppCompatActivity {

    private Button btn_drop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberdrop);

        btn_drop = findViewById(R.id.btn_drop);

        btn_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MemberDropActivity.this, LoginActivity.class));
            }
        });
    }
}