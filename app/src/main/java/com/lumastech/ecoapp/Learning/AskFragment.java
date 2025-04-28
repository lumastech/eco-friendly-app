package com.lumastech.ecoapp.Learning;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.badge.BadgeUtils;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;

public class AskFragment extends Fragment {
    private NavListener listener;
    private EditText askEdit;
    private Button submitBtn;

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
        if (listener != null){
            listener.onButtonClicked(R.id.nav_fragment_question);
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
}