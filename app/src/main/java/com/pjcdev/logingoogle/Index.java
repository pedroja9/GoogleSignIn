package com.pjcdev.logingoogle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class Index extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    private ImageView ivprofile;
    private TextView tvname;
    private TextView tvemail;
    private TextView tvidnumber;
    private String picprofile;
    private String name;
    private String idnumber;
    private String email;
    private Context context=null;

    final List<String> profile = new ArrayList<String>();
    private View headerview;

    private GoogleApiClient mGoogleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //create a headerview to conect to header of left menu
        headerview=navigationView.getHeaderView(0);

        ivprofile=(ImageView)headerview.findViewById(R.id.imageProfile);
        tvname=(TextView)headerview.findViewById(R.id.fullName);
        tvemail=(TextView)headerview.findViewById(R.id.email);
        tvidnumber=(TextView) headerview.findViewById(R.id.idNumber);

        //Load file saved by sharedpreferences into a new arraylist
        final SharedPreferences sharpref = getSharedPreferences("ProfileList",0);
        String Items = sharpref.getString("ProfileList","");
        String [] listItems = Items.split(",");
        for (int i=0;i<listItems.length;i++){
            profile.add(listItems[i]);
        }

        //get the profile

        idnumber=profile.get(0);
        name=profile.get(1);
        email=profile.get(2);
        picprofile=profile.get(3);

        Log.d("ArrayPerfil", name+email+idnumber+picprofile);

        tvname.setText(name);
        tvidnumber.setText(idnumber);
        tvemail.setText(email);
        Glide.with(this).load(picprofile).into(ivprofile);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        /*
        if(mGoogleApiClient!=null) {
            if (mGoogleApiClient.isConnected()) {
                Log.d("MYGAPICLIENT", "Estas conectado");
                Log.d("MYGAPICLIENT", mGoogleApiClient.toString());
            }
            else {
                Log.d("MYGAPICLIENT", "estas desconectado");
                ;
            }

        }
     else{
            Log.d("MYGAPICLIENT", "el googleapiclient esta vacio");
        }*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.index, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id == R.id.logout) {

           //myGoogleApiSingleton.getInstance(context).Logout();
            signOut();
            goLoginScreen();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        if(mGoogleApiClient!=null) {
            if (mGoogleApiClient.isConnected()) {

                Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.d("LOGOUT", "Logout correcto");
                            goLoginScreen();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error al hacer logout", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "no estas conectado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //method to go to login screen
    private void goLoginScreen() {
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
