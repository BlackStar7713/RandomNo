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

package com.nexus.nsnik.randomno.view.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nexus.nsnik.randomno.view.fragments.CoinTossFragment;
import com.nexus.nsnik.randomno.view.fragments.RandomNumberFragment;
import com.nexus.nsnik.randomno.view.fragments.RollDiceFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final CharSequence[] PAGER_HEADERS = {"Numbers", "Dice Roll", "Coin Toss"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RandomNumberFragment();
            case 1:
                return new RollDiceFragment();
            case 2:
                return new CoinTossFragment();
            default:
                throw new IllegalArgumentException("Invalid position : " + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return PAGER_HEADERS[0];
            case 1:
                return PAGER_HEADERS[1];
            case 2:
                return PAGER_HEADERS[2];
            default:
                throw new IllegalArgumentException("Invalid position : " + position);
        }
    }
}
