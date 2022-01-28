package com.g3;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    TaskAdapter taskAdapter;
    SettingsDB settingsDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsDB = new SettingsDB(this);
        //this.deleteDatabase("settings.db");
        taskAdapter=new TaskAdapter(settingsDB);

        try {
            settingsDB.getSettings(1);
        } catch(android.database.CursorIndexOutOfBoundsException ex){
            settingsDB.initSettings();
        }
        switch(settingsDB.getSettings(1).getDarkMode()){
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.TBMainAct);
        setSupportActionBar(toolbar);

        NavHostFragment host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NHFMain);
        NavController navController = host.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        DrawerLayout drawerLayout = findViewById(R.id.DLMain);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setupNavMenu(navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    private void setupNavMenu(NavController navController) {
        NavigationView sideNav = findViewById(R.id.sideNav);
        NavigationUI.setupWithNavController(sideNav, navController);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            Navigation.findNavController(this, R.id.NHFMain).navigate(item.getItemId());
            return true;
        } catch(Exception ex) {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.NHFMain).navigateUp();
    }

    public TaskAdapter getTaskAdapter(){
        return taskAdapter;
    }
    public SettingsDB getSettingsDB(){
        return settingsDB;
    }
}