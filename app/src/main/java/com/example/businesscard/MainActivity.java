package com.example.businesscard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Sqlite sql = new Sqlite();
    private ElecCardListFragment elecCardListFragment = new ElecCardListFragment();
    private PaperCardScanFragment paperCardScanFragment = new PaperCardScanFragment();
    private ElecCardExchangeFragment elecCardExchangeFragment = new ElecCardExchangeFragment();
    private ElecCardRegisterAndModifyFragment elecCardRegisterAndModifyFragment = new ElecCardRegisterAndModifyFragment();
    private MyProfileFragment myProfileFragment = new MyProfileFragment();

    // ScanNameCard scanNameCard = new ScanNameCard();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigationview);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, myProfileFragment).commit(); // 시작 프레그먼트 설정

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.elecCardList:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, elecCardListFragment).commit();
                        return true;
                    case R.id.paparCardScan:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, paperCardScanFragment).commit();
                        return true;
                    case R.id.elecCardExchange:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, elecCardExchangeFragment).commit();
                        return true;
                    case R.id.elecCardRegisterAndModify:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, elecCardRegisterAndModifyFragment).commit();
                        return true;
                    case R.id.myProfile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, myProfileFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }
}