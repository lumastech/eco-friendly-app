package com.lumastech.ecoapp.Chat;

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

import com.lumastech.ecoapp.Models.Chat;
import com.lumastech.ecoapp.Models.Course;
import com.lumastech.ecoapp.Models.Message;
import com.lumastech.ecoapp.NavListener;
import com.lumastech.ecoapp.R;
import com.lumastech.ecoapp.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChatsFragment extends Fragment {

    private List<Course> itemList;
    private ChatsAdapter adapter;
    private RecyclerView recyclerView;
    private Context context;
    private Utility utility;
    private NavListener listener;

    public ChatsFragment() {
        // Required empty public constructor
    }

    public static ChatsFragment newInstance(String param1, String param2) {
        return new ChatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        utility = new Utility(context);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new ChatsAdapter(generateDummyChats(), new ChatsAdapter.SelectItem() {
            @Override
            public void onItemClicked(Chat item) {
                if (listener != null){
                    Utility.CHAT = item;
                    listener.onButtonClicked(R.id.nav_fragment_chat);
                };
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public static List<Chat> generateDummyChats() {
        List<Chat> chats = new ArrayList<>();

        chats.add(new Chat() {{
            id = 1;
            user_id = 101;
            name = "Alice";
            image_url = "https://example.com/images/alice.png";
            messages = Arrays.asList(
                    new Message() {{
                        created_at = "2025-04-25T10:15:00";
                        message = "Hey there!";
                        sender_id = "101";
                        isOpen = true;
                    }},
                    new Message() {{
                        created_at = "2025-04-25T10:17:00";
                        message = "How are you?";
                        sender_id = "100";
                        isOpen = false;
                    }}
            );
        }});

        chats.add(new Chat() {{
            id = 2;
            user_id = 102;
            name = "Bob";
            image_url = "https://example.com/images/bob.png";
            messages = Arrays.asList(
                    new Message() {{
                        created_at = "2025-04-25T11:00:00";
                        message = "Hello!";
                        sender_id = "102";
                        isOpen = true;
                    }},
                    new Message() {{
                        created_at = "2025-04-25T11:05:00";
                        message = "Are we still meeting today?";
                        sender_id = "100";
                        isOpen = true;
                    }}
            );
        }});

        chats.add(new Chat() {{
            id = 3;
            user_id = 103;
            name = "Charlie";
            image_url = "https://example.com/images/charlie.png";
            messages = Arrays.asList(
                    new Message() {{
                        created_at = "2025-04-24T09:30:00";
                        message = "Good morning!";
                        isOpen = false;
                    }},
                    new Message() {{
                        created_at = "2025-04-24T09:30:00";
                        message = "Good morning, and how are you?";
                        sender_id = "100";
                        isOpen = false;
                    }}
            );
        }});

        return chats;
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