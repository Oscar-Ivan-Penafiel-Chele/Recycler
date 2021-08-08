package com.example.recycler_project;

import android.os.Bundle;

import com.example.recycler_project.fragments.AdminHomeFragment;
import com.example.recycler_project.fragments.HomeFragment;
import com.example.recycler_project.fragments.ProfileFragment;
import com.example.recycler_project.fragments.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.recycler_project.databinding.ActivityAdminNavigationBinding;

import org.jetbrains.annotations.NotNull;

public class AdminNavigationActivity extends AppCompatActivity {

    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_navigation);
        showSelectedFragment(new AdminHomeFragment());
        navigation = (BottomNavigationView) findViewById(R.id.menu_navigation_admin);
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

    private void showSelectedFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_cont_admin,fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(null)
                    .commit();
        }
    }
}