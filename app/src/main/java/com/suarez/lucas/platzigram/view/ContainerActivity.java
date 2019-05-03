package com.suarez.lucas.platzigram.view;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.suarez.lucas.platzigram.R;
import com.suarez.lucas.platzigram.view.fragments.Home2Fragment;
import com.suarez.lucas.platzigram.view.fragments.HomeFragment;
import com.suarez.lucas.platzigram.view.fragments.ProfileFragment;
import com.suarez.lucas.platzigram.view.fragments.SearchFragment;

public class ContainerActivity extends AppCompatActivity {

    private boolean viewIsAtHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottombar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.homeTap:
//                        addFragment(new HomeFragment());
                        addFragment(new Home2Fragment());
                        viewIsAtHome = true;
                        break;
                    case R.id.profileTap:
                        addFragment(new ProfileFragment());
                        viewIsAtHome = false;
                        break;
                    case R.id.searchTap:
                        addFragment(new SearchFragment());
                        viewIsAtHome = false;
                        break;
                }
                return true;

            }
        });

        // aqui se declara por default cual sear el fragment inicial cuando se inicie el container
        bottomNavigationView.setSelectedItemId(R.id.homeTap);

    }

    private void addFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(null)
                .commit();
    }

    // metodo para controlar el  backButton
    @Override
    public void onBackPressed() {
        if (!viewIsAtHome) { //Si la vista actual no es el fragment Home
            BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottombar);
            bottomNavigationView.setSelectedItemId(R.id.homeTap); //Selecciona el fragment Home
        } else {
            moveTaskToBack(true);  //Si presionas Back cuando ya muestras el fragment Home, sale de la app
        }
    }



}
