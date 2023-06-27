package com.example.businesscard;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ElecCardExchangeFragment extends Fragment {
    private View view;
    // 변수 초기화
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_elec_card_exchange, container, false);

        Intent intent = new Intent(getActivity(),QRCode.class);
        startActivity(intent);

        return inflater.inflate(R.layout.qwe, container, false);

        // 변수 할당

        // 타이머 기간 초기화

        // 카운트다운 타이머 초기화
//        new CountDownTimer(duration, 1000) {
//            @Override
//            public void onTick(long l) {
//                // 체크 표시할 때
//                // 밀리초를 분 및 초로 변환
//                String sDuration = String.format(Locale.ENGLISH, "%02d",
//                        TimeUnit.MILLISECONDS.toSeconds(l),
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
//                // textView에서 변환된 문자열 설정
//                textView.setText(sDuration);
//            }

//            @Override
//            public void onFinish() {
//                // 끝났을 때
//                // textView를 숨김
//                textView.setVisibility(View.GONE);
//
//                // 토스트메시지 띄움
//                Toast.makeText(getActivity(), "제한 시간이 초과하였습니다.다시 시도해 주세요.", Toast.LENGTH_LONG).show();
//            }
//        }.start();

//        return view;
    }
}