package com.nexus.nsnik.randomno;


import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Random;

public class RollDice extends Fragment implements View.OnClickListener {

    private static int mNoOfTimesCount = 0;
    private static int mCounter = 0;
    private static int[] diceValue = {0,0,0,0,0,0,0};
    ImageView dice, dice1, dice2, dice3, dice4, dice5;
    Button roll;
    Spinner diceNo;

    public RollDice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rollDice = inflater.inflate(R.layout.activity_dice, container, false);
        initilize(rollDice);
        setSpinner(diceNo);
        if (savedInstanceState != null) {
            setVisibility(mNoOfTimesCount);
            restoreValue(mNoOfTimesCount);
        }
        roll.setOnClickListener(this);
        return rollDice;
    }

    private int getSpinnerValue() {
        return Integer.parseInt(diceNo.getSelectedItem().toString());
    }

    private void setSpinner(Spinner spin) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.diceNo,R.layout.spinner_view);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(spf.getBoolean("drkmd",false))
        {
            spin.getBackground().setColorFilter(getResources().getColor(R.color.materialWhite), PorterDuff.Mode.SRC_ATOP);
        }
        else {
            spin.getBackground().setColorFilter(getResources().getColor(R.color.materialBlack), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void restoreValue(int count)
    {
        for(int k=1;k<=count;k++)
        {
            setImg(k,diceValue[k]);
        }
    }

    private void initilize(View rollDice) {
        dice = (ImageView) rollDice.findViewById(R.id.diceImg);
        dice1 = (ImageView) rollDice.findViewById(R.id.diceImg1);
        dice2 = (ImageView) rollDice.findViewById(R.id.diceImg2);
        dice3 = (ImageView) rollDice.findViewById(R.id.diceImg3);
        dice4 = (ImageView) rollDice.findViewById(R.id.diceImg4);
        dice5 = (ImageView) rollDice.findViewById(R.id.diceImg5);
        roll = (Button) rollDice.findViewById(R.id.roll);
        diceNo = (Spinner) rollDice.findViewById(R.id.diceQty);
    }

    @Override
    public void onClick(View v) {
        int getId = v.getId();
        switch (getId) {
            case R.id.roll:
                int noOfTimes = getSpinnerValue();
                mNoOfTimesCount = noOfTimes;
                mCounter = 1;
                setInvisible();
                setVisibility(noOfTimes);
                setDice(noOfTimes);
                break;
        }
    }

    private void setDice(int noOfTimes) {
        for (int i = 1; i <= noOfTimes; i++) {
            Random rand = new Random();
            int randomNum = rand.nextInt((6 - 1) + 1) + 1;
            int diceImageValue = getImg(randomNum);
            saveState(i,randomNum);
            setImg(i, diceImageValue);
        }
    }

    private void setInvisible() {
        dice5.setVisibility(View.GONE);
        dice4.setVisibility(View.GONE);
        dice3.setVisibility(View.GONE);
        dice2.setVisibility(View.GONE);
        dice1.setVisibility(View.GONE);
        dice.setVisibility(View.GONE);
    }

    private void setVisibility(int times) {
        switch (times) {
            case 6:
                dice5.setVisibility(View.VISIBLE);
            case 5:
                dice4.setVisibility(View.VISIBLE);
            case 4:
                dice3.setVisibility(View.VISIBLE);
            case 3:
                dice2.setVisibility(View.VISIBLE);
            case 2:
                dice1.setVisibility(View.VISIBLE);
            case 1:
                dice.setVisibility(View.VISIBLE);
                break;
            default:
                Log.v("Nothing", "Done");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("nodice", mNoOfTimesCount);
        super.onSaveInstanceState(outState);
    }

    private void saveState(int iteration,int value)
    {
        switch (iteration)
        {
            case 1:
                diceValue[iteration] = getImg(value);
                break;
            case 2:
                diceValue[iteration]= getImg(value);
                break;
            case 3:
                diceValue[iteration] = getImg(value);
                break;
            case 4:
                diceValue[iteration] = getImg(value);
                break;
            case 5:
                diceValue[iteration] = getImg(value);
                break;
            case 6:
                diceValue[iteration] = getImg(value);
                break;
        }
    }


    private void setImg(int no, int imgVal) {
        switch (no) {
            case 1:
                dice.setImageResource(imgVal);
                break;
            case 2:
                dice1.setImageResource(imgVal);
                break;
            case 3:
                dice2.setImageResource(imgVal);
                break;
            case 4:
                dice3.setImageResource(imgVal);
                break;
            case 5:
                dice4.setImageResource(imgVal);
                break;
            case 6:
                dice5.setImageResource(imgVal);
                break;
        }
    }

    private int getImg(int randomNum) {
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        switch (randomNum) {
            case 1:
                if(spf.getBoolean("drkmd",false)) {
                    return R.drawable.dice_one_white;
                }
                else {
                    return R.drawable.dice_one;
                }
            case 2:
                if(spf.getBoolean("drkmd",false)) {
                    return R.drawable.dice_two_white;
                }
                else {
                    return R.drawable.dice_two;
                }
            case 3:
                if(spf.getBoolean("drkmd",false)) {
                    return R.drawable.dice_three_white;
                }
                else {
                    return R.drawable.dice_three;
                }
            case 4:
                if(spf.getBoolean("drkmd",false)) {
                    return R.drawable.dice_four_white;
                }
                else {
                    return R.drawable.dice_four;
                }
            case 5:
                if(spf.getBoolean("drkmd",false)) {
                    return R.drawable.dice_five_white;
                }
                else {
                    return R.drawable.dice_five;
                }
            case 6:
                if(spf.getBoolean("drkmd",false)) {
                    return R.drawable.dice_six_white;
                }
                else {
                    return R.drawable.dice_six;
                }
        }
        return 0;
    }


}
