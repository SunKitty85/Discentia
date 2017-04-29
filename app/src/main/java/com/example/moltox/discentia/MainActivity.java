package com.example.moltox.discentia;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getName();
    View view;
    int count;
    Button m_btn_takeCard;
    TextView m_tv_main;
    Button btn_debugActivity;
    Button btn_download;
    Button btn_showCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        m_tv_main = (TextView) findViewById(R.id.id_tv_main);
        m_btn_takeCard = (Button) findViewById(R.id.btn_takeCard);
        m_btn_takeCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callQandaActivity(v);
            }
        });
        btn_showCards = (Button) findViewById(R.id.btn_showCards);
        btn_showCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callShowCardsActivity(v);
            }
        });

        btn_debugActivity = (Button) findViewById(R.id.btn_debugActivity);
        btn_debugActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDebugActivy(v);
            }
        });
        btn_download = (Button) findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDownloadActivity(v);
            }
        });

        Log.v(TAG, "Serialnr: " + Build.SERIAL
                + "\nFingerprint: " + Build.FINGERPRINT
                + "\nBoard: " + Build.BOARD
                + "\nBrand: " + Build.BRAND
                + "\nBoatloader: " + Build.BOOTLOADER
                + "\nDisplay: " + Build.DISPLAY
                + "\nHardware: " +Build.HARDWARE
                + "\nHost: " + Build.HOST
                + "\nID: " + Build.ID
                + "\nManufacturer: " + Build.MANUFACTURER
                + "\nModel: " + Build.MODEL
                + "\nProduct: " + Build.PRODUCT
                + "\nTags: " + Build.TAGS
                + "\nType: " + Build.TYPE
                + "\nUser: " + Build.USER
                + "\nTime: " + Build.TIME);
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
        getMenuInflater().inflate(R.menu.main, menu);
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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void callQandaActivity(View view) {
        Intent intent = new Intent(this, qanda_activity.class);
        startActivity(intent);
    }

    private void callShowCardsActivity(View v) {
        Intent intent = new Intent(this, ShowCards.class);
        startActivity(intent);
    }

    private void callDebugActivy(View view) {
        Intent intent = new Intent(this, DebugActivity.class);
        startActivity(intent);
    }

    private void callDownloadActivity(View view) {
        Intent intent = new Intent(this, Download.class);
        startActivity(intent);
    }
}
