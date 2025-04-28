package com.lumastech.ecoapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lumastech.ecoapp.Course.CourseAdapter;
import com.lumastech.ecoapp.Course.CoursesFragment;
import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;
import com.lumastech.ecoapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private View viewMoreCourses;
    private Button continueLearning, forumBtn;
    private NavListener listener;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewMoreCourses = view.findViewById(R.id.view_more_courses);
        continueLearning = view.findViewById(R.id.continue_learning);
        forumBtn = view.findViewById(R.id.forums_btn);

        viewMoreCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onButtonClicked(R.id.nav_fragment_courses);
                }
            }
        });
        continueLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onButtonClicked(R.id.nav_fragment_courses);
                }
            }
        });

        forumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onButtonClicked(R.id.nav_fragment_forums);
                }
            }
        });





        setModules(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof NavListener) {
            listener = (NavListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void setModules(View view){
        CoursesFragment coursesFragment = new CoursesFragment();
        RecyclerView recyclerView = view.findViewById(R.id.module_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CourseAdapter adapter = new CourseAdapter(coursesFragment.getDummyData(), new CourseAdapter.SelectItem() {
            @Override
            public void onItemClicked(Course item) {
                if (listener != null){
                    Utility.COURSE = item;
                    listener.onButtonClicked(R.id.nav_fragment_lessons);
                };
            }
        });

        recyclerView.setAdapter(adapter);
    }
}