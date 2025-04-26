package com.lumastech.ecoapp.Chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lumastech.ecoapp.Models.Chat;
import com.lumastech.ecoapp.R;

import java.util.List;

public class ChatsAdapter  extends RecyclerView.Adapter<ChatsAdapter.ChatsViewHolder> {
    private List<Chat> itemList;
    private ChatsAdapter.SelectItem selectItem;

    public ChatsAdapter(List<Chat> caseList, ChatsAdapter.SelectItem selectItem) {
        this.itemList = caseList;
        this.selectItem = selectItem;
    }

    @NonNull
    @Override
    public ChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        return new ChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.ChatsViewHolder holder, int position) {
        Chat chat = itemList.get(position);
        holder.username.setText(chat.name);
        if (!chat.messages.isEmpty()){
            holder.last_message.setText(chat.messages.get(0).message);
        }

        holder.itemCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem.onItemClicked(chat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ChatsViewHolder extends RecyclerView.ViewHolder {
        TextView username, last_message;
        View itemCont;
        ImageView dp;

        public ChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCont = itemView.findViewById(R.id.container);
            username = itemView.findViewById(R.id.username);
            last_message = itemView.findViewById(R.id.last_message);
            dp = itemView.findViewById(R.id.imageView);
        }
    }

    public interface SelectItem {
        void onItemClicked(Chat item);
    }
}
