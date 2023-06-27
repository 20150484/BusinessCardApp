package com.example.businesscard;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ElecCardListFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private CardView_ItemAdapter adapter;
    LoginActivity.myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;
    String phoneNo;
    String table = "CardModel";
    ArrayList<CardView_Item> items = new ArrayList<>();

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_elec_card_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        adapter = new CardView_ItemAdapter(getContext(), items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager); // 필수
        myDBHelper = new LoginActivity.myDBHelper(getContext());
        sqlDB = myDBHelper.getWritableDatabase();
        Cursor cursor;
        if(sqlDB.rawQuery("SELECT * FROM "+table+";", null)!= null) {
            cursor = sqlDB.rawQuery("SELECT * FROM " + table + ";", null);
            while (cursor.moveToNext()) {
                if (cursor.getCount() == 0) {
                    break;
                } else {
                    adapter.addItem(new CardView_Item(
                            cursor.getString(cursor.getColumnIndex("UserName")),
                            cursor.getString(cursor.getColumnIndex("Email"))));
                    Log.d("카운트","카운트");
                    Log.d("여기",cursor.getString(cursor.getColumnIndex("UserName")));

                }
            }
        }


        recyclerView.setAdapter(adapter);
        return view;
    }

    public boolean isCursorEmpty(Cursor cursor){
        return !cursor.moveToFirst() || cursor.getCount() == 0;
    }

    @SuppressLint("Range")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @SuppressLint("Range")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareData();
    }

    private void prepareData() {
    }
}