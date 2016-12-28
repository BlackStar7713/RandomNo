package com.nexus.nsnik.randomno;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class RandomActivity extends AppCompatActivity{

    private static final String[] mEmailAddress = {"n.nikhil.ns65@gmail.com"};
    private static final int mRequestCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
        if(spf.getBoolean("drkmd",false))
        {
           setTheme(R.style.AppThemeDrk);
        }
        setContentView(R.layout.activity_main);
        ViewPager random = (ViewPager)findViewById(R.id.mainViewPager);
        MyPagerAdap randomPager = new MyPagerAdap(getSupportFragmentManager());
        random.setAdapter(randomPager);
        TabLayout tb = (TabLayout)findViewById(R.id.mtabs_view);
        tb.setupWithViewPager(random);
        Toolbar ta = (Toolbar)findViewById(R.id.mtool_bar);
        setSupportActionBar(ta);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemId = item.getItemId();
        switch (menuItemId) {
            case R.id.menu_settings:
                Intent prefs = new Intent(RandomActivity.this,Prefrences.class);
                startActivityForResult(prefs,mRequestCode);
                break;
            case R.id.menu_feedback:
                Intent sentEmail = new Intent(Intent.ACTION_SEND);
                sentEmail.setType("text/plain");
                sentEmail.putExtra(Intent.EXTRA_EMAIL, mEmailAddress);
                sentEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback From Random No Generator App");
                if (sentEmail.resolveActivity(getPackageManager()) != null) {
                    startActivity(sentEmail);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==mRequestCode){
            if(resultCode==2)
            {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
