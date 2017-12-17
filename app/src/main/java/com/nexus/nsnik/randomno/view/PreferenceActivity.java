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
import android.preference.ListPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.nexus.nsnik.randomno.R;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().add(android.R.id.content, new PreferenceFragment()).commit();
    }

    public static class PreferenceFragment extends android.preference.PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
            ListPreference listPreference = (ListPreference) findPreference(getActivity().getResources().getString(R.string.prefDarkModeKey));
            listPreference.setSummary(listPreference.getValue());
            listPreference.setOnPreferenceChangeListener((preference, o) -> {
                listPreference.setValue(o.toString());
                preference.setSummary(o.toString());
                switchTheme(o.toString());
                return false;
            });
        }

        private void switchTheme(String mode) {
            switch (mode) {
                case "On":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    break;
                case "Off":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    break;
                case "Auto":
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid mode :" + mode);
            }
            getActivity().startActivity(new Intent(getActivity(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }
}
