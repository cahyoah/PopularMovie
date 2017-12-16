package wkwkw.asek.cataloguemovie;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;

import wkwkw.asek.cataloguemovie.Adapter.TabFragmentPagerAdapter;
import wkwkw.asek.cataloguemovie.Alarm.AlarmPreference;
import wkwkw.asek.cataloguemovie.Alarm.AlarmReceiver;
import wkwkw.asek.cataloguemovie.Scheduler.SchedulerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;
    private SchedulerTask mSchedulerTask;
    private ViewPager pager;
    private TabLayout tabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSchedulerTask = new SchedulerTask(this);

        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();
        mSchedulerTask.createPeriodicTask();
        if(alarmPreference.getDailyReminder()==null){
            dailyReminder();


        }
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);

        //set object adapter kedalam ViewPager

        pager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager()));

        //Manimpilasi sedikit untuk set TextColor pada Tab
        tabs.setTabTextColors(getResources().getColor(R.color.colorGray),
                getResources().getColor(android.R.color.background_dark));

        //set tab ke ViewPager
        tabs.setupWithViewPager(pager);

        //konfigurasi Gravity Fill untuk Tab berada di posisi yang proposional
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_search){
            Intent intent = new Intent(MainActivity.this, PencarianActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.nav_pengaturan){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
            finish();

        }else if(id == R.id.nav_favorit){
            Intent mIntent = new Intent(this, FilmFavoritActivity.class);
            startActivity(mIntent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void dailyReminder(){

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        String repeatTimeTime = "08:00";
        System.out.println(timeFormat);
        alarmPreference.setDailyReminder(repeatTimeTime);

        alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                alarmPreference.getDailyReminder());
    }
    @Override
    public void onClick(View view) {


    }
}
