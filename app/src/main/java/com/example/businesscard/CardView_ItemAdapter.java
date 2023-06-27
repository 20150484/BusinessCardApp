package com.example.businesscard;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.CookieHandler;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CardView_ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    public static List<CardView_Item> items;

    public CardView_ItemAdapter(Context context, List<CardView_Item> items) {
        this.context = context;
        this.items = items;
    }

    private List<CardView_Item> mItemList;
    private OnItemClickEventListener ItemClickListener;


    public void addItem(CardView_Item item) {
        items.add(item);
    }

    public interface OnItemClickEventListener {
        void onItemClick(View a_view, int a_position);
    }
    public CardView_ItemAdapter(List<CardView_Item> list) {
        mItemList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(viewType, viewGroup, false);
        final RecyclerView.ViewHolder viewHolder;
        if (viewType == CardView_Header_Holder.VIEW_TYPE) {
            viewHolder = new CardView_Header_Holder(view);
            return (CardView_Header_Holder) viewHolder;
        }else {
            viewHolder = new CardView_Holder(view);
            return (CardView_Holder) viewHolder;
        }
//        return new CardView_Holder(LayoutInflater.from(context).inflate(R.layout.item_show, viewGroup, false));
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof CardView_Header_Holder) {
                CardView_Header_Holder headerViewHolder = (CardView_Header_Holder) holder;
               Log.d("뷰홀더2","ㅇㅇ");
        }
            else{
            // 기본적으로 header 를 빼고 item 을 구한다.
            final CardView_Item item = items.get(position-1);
            CardView_Holder cardView_holder = (CardView_Holder) holder;
            ((CardView_Holder) holder).setItem(item);
            ((CardView_Holder) holder).txt_email.setTag(position);

            Log.d("뷰홀더", "ㅇㅇ");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
            //holder.name.setText(items.get(position).getName());
            //.email.setText(items.get(position).getEmail());
//        }
    }
    @Override
    public int getItemViewType(int a_position) {
        if (a_position == 0) {
            return CardView_Header_Holder.VIEW_TYPE;
        } else {
            return CardView_Holder.VIEW_TYPE;
        }
    }
    @Override
    public int getItemCount() {
        return items.size()+1;
    }
    public CardView_Item getItem(int position){
        return items.get(position);
    }

}