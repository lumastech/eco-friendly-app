package com.lumastech.ecoapp.Forum;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> itemList;
    private SelectItem selectItem;

    public PostAdapter(List<Post> caseList, SelectItem selectItem) {
        this.itemList = caseList;
        this.selectItem = selectItem;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = itemList.get(position);
        holder.title.setText(post.title);
        holder.createdAt.setText(post.created_at);
        holder.content.setText(post.content);
        if (position != 0){
            holder.expertCont.setVisibility(View.GONE);
        }
        holder.itemCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem.onItemClicked(post);
            }
        });

        holder.joinExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem.onItemClicked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title, content, createdAt;
        View itemCont, expertCont;
        Button joinExpert;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCont = itemView.findViewById(R.id.container);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            expertCont = itemView.findViewById(R.id.expert_cont);
            createdAt = itemView.findViewById(R.id.date);
            joinExpert = itemView.findViewById(R.id.join_btn);
        }
    }

    public interface SelectItem {
        void onItemClicked(Post item);
        void onItemClicked();
    }
}

