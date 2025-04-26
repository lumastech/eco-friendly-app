package com.lumastech.ecoapp.Course;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lumastech.ecoapp.Models.Lesson;
import com.lumastech.ecoapp.R;

import java.util.List;

public class LessonAdapter  extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {
    private List<Lesson> itemList;
    private LessonAdapter.SelectItem selectItem;

    public LessonAdapter(List<Lesson> caseList, LessonAdapter.SelectItem selectItem) {
        this.itemList = caseList;
        this.selectItem = selectItem;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item_list, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.LessonViewHolder holder, int position) {
        Lesson lesson = itemList.get(position);
        holder.lessonTitle.setText(lesson.title);
        holder.counter.setText("LESSON ".concat(String.valueOf(position+1)));
        holder.points.setText(String.valueOf(lesson.points));
        holder.startBtn.setVisibility(View.GONE);
        holder.itemCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem.onItemClicked(lesson);
            }
        });

        holder.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        TextView lessonTitle, counter, points, description;
        View itemCont;
        ImageView lockIcon;
        Button startBtn;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCont = itemView.findViewById(R.id.container);
            lessonTitle = itemView.findViewById(R.id.lesson_title);
            counter = itemView.findViewById(R.id.lesson_counter);
            points = itemView.findViewById(R.id.lesson_points);
            lockIcon = itemView.findViewById(R.id.lock_icon);
            startBtn = itemView.findViewById(R.id.start_lesson_btn);
            description = itemView.findViewById(R.id.lesson_description);
        }
    }

    public interface SelectItem {
        void onItemClicked(Lesson item);
    }
}
