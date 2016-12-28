package com.nexus.nsnik.randomno;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class Prefrences extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    private static final int mResultCode = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        spf.registerOnSharedPreferenceChangeListener(this);
        if(spf.getBoolean("drkmd",false))
        {
            setTheme(R.style.AppThemeDrk);
        }
        setContentView(R.layout.container);
        Toolbar pf = (Toolbar)findViewById(R.id.preftool_bar);
        setSupportActionBar(pf);
        getFragmentManager().beginTransaction().add(R.id.container,new Pres()).commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equalsIgnoreCase("drkmd"))
        {
            setResult(mResultCode);
            Intent rs = getIntent();
            rs.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(rs);
        }
    }


    public static class Pres extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
        }
    }
}
