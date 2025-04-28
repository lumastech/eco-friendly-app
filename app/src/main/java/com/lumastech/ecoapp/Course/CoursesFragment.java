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
import com.lumastech.ecoapp.Models.Quiz;
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
        // lesson list
        ArrayList<Lesson> lessonList = new ArrayList<>();
        lessonList.add(new Lesson( 1, "Climate Change 101: The Basics", 10, "An introduction to climate change.", "Climate change is a large-scale, long-term shift in the Earth's weather patterns or average temperatures. Climate systems have varied naturally over the years; changes occurred in cycles over tens of thousands of years resulting in the Ice Ages and interglacial warming periods.\n\nWhat’s the science?\n" +
                "\n" +
                "The Earth is constantly bombarded by radiation from the sun. About 30% of this is reflected back into space by clouds and ice. The rest of this heat radiation is absorbed into the land, oceans and atmosphere. As the latter heat up, they release heat back into space. Greenhouse gases in the atmosphere absorb and re-emit radiation, trapping warmth. The balance of incoming and outgoing heat radiation determines global temperatures.", getDummy()));
        lessonList.add(new Lesson( 1, "Climate Change 101: The Basics", 10, "An introduction to climate change.", "Climate change is a large-scale, long-term shift in the Earth's weather patterns or average temperatures. Climate systems have varied naturally over the years; changes occurred in cycles over tens of thousands of years resulting in the Ice Ages and interglacial warming periods.\n\nWhat’s the science?\n" +
                "\n" +
                "The Earth is constantly bombarded by radiation from the sun. About 30% of this is reflected back into space by clouds and ice. The rest of this heat radiation is absorbed into the land, oceans and atmosphere. As the latter heat up, they release heat back into space. Greenhouse gases in the atmosphere absorb and re-emit radiation, trapping warmth. The balance of incoming and outgoing heat radiation determines global temperatures.", getDummy()));
        lessonList.add(new Lesson( 1, "Climate Change 101: The Basics", 10, "An introduction to climate change.", "Climate change is a large-scale, long-term shift in the Earth's weather patterns or average temperatures. Climate systems have varied naturally over the years; changes occurred in cycles over tens of thousands of years resulting in the Ice Ages and interglacial warming periods.\n\nWhat’s the science?\n" +
                "\n" +
                "The Earth is constantly bombarded by radiation from the sun. About 30% of this is reflected back into space by clouds and ice. The rest of this heat radiation is absorbed into the land, oceans and atmosphere. As the latter heat up, they release heat back into space. Greenhouse gases in the atmosphere absorb and re-emit radiation, trapping warmth. The balance of incoming and outgoing heat radiation determines global temperatures.", getDummy()));
        lessonList.add(new Lesson( 1, "Climate Change 101: The Basics", 10, "An introduction to climate change.", "Climate change is a large-scale, long-term shift in the Earth's weather patterns or average temperatures. Climate systems have varied naturally over the years; changes occurred in cycles over tens of thousands of years resulting in the Ice Ages and interglacial warming periods.\n\nWhat’s the science?\n" +
                "\n" +
                "The Earth is constantly bombarded by radiation from the sun. About 30% of this is reflected back into space by clouds and ice. The rest of this heat radiation is absorbed into the land, oceans and atmosphere. As the latter heat up, they release heat back into space. Greenhouse gases in the atmosphere absorb and re-emit radiation, trapping warmth. The balance of incoming and outgoing heat radiation determines global temperatures.", getDummy()));
        lessonList.add(new Lesson( 1, "Climate Change 101: The Basics", 10, "An introduction to climate change.", "Climate change is a large-scale, long-term shift in the Earth's weather patterns or average temperatures. Climate systems have varied naturally over the years; changes occurred in cycles over tens of thousands of years resulting in the Ice Ages and interglacial warming periods.\n\nWhat’s the science?\n" +
                "\n" +
                "The Earth is constantly bombarded by radiation from the sun. About 30% of this is reflected back into space by clouds and ice. The rest of this heat radiation is absorbed into the land, oceans and atmosphere. As the latter heat up, they release heat back into space. Greenhouse gases in the atmosphere absorb and re-emit radiation, trapping warmth. The balance of incoming and outgoing heat radiation determines global temperatures.", getDummy()));


        list.add(new Course( 1, "Mathematics", "An introduction to mathematical concepts.", "John Doe", "Active", lessonList));
        list.add(new Course( 2, "Physics", "Fundamentals of physics", "Jane Smith", "Active", lessonList));
        list.add(new Course(3, "Chemistry", "Core concepts in chemistry", "Bob Johnson", "Inactive", lessonList));
        list.add(new Course(4, "Biology", "Introduction to biological systems", "Alice Brown", "Active", lessonList));
        list.add(new Course(5, "History", "World history overview", "Eve Davis", "Inactive", lessonList));
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

    public List<Quiz> getDummy(){
        ArrayList<Quiz> list = new ArrayList<>();
        String qs = "What’s the science?";
        list.add(new Quiz(1, 1, "answer 1", "answer 2", "answer 3", "answer 4", "answer 5", "answer 2", qs));
        list.add(new Quiz(2, 1, "answer 1", "answer 2", "answer 3", "answer 4", "answer 5", "answer 4", qs));
        list.add(new Quiz(3, 1, "answer 1", "answer 2", "answer 3", "answer 4", "answer 5", "answer 2", qs));
        list.add(new Quiz(4, 1, "answer 1", "answer 2", "answer 3", "answer 4", "answer 5", "answer 1", qs));
        list.add(new Quiz(5, 1, "answer 1", "answer 2", "answer 3", "answer 4", "answer 5", "answer 3", qs));
        return list;
    }
}