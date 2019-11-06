package com.example.krucils;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class GroupChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String TAG = "bastard";

    private static FirestoreRecyclerAdapter adapter;





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_groupchat, container, false);



        Query query = FirebaseFirestore.getInstance()
                .collection("Messages").document("groupchat1")
                .collection("messages")
                .limit(50);

        FirestoreRecyclerOptions<GroupChat> options = new FirestoreRecyclerOptions.Builder<GroupChat>()
                .setQuery(query, GroupChat.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<GroupChat, GroupChatHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  GroupChatHolder  holder, int position, @NonNull GroupChat model) {
                holder.setText("Nama = "+ model.getName(), "Message = " + model.getMessage());
            }

            @NonNull
            @Override
            public  GroupChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_row_item, parent, false);
                return new  GroupChatHolder(view);
            }
        };
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewchat);

        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private class GroupChatHolder extends RecyclerView.ViewHolder {
        private View view;

        GroupChatHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setText(String setName, String setMessage) {
            TextView nama = view.findViewById(R.id.chatnama);
            TextView message = view.findViewById(R.id.chatmessage);
            nama.setText(setName);
            message.setText(setMessage);
        }
    }
}
