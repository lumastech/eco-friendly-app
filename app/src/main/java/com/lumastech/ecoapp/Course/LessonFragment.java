package com.lumastech.ecoapp.Course;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.Models.Lesson;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;


public class LessonFragment extends Fragment {
    private Context context;
    private Utility utility;
    private NavListener listener;
    TextView lessonCounter, title, content;
    ImageView imageView;
    Lesson lesson;
    Button takeTestBtn;
    public LessonFragment() {
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
        return inflater.inflate(R.layout.fragment_lesson, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        utility = new Utility(context);
        lessonCounter = view.findViewById(R.id.lesson_counter);
        title = view.findViewById(R.id.lesson_title);
        content = view.findViewById(R.id.content);
        imageView = view.findViewById(R.id.imageView);
        lesson = Utility.LESSON;
        takeTestBtn = view.findViewById(R.id.take_test_btn);

        if(lesson != null) {
            title.setText(lesson.title);
            lessonCounter.setText("LESSON ");
            content.setText("Content");
        }

        takeTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onButtonClicked(R.id.nav_fragment_quiz);
                }
            }
        });
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


