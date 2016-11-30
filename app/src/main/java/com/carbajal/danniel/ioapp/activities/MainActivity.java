package com.carbajal.danniel.ioapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.activities.InputActivities.GraficoInputActivity;
import com.carbajal.danniel.ioapp.activities.InputActivities.SimplexInputActivity;
import com.carbajal.danniel.ioapp.activities.InputActivities.TransportInputActivity;
import com.carbajal.danniel.ioapp.fragments.ModelInputFragment;
import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ModelInputFragment.onCaptureModelListener{

    private Toolbar toolbar;
    private FuncionObjetivo funcionObjetivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDrawer();

        initNavigations();
    }

    private  void initDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavigations(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Simplex){
            Activity host = this;
            Intent newIntent = new Intent(host, SimplexInputActivity.class);
            host.startActivity(newIntent);
        } else if (id == R.id.Grafico) {
            Activity host = this;
            Intent newIntent = new Intent(host, GraficoInputActivity.class);
            host.startActivity(newIntent);

        } else if (id == R.id.ESN) {
            Activity host = this;
            Intent newIntent = new Intent(host, TransportInputActivity.class);
            host.startActivity(newIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view) {
        Log.v("ok","yes");
        int id = view.getId();
        if (id == R.id.Simplex){
            Activity host = this;
            Intent newIntent = new Intent(host, SimplexInputActivity.class);
            host.startActivity(newIntent);
        } else if (id == R.id.Grafico) {
            Activity host = this;
            Intent newIntent = new Intent(host, GraficoInputActivity.class);
            host.startActivity(newIntent);

        } else if (id == R.id.ESN) {
            Activity host = this;
            Intent newIntent = new Intent(host, TransportInputActivity.class);
            host.startActivity(newIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onCaptureModel(FuncionObjetivo funcionObjetivoa) {
        this.funcionObjetivo = funcionObjetivoa;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
