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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class GroupChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db;
    private static String[] Array = {"a"," b"," c"};
    private static String[] Input;

    private static final String TAG = "bastard";
    private static ArrayList<String> chatmessage = new ArrayList<String>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_groupchat, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewgroupchat);

        recyclerView.setHasFixedSize(true);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        db = FirebaseFirestore.getInstance();


        db.collection("Messages")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                GroupChatFragment.chatmessage.add(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        // specify an adapter (see also next example)
        //chatmessage.get(0);
        convertArrays(chatmessage);

        // TODO: 2019/11/01 bikin recyclerview dari list kelas, karena async datanya gak bisa dipake langsung setelah diquery 
        //mAdapter = new GroupChatRecyclerViewAdapter(Input);
        mAdapter = new GroupChatRecyclerViewAdapter(Array);
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    public static String[] convertArrays(ArrayList<String> strings)
    {
        String[] ret = new String[strings.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = strings.get(i);
        }

        return Input;
    }

}
