package com.example.recycler_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.recycler_project.fragments.AboutFragment;
import com.example.recycler_project.fragments.HelpFragment;
import com.example.recycler_project.fragments.HomeFragment;
import com.example.recycler_project.fragments.PoliticFragment;
import com.example.recycler_project.fragments.ProductsFragment;
import com.example.recycler_project.fragments.ProfileFragment;
import com.example.recycler_project.fragments.RecyclerFragment;
import com.example.recycler_project.fragments.SettingFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class Navigation extends AppCompatActivity {

    BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        showSelectedFragment(new HomeFragment());
        navigation = (BottomNavigationView) findViewById(R.id.menu_navigation);
//

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                if (item.getItemId() == R.id.menu_home){
                    showSelectedFragment(new HomeFragment());
                }
                if (item.getItemId() == R.id.menu_settings){
                    showSelectedFragment(new SettingFragment());
                }
                if (item.getItemId() == R.id.menu_profile){
                    showSelectedFragment(new ProfileFragment());
                }

                return true;
            }
        });
    }

    private void showSelectedFragment (Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container_nav,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
    }

}