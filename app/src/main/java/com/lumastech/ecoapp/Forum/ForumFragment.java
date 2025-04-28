package com.lumastech.ecoapp.Forum;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.lumastech.ecoapp.Models.Comment;
import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

public class ForumFragment extends Fragment {
    Context context;
    private TextView title, content;
    private ImageView imageView;
    private Post item;
    private RecyclerView recyclerView;
    private CommentAdapter commentAdapter;
    private Button commentBtn;
    private EditText commentEt;

    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance(String param1, String param2) {
        return new ForumFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forum, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        title = view.findViewById(R.id.post_title);
        content = view.findViewById(R.id.post_content);
        imageView = view.findViewById(R.id.imageView);
        recyclerView = view.findViewById(R.id.recyclerView);
        commentBtn = view.findViewById(R.id.comment_btn);
        commentEt = view.findViewById(R.id.comment_edit);

        item = Utility.POST;

        if (item != null){
            title.setText(item.title);
            content.setText(item.content);
        }

        commentAdapter = new CommentAdapter(item.comments, new CommentAdapter.SelectItem() {
            @Override
            public void onItemClicked(Comment item) {

            }
        });

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEt.getText().toString().trim();
                if (comment.isEmpty()){
                    commentEt.setError("Please Type Your comment");
                    return;
                }
                postComment(comment);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(commentAdapter);
    }

    private void postComment(String comment) {
        // post comment
        Toast.makeText(context, "post submitted", Toast.LENGTH_SHORT).show();
        commentEt.setText("");
    }
}