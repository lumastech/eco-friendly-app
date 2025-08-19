package com.lumastech.ecoapp.Learning;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.Models.Question;
import com.lumastech.ecoapp.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> itemList;
    private SelectItem selectItem;

    public QuestionAdapter(List<Question> caseList, SelectItem selectItem) {
        this.itemList = caseList;
        this.selectItem = selectItem;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qna_list_item, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.editCont.setVisibility(GONE);
//        holder.editBtn.setVisibility(GONE);
        Question item = itemList.get(position);
        holder.question.setText(item.question);
        holder.createdAt.setText(item.created_at);
        holder.username.setText(item.user.getName());
        if (item.answer != null){
            holder.answer.setText(item.answer);
            holder.answerEdit.setText(item.answer);
            holder.updatedAt.setText(item.updated_at);
        }
        holder.itemCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem.onItemClicked(item);
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.editBtn.getText().toString().equals("Edit")){
                    holder.editCont.setVisibility(VISIBLE);
                    holder.editBtn.setText("Close");
                }else {
                    holder.editCont.setVisibility(GONE);
                    holder.editBtn.setText("Edit");
                }
            }
        });

        holder.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.answerEdit.getText().toString().trim().isEmpty()){
                    holder.answerEdit.setError("Please Type Your answer");
                    return;
                }

                item.answer = holder.answerEdit.getText().toString().trim();
                selectItem.onItemClicked(itemList.get(holder.getAbsoluteAdapterPosition()), item);
                holder.editCont.setVisibility(GONE);
                holder.editBtn.setText("Edit");
            }
        });

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.editCont.setVisibility(GONE);
                holder.editBtn.setText("Edit");
                holder.answerEdit.setText(item.answer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView question, answer, createdAt, updatedAt, username, editBtn;
        View itemCont, editCont;
        Button submitBtn, cancelBtn;
        EditText answerEdit;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCont = itemView.findViewById(R.id.container);
            question = itemView.findViewById(R.id.question);
            username = itemView.findViewById(R.id.username);
            answer = itemView.findViewById(R.id.answer);
            createdAt = itemView.findViewById(R.id.created_at);
            updatedAt = itemView.findViewById(R.id.updated_at);

            editCont = itemView.findViewById(R.id.input_cont);
            answerEdit = itemView.findViewById(R.id.answer_edit);
            editBtn = itemView.findViewById(R.id.edit_btn);
            submitBtn = itemView.findViewById(R.id.submit_btn);
            cancelBtn = itemView.findViewById(R.id.cancel_btn);
        }
    }

    public interface SelectItem {
        void onItemClicked(Question item);
        void onItemClicked(Question item, Question update);
    }
}

