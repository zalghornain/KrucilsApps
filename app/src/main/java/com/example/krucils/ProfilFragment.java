package com.example.krucils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProfilFragment extends Fragment {
    private FirebaseUser user;
    private Button changeusernameBtn, changeemailBtn, changepassBtn;
    private EditText usernamefield, emailfield, oldpassfield, newpassfield, retypedpassfield;
    private String username, email, oldpass, newpass, retypedpass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profil, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        usernamefield = v.findViewById(R.id.profile_username);

        emailfield = v.findViewById(R.id.profile_email);

        oldpassfield = v.findViewById(R.id.profile_old_password);
        oldpass = oldpassfield.getText().toString();
        //todo harus pake reauth, cek bawah
        oldpassfield.setVisibility(View.INVISIBLE);

        newpassfield = v.findViewById(R.id.profile_new_password);

        retypedpassfield = v.findViewById(R.id.profile_new_password_confirm);


        changeusernameBtn = v.findViewById(R.id.change_username);
        changeusernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernamefield.getText().toString();

                if(username.isEmpty()){
                    Toast.makeText( getActivity(), "Tolong lengkapi field.",Toast.LENGTH_LONG).show();
                } else {

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                        Toast.makeText( getActivity(), "Username anda telah terupdate.",Toast.LENGTH_LONG).show();
                                        //todo bikin exception kalo username sama ?
                                        updateUIprofile();
                                    }
                                }
                            });
                }
            }
        });

        changeemailBtn = v.findViewById(R.id.change_email);
        changeemailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailfield.getText().toString();

                if(email.isEmpty()){

                    Toast.makeText( getActivity(), "Tolong lengkapi field.",Toast.LENGTH_LONG).show();

                }else {

                    user.updateEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User email address updated.");
                                        Toast.makeText( getActivity(), "Email anda telah terupdate.",Toast.LENGTH_LONG).show();
                                        //todo bikin send email verif disini
                                        updateUIprofile();
                                    }
                                }
                            });
                }
            }
        });

        changepassBtn = v.findViewById(R.id.change_pass);
        changepassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo bikin reauthentication, untuk sekarang old password gak dipake dulu mungkin
                newpass = newpassfield.getText().toString();
                retypedpass = retypedpassfield.getText().toString();

                if (newpass.isEmpty() || retypedpass.isEmpty()){
                    Toast.makeText( getActivity(), "Tolong lengkapi field.",Toast.LENGTH_LONG).show();
                } else if (retypedpass.equals(newpass)){
                    user.updatePassword(retypedpass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password updated.");
                                        Toast.makeText( getActivity(), "Password anda telah terupdate.",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        //todo bikin exception login udah lama, perlu reauth
                                        Toast.makeText( getActivity(), "Reauthentication gagal.",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                } else{
                    Toast.makeText( getActivity(), "Password baru dan lama anda tidak sama.",Toast.LENGTH_LONG).show();
                }

            }
        });

        //todo bikin reset pass

        return v;
    }

    private void updateUIprofile(){
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.username);
        TextView email = headerView.findViewById(R.id.email);

        username.setText(user.getDisplayName());
        email.setText(user.getEmail());
    }
}