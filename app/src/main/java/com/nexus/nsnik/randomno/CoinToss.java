package com.nexus.nsnik.randomno;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class CoinToss extends Fragment implements View.OnClickListener{

    ImageView toss;
    Button flip;
    private static int mCounter = 0;

    public CoinToss() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View coinToss = inflater.inflate(R.layout.activity_toss,container,false);
        initilize(coinToss);
        if(savedInstanceState!=null)
        {
            if(savedInstanceState.getInt("coin")==1||savedInstanceState.getInt("coin")==2)
            {
                toss.setVisibility(View.VISIBLE);
                setImg(savedInstanceState.getInt("coin"));
            }
        }
        flip.setOnClickListener(this);
        return coinToss;
    }

    private void initilize(View coinToss) {
        toss = (ImageView) coinToss.findViewById(R.id.coin);
        flip = (Button)coinToss.findViewById(R.id.flip);
    }

    @Override
    public void onClick(View v) {
        int getId = v.getId();
        switch (getId)
        {
            case R.id.flip :
                Random rand = new Random();
                int randomNum = rand.nextInt((1 - 0) + 1) + 0;
                toss.setVisibility(View.VISIBLE);
                if(randomNum==1)
                {
                    mCounter = 1;
                    setImg(mCounter);
                }
                if(randomNum==0)
                {
                    mCounter = 2;
                    setImg(mCounter);
                }
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("coin",mCounter);
        super.onSaveInstanceState(outState);
    }

    private void setImg(int x)
    {
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        switch (x)
        {
            case 1:
                if(spf.getBoolean("drkmd",false)) {
                    toss.setImageResource(R.drawable.coin_heads_white);
                }
                else {
                    toss.setImageResource(R.drawable.coin_heads);
                }
                break;
            case 2:
                if(spf.getBoolean("drkmd",false)) {
                    toss.setImageResource(R.drawable.coin_tails_white);
                }
                else {
                    toss.setImageResource(R.drawable.coin_tails);
                }
                break;
        }
    }
}
