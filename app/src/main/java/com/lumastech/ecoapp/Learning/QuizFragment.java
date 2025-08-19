package com.lumastech.ecoapp.Learning;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

import com.lumastech.ecoapp.Api;
import com.lumastech.ecoapp.Models.ApiResponse;
import com.lumastech.ecoapp.Models.Lesson;
import com.lumastech.ecoapp.Models.Quiz;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizFragment extends Fragment {

    RadioButton a, b, c, d, e;
    RadioGroup optGroup;
    Button submitBtn;
    List<Quiz> items = new ArrayList<>();
    Quiz quiz = null;
    TextView question, attempts, counter, myAnswerTv, resultTv;
    String myAnswer = null;
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

        items = Utility.LESSON.quizzes;

        if (!items.isEmpty()){
            quiz = items.get(0);
            setView();
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizCounter < (items.size() -1)){
                    myAnswer = "";
                    refreshOpt();
                    quizCounter++;
                    quiz = items.get(quizCounter);
                    setView();
                }else if (nextBtn.getText().toString().equalsIgnoreCase("finish")){
                    completeLeason(Utility.LESSON.id);
//                    popBack(R.id.nav_fragment_quiz);
//                    popBack(R.id.nav_fragment_lesson);
                }
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quizCounter > 0){
                    myAnswer = "";
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
                    myAnswer = "";
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
                if (myAnswer == null){
                    utility.dialog("Please select an Option");
                    return;
                }
                if (quiz.attempts > 0) {
                    quiz.attempts = quiz.attempts -1;
                }
                disableOpt();
                submitBtn.setVisibility(GONE);
                retryBtn.setVisibility(GONE);
                quiz.choice = myChoice;
                if (Objects.equals(quiz.as, myAnswer)){
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
                    myAnswer = a.getText().toString();
                    myChoice = "a";
                }
            }
        });
        b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myAnswer = b.getText().toString();
                    myChoice = "b";
                }
            }
        });
        c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myAnswer = c.getText().toString();
                    myChoice = "c";
                }
            }
        });
        d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myAnswer = d.getText().toString();
                    myChoice = "d";
                }
            }
        });
        e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myAnswer = e.getText().toString();
                    myChoice = "e";
                }
            }
        });
    }

    private void completeLeason(long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog alertDialog;

        alertDialog = builder.create();
        ProgressDialog dialog = ProgressDialog.show(context, "GETTING LESSONS",
                "Please wait...", true);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(context, ConnectivityManager.class);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()){
            Call<ApiResponse<Lesson>> registerResponseCall = Api.apiCall().completeLesson(utility.token(), id);
            registerResponseCall.enqueue(new Callback<ApiResponse<Lesson>>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse<Lesson>> call, @NonNull Response<ApiResponse<Lesson>> response) {
                    dialog.dismiss();
                    utility.checkResponse(response.code(), response.message());
                    if (response.isSuccessful() && response.body() != null) {
                        popBack(R.id.nav_fragment_quiz);
                        popBack(R.id.nav_fragment_lesson);
                        utility.dialog("Lesson Completed! Congratulations!");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse<Lesson>> call, @NonNull Throwable t) {
                    String message = "Error : "+t.getLocalizedMessage();
                    if (Objects.equals(t.getLocalizedMessage(), "End of input at line 1 column 1 path $")){
                        message = "We are having trouble connecting to the internet! Please make sure you have a working Internet connection.";
                    }
                    utility.dialog(message);
                    dialog.dismiss();
                    builder.setMessage(message);
                    alertDialog.show();
                }
            });
        }else{
            dialog.dismiss();
            utility.dialog("There is no Internet connection!");
        }
    }


    public void setView(){
        if (quiz == null) return;
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
        myAnswer = null;
    }

    private void popBack(int id) {
        NavController navController = Navigation.findNavController(requireView());
        navController.popBackStack(id, true);
    }

    private void fetchData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog alertDialog;

        alertDialog = builder.create();
        ProgressDialog dialog = ProgressDialog.show(context, "GETTING LESSONS",
                "Please wait...", true);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(context, ConnectivityManager.class);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()){
            Call<ApiResponse<List<Quiz>>> registerResponseCall = Api.apiCall().getQuizzes(utility.token(), Utility.LESSON.id);
            registerResponseCall.enqueue(new Callback<ApiResponse<List<Quiz>>>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse<List<Quiz>>> call, @NonNull Response<ApiResponse<List<Quiz>>> response) {
                    dialog.dismiss();
                    utility.checkResponse(response.code(), response.message());
                    if (response.isSuccessful() && response.body() != null) {
                        items = response.body().getData();
                        setView();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse<List<Quiz>>> call, @NonNull Throwable t) {
                    String message = "Error : "+t.getLocalizedMessage();
                    if (Objects.equals(t.getLocalizedMessage(), "End of input at line 1 column 1 path $")){
                        message = "We are having trouble connecting to the internet! Please make sure you have a working Internet connection.";
                    }
                    utility.dialog(message);
                    dialog.dismiss();
                    builder.setMessage(message);
                    alertDialog.show();
                }
            });
        }else{
            dialog.dismiss();
            utility.dialog("There is no Internet connection!");
        }
    }


}