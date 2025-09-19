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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.badge.BadgeUtils;
import com.lumastech.ecoapp.Api;
import com.lumastech.ecoapp.Models.ApiResponse;
import com.lumastech.ecoapp.Models.Question;
import com.lumastech.ecoapp.Models.ResponseData;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskFragment extends Fragment {
    private NavListener listener;
    private EditText askEdit;
    private Button submitBtn;
    Context context;
    Utility utility;

    public AskFragment() {
        // Required empty public constructor
    }

    public static AskFragment newInstance(String param1, String param2) {
        return new AskFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ask, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        utility = new Utility(context);
        askEdit = view.findViewById(R.id.et_question);
        submitBtn = view.findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (askEdit.getText().toString().trim().isEmpty()){
                    askEdit.setError("Please Type your question first");
                    return;
                }

                postQuestion();
            }
        });
    }

    private void postQuestion() {
//        if (listener != null){
//            listener.onButtonClicked(R.id.nav_fragment_question);
//        }

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
            Call<ResponseData<Question>> registerResponseCall = Api.apiCall().createQuestion(utility.token());
            registerResponseCall.enqueue(new Callback<ResponseData<Question>>() {
                @Override
                public void onResponse(@NonNull Call<ResponseData<Question>> call, @NonNull Response<ResponseData<Question>> response) {
                    dialog.dismiss();
                    utility.checkResponse(response.code(), response.message());
                    if (response.isSuccessful() && response.body() != null) {
                        popBack();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseData<Question>> call, @NonNull Throwable t) {
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

    private void popBack() {
        NavController navController = Navigation.findNavController(requireView());
        navController.popBackStack(R.id.nav_fragment_ask, true);
    }
}