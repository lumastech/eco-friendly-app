package com.lumastech.ecoapp.Learning;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lumastech.ecoapp.Models.Quiz;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class QuizFragment extends Fragment {

    RadioButton a, b, c, d, e;
    RadioGroup optGroup;
    Button submitBtn;
    List<Quiz> items;
    Quiz quiz = null;
    TextView question, attempts, counter, myAnswerTv, resultTv;
    String myChoice = null;
    View resultCont, retryBtn;
    Button previousBtn, nextBtn;
    Utility utility;
    Context context;

    int quizCounter = 0, attemptCount=1;
    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance() {
        return new QuizFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        utility = new Utility(context);
        question = view.findViewById(R.id.qs);
        a = view.findViewById(R.id.opt_a);
        b = view.findViewById(R.id.opt_b);
        c = view.findViewById(R.id.opt_c);
        d = view.findViewById(R.id.opt_d);
        e = view.findViewById(R.id.opt_e);
        optGroup = view.findViewById(R.id.optionGroup);
        submitBtn = view.findViewById(R.id.submit_btn);
        counter = view.findViewById(R.id.counter);
        attempts = view.findViewById(R.id.attempts);

        resultCont = view.findViewById(R.id.result_cont);
        resultTv = view.findViewById(R.id.result);
        myAnswerTv = view.findViewById(R.id.my_choice);
        retryBtn = view.findViewById(R.id.retry_btn);
        previousBtn = view.findViewById(R.id.previous_btn);
        nextBtn = view.findViewById(R.id.next_btn);
        resultCont.setVisibility(GONE);

        items = getDummy();

        if (!items.isEmpty()){
            quiz = items.get(0);
            setView();
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizCounter < (items.size() -1)){
                    refreshOpt();
                    quizCounter++;
                    quiz = items.get(quizCounter);
                    setView();
                }else if (nextBtn.getText().toString().equalsIgnoreCase("finish")){
                    popBack(R.id.nav_fragment_quiz);
                    popBack(R.id.nav_fragment_lesson);
                }
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizCounter > 0){
                    refreshOpt();
                    quizCounter--;
                    quiz = items.get(quizCounter);
                    setView();
                }
            }
        });

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (items.get(quizCounter).attempts > 0){
                    resultCont.setVisibility(GONE);
                    refreshOpt();
                    submitBtn.setVisibility(VISIBLE);
                    enableOpt();
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myChoice == null){
                    utility.generalDialog("Please select an Option");
                    return;
                }
                if (quiz.attempts > 0) {
                    quiz.attempts = quiz.attempts -1;
                }
                disableOpt();
                submitBtn.setVisibility(GONE);
                retryBtn.setVisibility(GONE);
                if (Objects.equals(quiz.as, myChoice)){
                    resultTv.setText("Your Answer is correct");
                }else {
                    resultTv.setText("Your Answer is Wrong!");
                    if (quiz.attempts > 0) {
                        retryBtn.setVisibility(VISIBLE);
                    }
                }

                items.get(items.indexOf(quiz)).attempts = quiz.attempts;
                items.get(items.indexOf(quiz)).choice = quiz.choice;
                attempts.setText("Attempts Remaining ".concat(String.valueOf(quiz.attempts)));
                updateOptView();
                resultCont.setVisibility(VISIBLE);
            }
        });

        a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myChoice = a.getText().toString();
                    quiz.choice = "a";
                }
            }
        });
        b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myChoice = b.getText().toString();
                    quiz.choice = "b";
                }
            }
        });
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myChoice = c.getText().toString();
                    quiz.choice = "c";
                }
            }
        });
        d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myChoice = d.getText().toString();
                    quiz.choice = "d";
                }
            }
        });
        e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myChoice = e.getText().toString();
                    quiz.choice = "e";
                }
            }
        });
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


    public void setView(){
        counter.setText(String.valueOf(items.indexOf(quiz)+1).concat("/").concat(String.valueOf(items.size())));
        question.setText(String.valueOf(quizCounter+1).concat(". ").concat(quiz.qs));
        attempts.setText("Attempts Remaining ".concat(String.valueOf(quiz.attempts)));
        a.setText(quiz.a);
        b.setText(quiz.b);

        if (quiz.c != null){
            c.setText(quiz.c);
            c.setVisibility(VISIBLE);
        }else {
            c.setVisibility(GONE);
        }

        if (quiz.d != null){
            d.setText(quiz.d);
            d.setVisibility(VISIBLE);
        }else {
            d.setVisibility(GONE);
        }

        if (quiz.e != null){
            e.setText(quiz.e);
            e.setVisibility(VISIBLE);
        }else {
            e.setVisibility(GONE);
        }
        updateOptView();

        if (quiz.attempts == 0){
            disableOpt();
            submitBtn.setVisibility(GONE);
            retryBtn.setVisibility(GONE);
            resultCont.setVisibility(VISIBLE);
        }else if (!Objects.equals(quiz.status, "Pass")){
            resultCont.setVisibility(GONE);
            submitBtn.setVisibility(VISIBLE);
            enableOpt();
        }

        if (quizCounter > 0){
            previousBtn.setEnabled(true);
        }

        if (quizCounter < (items.size() -1)){
            nextBtn.setEnabled(true);
        }else {
            nextBtn.setText("Finish");
        }
    }

    private void updateOptView(){
        if (Objects.equals(quiz.choice, "a")){
            a.setChecked(true);
            if (Objects.equals(quiz.a, quiz.as)){
                a.setTextColor(Color.GREEN);
            }else {
                a.setTextColor(Color.RED);
            }
        }else if (Objects.equals(quiz.choice, "b")){
            b.setChecked(true);
            if (Objects.equals(quiz.b, quiz.as)){
                b.setTextColor(Color.GREEN);
            }else {
                b.setTextColor(Color.RED);
            }
        }else if (Objects.equals(quiz.choice, "c")){
            c.setChecked(true);
            if (Objects.equals(quiz.c, quiz.as)){
                c.setTextColor(Color.GREEN);
            }else {
                c.setTextColor(Color.RED);
            }
        }else if (Objects.equals(quiz.choice, "d")){
            d.setChecked(true);
            if (Objects.equals(quiz.d, quiz.as)){
                d.setTextColor(Color.GREEN);
            }else {
                d.setTextColor(Color.RED);
            }
        }else if (Objects.equals(quiz.choice, "e")){
            e.setChecked(true);
            if (Objects.equals(quiz.e, quiz.as)){
                e.setTextColor(Color.GREEN);
            }else {
                e.setTextColor(Color.RED);
            }
        }
    }

    private void disableOpt(){
        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        d.setEnabled(false);
        e.setEnabled(false);
    }

    private void enableOpt(){
        a.setEnabled(true);
        b.setEnabled(true);
        c.setEnabled(true);
        d.setEnabled(true);
        e.setEnabled(true);
    }

    private void refreshOpt(){
        a.setChecked(false);
        b.setChecked(false);
        c.setChecked(false);
        d.setChecked(false);
        e.setChecked(false);

        previousBtn.setEnabled(false);
        nextBtn.setText("Next");

        // reset text color
        a.setTextColor(Color.parseColor("#000000"));
        b.setTextColor(Color.parseColor("#000000"));
        c.setTextColor(Color.parseColor("#000000"));
        d.setTextColor(Color.parseColor("#000000"));
        e.setTextColor(Color.parseColor("#000000"));
        myChoice = null;
    }

    private void popBack(int id) {
        NavController navController = Navigation.findNavController(requireView());
        navController.popBackStack(id, true);
    }


}