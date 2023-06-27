package com.example.businesscard;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CardView_Header_Holder extends RecyclerView.ViewHolder {

    public static int VIEW_TYPE = R.layout.item_show_header;
    CardView_ItemAdapter.OnItemClickEventListener itemClickListener;
    public CardView_Header_Holder(@NonNull View a_itemView) {
        super(a_itemView);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Context context = view.getContext();
//
//                Intent intent = new Intent(context, PeopleProfileActivity.class);
//
//                context.startActivity(intent);
//            }
//        });
        a_itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View a_view) {
                Context context = a_view.getContext();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
//                final int position = getAbsoluteAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    itemClickListener.onItemClick(a_view, position);
//
//                }
            }
        });
    }
}