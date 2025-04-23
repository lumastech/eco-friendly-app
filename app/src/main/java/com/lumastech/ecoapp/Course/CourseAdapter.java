package com.lumastech.ecoapp.Course;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> itemList;
    private SelectItem selectItem;

    public CourseAdapter(List<Course> caseList, SelectItem selectItem) {
        this.itemList = caseList;
        this.selectItem = selectItem;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_list, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = itemList.get(position);
        holder.courseName.setText(course.name);
        holder.progressBar.setVisibility(View.GONE);
        holder.itemCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem.onItemClicked(course);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        ProgressBar progressBar;
        View itemCont;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCont = itemView.findViewById(R.id.container);
            courseName = itemView.findViewById(R.id.course_name);
            progressBar = itemView.findViewById(R.id.course_progress);
        }
    }

    public interface SelectItem {
        void onItemClicked(Course item);
    }
}

