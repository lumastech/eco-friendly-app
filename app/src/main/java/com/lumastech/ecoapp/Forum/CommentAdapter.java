package com.lumastech.ecoapp.Forum;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lumastech.ecoapp.Models.Comment;
import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> itemList;
    private SelectItem selectItem;

    public CommentAdapter(List<Comment> caseList, SelectItem selectItem) {
        this.itemList = caseList;
        this.selectItem = selectItem;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment item = itemList.get(position);
        holder.username.setText(item.username);
        holder.createdAt.setText(item.created_at);
        holder.content.setText(item.comment);
        holder.itemCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem.onItemClicked(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView username, content, createdAt;
        View itemCont;
        ImageView imageView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCont = itemView.findViewById(R.id.container);
            username = itemView.findViewById(R.id.username);
            content = itemView.findViewById(R.id.content);
            createdAt = itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public interface SelectItem {
        void onItemClicked(Comment item);
    }
}

