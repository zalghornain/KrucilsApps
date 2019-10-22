package com.example.krucils;

import android.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Button registerhomeBtn, loginBtn, logoutBtn;
    private TextView logintitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);

        mAuth = FirebaseAuth.getInstance();

        registerhomeBtn=v.findViewById(R.id.register);
        loginBtn=v.findViewById(R.id.login);
        logintitle=v.findViewById(R.id.main_title);
        logoutBtn=v.findViewById(R.id.logout);

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String name = user.getEmail();
            logintitle.setText("Selamat datang \n" + name);
            loginBtn.setVisibility(View.GONE);
            registerhomeBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.VISIBLE);
        }else{
            String name = "Guest";
            logintitle.setText("Selamat datang \n" + name);
            loginBtn.setVisibility(View.VISIBLE);
            registerhomeBtn.setVisibility(View.VISIBLE);
            logoutBtn.setVisibility(View.GONE);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                //TODO bikin refresh layout setelah login
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (currentFragment instanceof ProfilFragment) {
                    FragmentTransaction fragTransaction =   (getActivity()).getSupportFragmentManager().beginTransaction();
                    fragTransaction.detach(currentFragment);
                    fragTransaction.attach(currentFragment);
                    fragTransaction.commit();
                }
            }
        });

        return v;
    }
}