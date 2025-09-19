package com.lumastech.ecoapp.Learning;

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
import android.widget.Button;

import com.lumastech.ecoapp.Api;
import com.lumastech.ecoapp.Models.ApiResponse;
import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.Models.Question;
import com.lumastech.ecoapp.Models.User;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuestionsFragment extends Fragment {

    private List<Question> itemList;
    private QuestionAdapter adapter;
    private RecyclerView recyclerView;
    private Context context;
    private Utility utility;
    private Button askBtn;
    private NavListener listener;
    public QuestionsFragment() {
        // Required empty public constructor
    }

    public static QuestionsFragment newInstance(String param1, String param2) {
        return new QuestionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        utility = new Utility(context);
        recyclerView = view.findViewById(R.id.recyclerView);
        fetchData();
        askBtn = view.findViewById(R.id.ask_btn);

        askBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onButtonClicked(R.id.nav_fragment_ask);
                }
            }
        });
    }


    private void fetchData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        AlertDialog alertDialog;

        alertDialog = builder.create();
        ProgressDialog dialog = ProgressDialog.show(context, "GETTING POSTS",
                "Please wait...", true);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(context, ConnectivityManager.class);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()){
            Call<ApiResponse<List<Question>>> registerResponseCall = Api.apiCall().getQuestions(utility.token());
            registerResponseCall.enqueue(new Callback<ApiResponse<List<Question>>>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse<List<Question>>> call, @NonNull Response<ApiResponse<List<Question>>> response) {
                    dialog.dismiss();
                    utility.checkResponse(response.code(), response.message());
                    if (response.isSuccessful() && response.body() != null) {
                        itemList = response.body().getData();
                        setViews();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse<List<Question>>> call, @NonNull Throwable t) {
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

    private void setViews(){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new QuestionAdapter(itemList, new QuestionAdapter.SelectItem() {
            @Override
            public void onItemClicked(Question item) {

            }

            @Override
            public void onItemClicked(Question item, Question update) {
                itemList.get(itemList.indexOf(item)).answer = update.answer;
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private List<Question> getDummyData() {
        List<Question> list = Arrays.asList(
                new Question(){{
                    id = 1;
                    user_id = 102;
                    question = "What’s the science?";
                    answer = "The speed of the current warming phenomenon is far faster any previous temperature change";
                    user = new User() {{
                        id = 102;
                        name = "Lumas Mulo";
                    }};
                    created_at = "21/04/2025 14:00";
                    updated_at = "21/04/2025 17:00";
                }},new Question(){{
                    id = 2;
                    user_id = 103;
                    question = "What’s the science?";
                    answer = "The speed of the current warming phenomenon is far faster any previous temperature change";
                    user = new User() {{
                        id = 103;
                        name = "Mulenga Mapalo";
                    }};
                    created_at = "21/04/2025 14:00";
                    updated_at = "21/04/2025 17:00";
                }},new Question(){{
                    id = 3;
                    user_id = 104;
                    question = "What’s the science?";
                    answer = "The speed of the current warming phenomenon is far faster any previous temperature change";
                    user = new User() {{
                        id = 104;
                        name = "Peter Malley";
                    }};
                    created_at = "21/04/2025 14:00";
                    updated_at = "21/04/2025 17:00";
                }},new Question(){{
                    id = 4;
                    user_id = 101;
                    question = "What’s the science?";
                    answer = "The speed of the current warming phenomenon is far faster any previous temperature change";
                    user = new User() {{
                        id = 101;
                        name = "James Daka";
                    }};
                    created_at = "21/04/2025 14:00";
                    updated_at = "21/04/2025 17:00";
                }}
        );

        return list;
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