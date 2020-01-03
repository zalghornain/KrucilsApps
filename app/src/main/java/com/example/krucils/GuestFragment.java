package com.example.krucils;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GuestFragment extends Fragment {
    private Button registerhomeBtn, loginBtn;
    private TextView welcometitle;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guest, container, false);

        registerhomeBtn = v.findViewById(R.id.register);
        loginBtn = v.findViewById(R.id.login);
        welcometitle = v.findViewById(R.id.welcome_title);

        String name = getText(R.string.guest).toString();
        welcometitle.setText(getText(R.string.welcome) + name);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                GuestFragment.this.startActivity(loginIntent);
            }
        });

        registerhomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(getActivity(), RegisterActivity.class);
                GuestFragment.this.startActivity(registerIntent);
            }
        });
        return v;
    }
}


