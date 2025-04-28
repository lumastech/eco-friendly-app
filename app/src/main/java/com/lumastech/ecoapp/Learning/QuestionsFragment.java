package com.lumastech.ecoapp.Learning;

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
import android.widget.Button;

import com.lumastech.ecoapp.Forum.PostAdapter;
import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.Models.Question;
import com.lumastech.ecoapp.Models.User;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

        itemList = getDummyData();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        askBtn = view.findViewById(R.id.ask_btn);

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

        askBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onButtonClicked(R.id.nav_fragment_ask);
                }
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