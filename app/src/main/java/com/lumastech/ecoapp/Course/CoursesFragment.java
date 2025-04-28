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
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.ArrayList;
import java.util.List;


public class CoursesFragment extends Fragment {

    private List<Course> itemList;
    private CourseAdapter adapter;
    private RecyclerView recyclerView;
    private Context context;
    private Utility utility;
    private NavListener listener;
    public CoursesFragment() {
        // Required empty public constructor
    }

    public static CoursesFragment newInstance(String param1, String param2) {
        return new CoursesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        utility = new Utility(context);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new CourseAdapter(getDummyData(), new CourseAdapter.SelectItem() {
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

    public ArrayList<Course> getDummyData(){
        ArrayList<Course> list = new ArrayList<>();
        list.add(new Course( 1, "Mathematics", "An introduction to mathematical concepts.", "John Doe", "Active" ));
        list.add(new Course( 2, "Physics", "Fundamentals of physics", "Jane Smith", "Active" ));
        list.add(new Course(3, "Chemistry", "Core concepts in chemistry", "Bob Johnson", "Inactive"));
        list.add(new Course(4, "Biology", "Introduction to biological systems", "Alice Brown", "Active"));
        list.add(new Course(5, "History", "World history overview", "Eve Davis", "Inactive"));
        return  list;
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