package com.example.krucils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Beranda extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beranda);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new BerandaFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_beranda);
        }

        mAuth = FirebaseAuth.getInstance();
        /*if (!userLoggedin()) {
            username.setText("Guest");
        }else{
            username.setText(user.getEmail());
        }*/
        updateUI(this,mAuth);

        //todo bikin kelas getter userLoggedIn sama buat profile dari firebase ?

        //cari cara buat invalidateoptionmenu kalo logout dari action bar
        //ini udah

        //cari cara buat automatis manggil sesuatu abis layout/data change pada fragment
        //ini sepertinya udah



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //TODO bingung pake userloggedin atau cara lain

        getMenuInflater().inflate(R.menu.main, menu);
        if (!userLoggedin()) {
            menu.findItem(R.id.action_login).setVisible(true);
            menu.findItem(R.id.action_daftar).setVisible(true);
            menu.findItem(R.id.action_logout).setVisible(false);
        }else{
            menu.findItem(R.id.action_login).setVisible(false);
            menu.findItem(R.id.action_daftar).setVisible(false);
            menu.findItem(R.id.action_logout).setVisible(true);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo bikin reloader fragment di new kelas (atau masukkin updateUI), login dari profil fragment ke reload profil fragmentnya tapi dari action bar nggak
        switch (item.getItemId()) {
            case R.id.action_login:

                Intent loginIntent = new Intent(Beranda.this, LoginActivity.class);
                Beranda.this.startActivity(loginIntent);
                updateUI(this,mAuth);

                return true;
            case R.id.action_daftar:
                Intent registerIntent = new Intent(Beranda.this, RegisterActivity.class);
                Beranda.this.startActivity(registerIntent);
                updateUI(this,mAuth);

                return true;


            case R.id.action_logout:
                mAuth.signOut();
                updateUI(this,mAuth);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_beranda:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BerandaFragment()).commit();
                updateUI(this,mAuth);
                break;
            case R.id.nav_kelas:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new KelasFragment()).commit();
                updateUI(this,mAuth);
                break;
            case R.id.nav_notif:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NotifikasiFragment()).commit();
                updateUI(this,mAuth);
                break;

            case R.id.nav_profil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfilFragment()).commit();
                updateUI(this,mAuth);
                break;

            case R.id.nav_input:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InputFragment()).commit();
                updateUI(this,mAuth);
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawer.isDrawerOpen(GravityCompat.START)){

            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    public boolean userLoggedin(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        //gak butuh kah karena udah ada updateui ?
        updateUI(this,mAuth);

    }

    public void updateUI(Activity activity, FirebaseAuth auth){
        //update header name
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.username);
        TextView email = headerView.findViewById(R.id.email);

        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            username.setText(R.string.guest);
            email.setText("");
        }else{
            //untuk sekarang samain dulu username sama email
            username.setText(user.getEmail());
            email.setText(user.getEmail());
        }


        //update action bar
        activity.invalidateOptionsMenu();

        //todo bikin buat reset fragment yang lagi di opennya
    }
}
