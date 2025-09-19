package com.lumastech.ecoapp.Forum;

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
import com.lumastech.ecoapp.Course.CourseAdapter;
import com.lumastech.ecoapp.Models.ApiResponse;
import com.lumastech.ecoapp.Models.Comment;
import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.Models.Post;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForumsFragment extends Fragment {

    private List<Post> itemList;
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
        fetchCourses();
    }


    private void fetchCourses() {
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
            Call<ApiResponse<List<Post>>> registerResponseCall = Api.apiCall().getPosts(utility.token());
            registerResponseCall.enqueue(new Callback<ApiResponse<List<Post>>>() {
                @Override
                public void onResponse(@NonNull Call<ApiResponse<List<Post>>> call, @NonNull Response<ApiResponse<List<Post>>> response) {
                    dialog.dismiss();
                    utility.checkResponse(response.code(), response.message());
                    if (response.isSuccessful() && response.body() != null) {
                        itemList = response.body().getData();
                        setViews();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ApiResponse<List<Post>>> call, @NonNull Throwable t) {
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
        adapter = new PostAdapter(itemList, new PostAdapter.SelectItem() {
            @Override
            public void onItemClicked(Post item) {
                if (listener != null){
                    Utility.POST = item;
                    listener.onButtonClicked(R.id.nav_fragment_lessons);
                };
            }

            @Override
            public void onItemClicked() {
                listener.onButtonClicked(R.id.nav_fragment_question);
            }
        });

        adapter.notifyDataSetChanged();

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