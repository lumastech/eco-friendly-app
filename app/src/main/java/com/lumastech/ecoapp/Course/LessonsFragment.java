package com.lumastech.ecoapp.Course;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lumastech.ecoapp.Api;
import com.lumastech.ecoapp.Models.ApiResponse;
import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.Models.Lesson;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        fetchData();

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
            Call<ApiResponse<List<Lesson>>> registerResponseCall = Api.apiCall().getLessons(utility.token(), Utility.COURSE.id);
            registerResponseCall.enqueue(new Callback<ApiResponse<List<Lesson>>>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse<List<Lesson>>> call, @NonNull Response<ApiResponse<List<Lesson>>> response) {
                    dialog.dismiss();
                    utility.checkResponse(response.code(), response.message());
                    if (response.isSuccessful() && response.body() != null) {
                        itemList = response.body().getData();
                        setViews();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse<List<Lesson>>> call, @NonNull Throwable t) {
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

    private void setViews() {
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

    public void getQuiz(Lesson item){

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


