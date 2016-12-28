package com.nexus.nsnik.randomno;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class MyPagerAdap extends FragmentStatePagerAdapter{

    private static final CharSequence[] ab = {"Numbers","Dice Roll"," Coin Toss"};

    public MyPagerAdap(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if(position ==0)
        {
            f = new RandomNo();
        }
        if(position == 1)
        {
            f = new RollDice();
        }
        if(position == 2)
        {
            f = new CoinToss();
        }
        return f;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence s= null;
        if(position==0)
        {
            s = ab[0];
        }
        if(position==1)
        {
            s = ab[1];
        }
        if(position==2)
        {
            s = ab[2];
        }
        return s;
    }
}
