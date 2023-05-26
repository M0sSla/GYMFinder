package com.example.gymfinder;

import android.os.Bundle;
import android.view.View;

import com.example.gymfinder.ui.auth.AuthorizationFragment;
import com.example.gymfinder.ui.auth.RegistrationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.gymfinder.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements HidingBottomNav {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //binding.navView.setVisibility(View.GONE);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_finder, R.id.navigation_training, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void HidingNavigation(Fragment fragment) {
        binding.navView.setVisibility(View.GONE);
    }
}