package com.lumastech.ecoapp.Chat;

import static android.view.View.GONE;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lumastech.ecoapp.Models.Chat;
import com.lumastech.ecoapp.Models.Message;
import com.lumastech.ecoapp.R;

import java.util.List;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Message> itemList;
    Chat chat;
    private ChatAdapter.SelectItem selectItem;

    public ChatAdapter(Chat chat, ChatAdapter.SelectItem selectItem) {
        this.itemList = chat.messages;
        this.selectItem = selectItem;
        this.chat = chat;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = itemList.get(position);
        holder.username.setText(chat.name);
        holder.card_message.setText(message.message);
        holder.created_at.setText(message.created_at);
        holder.messageCon.setCardBackgroundColor(Color.parseColor("#dcfce7"));

        if (Objects.equals(message.sender_id, "100")){
            holder.dp.setVisibility(GONE);
            holder.username.setVisibility(GONE);
            holder.messageCon.setCardBackgroundColor(Color.parseColor("#ffffff"));

            // Change margin
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.itemCont.getLayoutParams();
            params.leftMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 80,
                    holder.itemCont.getResources().getDisplayMetrics()
            );

            params.rightMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,0,
                    holder.itemCont.getResources().getDisplayMetrics()
            );
            holder.itemCont.setLayoutParams(params);
            holder.itemCont.setGravity(Gravity.RIGHT);
        }

        holder.itemCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem.onItemClicked(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView username, card_message, created_at;
        LinearLayout itemCont;
        ImageView dp;
        CardView messageCon;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCont = itemView.findViewById(R.id.container);
            username = itemView.findViewById(R.id.username);
            card_message = itemView.findViewById(R.id.card_message);
            dp = itemView.findViewById(R.id.imageView);
            created_at = itemView.findViewById(R.id.date);
            messageCon = itemView.findViewById(R.id.message_cont);
        }
    }

    public interface SelectItem {
        void onItemClicked(Message item);
    }
}
