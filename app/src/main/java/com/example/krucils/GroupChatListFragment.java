package com.example.krucils;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class GroupChatListFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private static FirestoreRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;


    //todo create onclick recyclerview, pake position aja atau judul atau uid
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_groupchatlist, container, false);
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        //todo sekarang bikinnya pake FirestoreRecyclerAdapter dulu, tapi mungkin lebih  baik pake paging adapter ?, atau pake normal recyclerview ?

        //todo cari cara lain buat manggil list groupchatnya
        Query query = db
                .collection("Messages")
                .whereEqualTo("aksesUserUID",user.getUid());

        FirestoreRecyclerOptions<GroupChatList> options = new FirestoreRecyclerOptions.Builder<GroupChatList>()
                .setQuery(query, GroupChatList.class)
                .build();


        adapter = new FirestoreRecyclerAdapter<GroupChatList, GroupChatListFragment.GroupChatHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GroupChatListFragment.GroupChatHolder holder, int position, @NonNull GroupChatList model) {
                holder.setText(model.getJudul());
            }

            @NonNull
            @Override
            public GroupChatListFragment.GroupChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listgroupchat, parent, false);
                return new GroupChatListFragment.GroupChatHolder(view);
            }

            @Override
            public void onDataChanged() {
                super.onDataChanged();
                //todo untuk sekarang kalo ada data baru langsung pindah ke data baru recyclerviewnya
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        };
        recyclerView = v.findViewById(R.id.recyclerviewlistchat);

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

        void setText(String setJudul) {
            TextView judul = view.findViewById(R.id.judulgroupchat);
            judul.setText(setJudul);
            judul.setTextColor(Color.BLACK);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo investigasi sebaiknya add atau replace aja
                    //todo passing argument id groupchat
                    GroupChatFragment fragment = new GroupChatFragment();
                    Bundle args = new Bundle();
                    args.putString("chatid", "Value");
                    fragment.setArguments(args);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new GroupChatFragment()).commit();
                }
            });
        }
    }
}
