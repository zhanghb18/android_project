package com.example.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.forum.ui.moments.MomentsAll;
import com.example.forum.ui.moments.SingleMoment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.forum.databinding.ActivityMainBinding;

public class ForumActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        getSupportActionBar().hide();

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_moments)
                .build();
        this.navController= Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, this.navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, this.navController);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        System.out.println("onActivityResult");
        super.onActivityResult(requestCode, resultCode, intent);
        System.out.println(requestCode);
        System.out.println(resultCode);

        if (resultCode == RESULT_OK) {
            Bundle bundle = intent.getExtras();
            //String title = bundle.getString("title");
            //String content = bundle.getString("content");

            //Intent intent=getIntent();
            System.out.println("getIntent");
            System.out.println(bundle);
            if(bundle.getInt("id")==1){
                System.out.println("id");
            }
            //Bundle bundle=intent.getExtras();
//                MomentsAll momentsAll=new MomentsAll();
//                momentsAll.setArguments(bundle);
//                getSupportFragmentManager().beginTransaction().add(R.id.navigation_moments,momentsAll).commit();

        }
        // Test for the right intent reply.
//        if (requestCode == 1) {
//            // Test to make sure the intent reply result was good.
//            if (resultCode == RESULT_OK) {
//                Bundle bundle = intent.getExtras();
//                //String title = bundle.getString("title");
//                //String content = bundle.getString("content");
//
//                //Intent intent=getIntent();
//                System.out.println("getIntent");
//                System.out.println(bundle);
//                //Bundle bundle=intent.getExtras();
////                MomentsAll momentsAll=new MomentsAll();
////                momentsAll.setArguments(bundle);
////                getSupportFragmentManager().beginTransaction().add(R.id.navigation_moments,momentsAll).commit();
//
//            }
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
