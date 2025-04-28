package com.lumastech.ecoapp.Forum;

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

import com.lumastech.ecoapp.Course.CourseAdapter;
import com.lumastech.ecoapp.Models.Comment;
import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.ArrayList;
import java.util.List;

public class ForumsFragment extends Fragment {

    private List<Course> itemList;
    private PostAdapter adapter;
    private RecyclerView recyclerView;
    private Context context;
    private Utility utility;
    private NavListener listener;
    public ForumsFragment() {
        // Required empty public constructor
    }

    public static ForumsFragment newInstance(String param1, String param2) {
        return new ForumsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        utility = new Utility(context);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new PostAdapter(getDummyData(), new PostAdapter.SelectItem() {
            @Override
            public void onItemClicked(Post item) {
                if (listener != null){
                    Utility.POST = item;
                    listener.onButtonClicked(R.id.nav_fragment_forum);
                };
            }

            @Override
            public void onItemClicked() {
                listener.onButtonClicked(R.id.nav_fragment_question);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public ArrayList<Post> getDummyData(){
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(){{
            id=1;
            user_id=100;
            comment="The speed of the current warming phenomenon is far faster any previous temperature change";
            created_at = "27-04-2025 11:55";
            username = "John Vans";
            status = "active";
        }});

        comments.add(new Comment(){{
            id=1;
            user_id=100;
            comment="Naturally caused CO2 fluctuations take well over 5000 years – rather than the last 150 years to change";
            created_at = "27-04-2025 11:55";
            username = "Mwanza Musanda";
            status = "active";
        }});

        comments.add(new Comment(){{
            id=1;
            user_id=100;
            comment="The speed of the current warming phenomenon is far faster any previous temperature change";
            created_at = "27-04-2025 11:55";
            username = "Malleck Phiri";
            status = "active";
        }});

        ArrayList<Post> list = new ArrayList<>();
        list.add(new Post( 1, 100, "Mathematics", "", "An introduction to mathematical concepts.", "27-04-2025 11:55", comments));
        list.add(new Post( 2, 100, "Physics", "", "Fundamentals of physics", "27-04-2025 11:55", comments));
        list.add(new Post(3, 100, "Chemistry", "", "Core concepts in chemistry", "27-04-2025 11:55", comments));
        list.add(new Post(4, 100, "Biology", "", "Introduction to biological systems", "27-04-2025 11:55", comments));
        list.add(new Post(5, 100, "History", "", "World history overview", "27-04-2025 11:55", comments));
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