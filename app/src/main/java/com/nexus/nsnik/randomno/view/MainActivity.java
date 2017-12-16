/*
 * Copyright 2017 nsnikhil
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.nexus.nsnik.randomno.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.nexus.nsnik.randomno.R;
import com.nexus.nsnik.randomno.view.adapters.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int PREFERENCE_REQUEST_CODE = 0;

    @BindView(R.id.mainToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mainTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mainViewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        setSupportActionBar(mToolbar);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
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
                startActivityForResult(new Intent(MainActivity.this, PreferenceActivity.class), PREFERENCE_REQUEST_CODE);
                break;
            case R.id.menu_feedback:
                Intent sentEmail = new Intent(Intent.ACTION_SEND);
                sentEmail.setType("text/plain");
                sentEmail.putExtra(Intent.EXTRA_EMAIL, getResources().getString(R.string.developerEmailAddress));
                sentEmail.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.emailSubject));
                if (sentEmail.resolveActivity(getPackageManager()) != null) {
                    startActivity(sentEmail);
                }
                break;
        }
        return true;
    }

}
