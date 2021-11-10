package com.example.appblehv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    DevicesFragment devicesFragment = new DevicesFragment();
    PlantsFragment plantsFragment = new PlantsFragment();
    SessionFragment sessionFragment = new SessionFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         BottomNavigationView navigation = findViewById(R.id.materialButton);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFrament(devicesFragment);

    }
    private final  BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.nav_devices:loadFrament(devicesFragment);
                return true;
                case R.id.nav_plants:loadFrament(plantsFragment);
                    return true;
                case R.id.nav_session:loadFrament(sessionFragment);
                    return true;
            }
            return false;
        }
    };

    public  void loadFrament (Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentInitial,fragment);
        transaction.commit();
    }

}