package com.lumastech.ecoapp.Course;

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

import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.Models.Lesson;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.ArrayList;
import java.util.List;


public class LessonsFragment extends Fragment {

    private List<Lesson> itemList;
    private LessonAdapter adapter;
    private RecyclerView recyclerView;
    private Context context;
    private Utility utility;
    private NavListener listener;
    public LessonsFragment() {
        // Required empty public constructor
    }


    public static LessonFragment newInstance() {
        return new LessonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lessons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        utility = new Utility(context);
        itemList = Utility.COURSE.lessons;

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new LessonAdapter(itemList, new LessonAdapter.SelectItem() {
            @Override
            public void onItemClicked(Lesson item) {
                if (listener != null){
                    Utility.LESSON = item;
                    Utility.LESSON_COUNT = itemList.indexOf(item)+1;
                    listener.onButtonClicked(R.id.nav_fragment_lesson);
                }
            }
        });

        recyclerView.setAdapter(adapter);
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
}


