package com.example.collegeapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.collegeapp.ebook.EbookActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private FirebaseAuth auth;
    public static String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
auth=FirebaseAuth.getInstance();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        navController = Navigation.findNavController(this, R.id.main_fragment);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_drawer);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.al:
                Toast.makeText(this, "Admin Login", Toast.LENGTH_SHORT).show();
                Intent ai=new Intent(MainActivity.this,LoginPage.class);
                userType="Admin";
                ai.putExtra("name","Admin");
                startActivity(ai);

                return true;
            case R.id.fl:
                Toast.makeText(this, "Faculty Login", Toast.LENGTH_SHORT).show();
                Intent fi=new Intent(MainActivity.this,LoginPage.class);
                userType="Faculty";
                fi.putExtra("name","Faculty");
                startActivity(fi);

                return true;
            case R.id.sl:
                Toast.makeText(this, "Student Login", Toast.LENGTH_SHORT).show();
                Intent si=new Intent(MainActivity.this,LoginPage.class);
                userType="Student";
                si.putExtra("name","Student");
                startActivity(si);

                return true;
            case R.id.lg:
                auth.signOut();
                findViewById(R.id.s).setVisibility(View.GONE);
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.arch:
                Toast.makeText(this, "Archive", Toast.LENGTH_SHORT).show();
                item.setCheckable(false);
                break;
            case R.id.gal:
                startActivity(new Intent(MainActivity.this,ImageGallery.class));
                item.setCheckable(false);
                break;
            case R.id.eBooks:
                startActivity(new Intent(MainActivity.this, EbookActivity.class));
                item.setCheckable(false);
                break;
            case R.id.pol:
                startActivity(new Intent(MainActivity.this, PolicyNavigation.class));
                item.setCheckable(false);
                break;
            case R.id.about:
                startActivity(new Intent(MainActivity.this, AboutNavigation.class));
                item.setCheckable(false);
                break;
            case R.id.rate:
                Toast.makeText(this, "Rate Us", Toast.LENGTH_SHORT).show();
                item.setCheckable(false);
                break;
            case R.id.share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                item.setCheckable(false);
                break;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_menu, menu);
        if(auth.getCurrentUser()!=null)
        menu.getItem(3).setVisible(true);
         return super.onCreateOptionsMenu(menu);
    }

}