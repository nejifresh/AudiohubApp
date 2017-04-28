package com.nejitawo.audiohub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;


import com.nejitawo.audiohub.TabSetup.TabMakerFragment;
import com.nejitawo.audiohub.R;
import com.parse.ParseUser;

import java.util.Random;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private static final String ALLOWED_CHARACTERS ="0123456789";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_here);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);



         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabMakerFragment()).commit();
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_checkout) {
            shareIntent();
          //  return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
         if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_download) {
             Intent intent = new Intent(HomeActivity.this, DownloadsActivity.class);
             startActivity(intent);

        } else if (id == R.id.nav_account) {

             Intent intent = new Intent(HomeActivity.this, WebActivity.class);
             intent.putExtra("data", "http://audiohub.mymegalibrary.com/members/");
             startActivity(intent);
         }

         else if (id == R.id.nav_logout) {

            ParseUser.logOutInBackground();
             Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
             startActivity(intent);
             finish();
         }
         else if (id == R.id.nav_info) {

             Intent intent = new Intent(HomeActivity.this, WebActivity.class);
             intent.putExtra("data", "http://www.mymegalibrary.com/audiohub/");
             startActivity(intent);
         }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareIntent(){
        try{
            //  AssetManager am = getAssets();
            //  InputStream inputStream = am.open("nairababe.jpg");
            //  File file = createFileFromInputStream(inputStream);
            // File filePath = getFileStreamPath(file);
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Download EduAngel!");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "EduAngel is a mobile app which provides the latest and comprehensive scholarship information for undergraduate, postgraduate and short courses from universities, organizations, foundations, fellowships and governments across the globe. It is a one-stop scholarship center in your pocket.. Download EduAngel on Google Play: https://play.google.com/store/apps/details?id=com.nejitawo.eduangel");
            // shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));  //optional//use this when you want to send an image
            shareIntent.setType("text/plain");
            //shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        } catch (Exception e){
            Log.e("Errorshare",e.getMessage());
        }


    }






    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }


}
