package com.nexus.nsnik.randomno;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class RandomNo extends Fragment implements View.OnClickListener{

    TextView randomNo;
    EditText lowerLimit, upperLimit, quantity;
    Button generate, reset;
    CheckBox nonrp, sort;
    int[] mRandomQuantity = new int[100];
    private static int getLowerLimit;
    private static int getUpperLimit;
    View vln;
    private static int mCleared = 0;

    public RandomNo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View randomNoView = inflater.inflate(R.layout.activity_random, container, false);
        initilize(randomNoView);
        if (savedInstanceState != null) {
            if(savedInstanceState.getString("random")==null)
            {
                randomNo.setText(getResources().getString(R.string.na));
            }else {
                randomNo.setText(savedInstanceState.getString("random"));
            }
        }
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(spf.getBoolean("drkmd",false))
        {
            vln.setBackgroundColor(getResources().getColor(R.color.materialWhite));
        }
        else {
            vln.setBackgroundColor(getResources().getColor(R.color.materialBlack));
        }

        generate.setOnClickListener(this);
        reset.setOnClickListener(this);
        return randomNoView;
    }

    private void initilize(View v) {
        randomNo = (TextView) v.findViewById(R.id.randomNo);
        lowerLimit = (EditText) v.findViewById(R.id.lowerLimit);
        upperLimit = (EditText) v.findViewById(R.id.upperLimit);
        generate = (Button) v.findViewById(R.id.generate);
        reset = (Button) v.findViewById(R.id.reset);
        quantity = (EditText) v.findViewById(R.id.random_quantity);
        nonrp = (CheckBox) v.findViewById(R.id.nonRepeating);
        sort = (CheckBox) v.findViewById(R.id.sorted);
        vln = v.findViewById(R.id.view_line);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.generate:
                int checkEmpty = notEmpty();
                switch (checkEmpty) {
                    case 0:
                        Toast.makeText(getActivity(), getResources().getString(R.string.elmt), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), getResources().getString(R.string.eult), Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        int lowerCheck = validateLower();
                        if (lowerCheck == 0) {
                            int upperCheck = validateUpper();
                            if (upperCheck == 0) {
                                int bigCheck = checkBigger();
                                if (bigCheck == 0) {
                                    getLowerLimit = Integer.parseInt(lowerLimit.getText().toString());
                                    getUpperLimit = Integer.parseInt(upperLimit.getText().toString());
                                    if(chkqty())
                                    {
                                        if (nonrp.isChecked()) {
                                            if(dispError())
                                            {
                                                new GetNum().execute();
                                            }
                                        }
                                        else {
                                            new GetNum().execute();
                                        }
                                    }
                                } else {
                                    Toast.makeText(getActivity(), getResources().getString(R.string.lowerLarger), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getActivity(), getResources().getString(R.string.upperOutOfBound), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.lowerOutOfBound), Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
            case R.id.reset:
                rest();
                break;
        }
    }

    private int getQuantity() {
        if (quantity.getText().toString().isEmpty()) {
            return 1;
        } else {
            return Integer.parseInt(quantity.getText().toString());
        }
    }

    private boolean chkqty(){
        if(quantity.getText().toString().isEmpty())
        {
            return true;
        }
        else
        {
            if(Integer.parseInt(quantity.getText().toString())>100) {
                Toast.makeText(getActivity(), getResources().getString(R.string.qtyrg), Toast.LENGTH_LONG).show();
                return false;
            }
            if(Integer.parseInt(quantity.getText().toString())==0) {
                Toast.makeText(getActivity(), getResources().getString(R.string.qtyz), Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
    }

    private boolean dispError() {
        if ((Integer.parseInt(upperLimit.getText().toString()) - Integer.parseInt(lowerLimit.getText().toString())) < getQuantity()) {
            Toast.makeText(getActivity(),
                    getResources().getStringArray(R.array.nonwr)[0]
                            + " "
                            + getQuantity()
                            + " "
                            + getResources().getStringArray(R.array.nonwr)[1]
                            + " "
                            + Integer.parseInt(upperLimit.getText().toString())
                            + " "
                            + getResources().getStringArray(R.array.nonwr)[2]
                            + " "
                            + Integer.parseInt(lowerLimit.getText().toString())
                    , Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void chk() {
        if (dispError()) {
            if (!chknonrp()) {
                new GetNum().execute();
            }
        }
    }

    private boolean chknonrp() {
        for (int i = 0; i < getQuantity(); i++) {
            for (int j = 0; j < getQuantity(); j++) {
                if (i != j) {
                    if (mRandomQuantity[i] == mRandomQuantity[j]) {
                        return false;
                    }
                }
            }
        }
        mCleared = 1;
        return true;
    }

    private void sortNo() {
        int temp;
        for (int i = 0; i < getQuantity() - 1; i++) {
            for (int j = 0; j < getQuantity() - i - 1; j++) {
                if (mRandomQuantity[j] > mRandomQuantity[j + 1]) {
                    temp = mRandomQuantity[j];
                    mRandomQuantity[j] = mRandomQuantity[j + 1];
                    mRandomQuantity[j + 1] = temp;
                }
            }
        }
    }

    private void setRandom() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getQuantity(); i++) {
            if ((i > 0) && (i < getQuantity())) {
                sb.append(", ");
            }
            sb.append(mRandomQuantity[i]);
        }
        randomNo.setText(sb.toString());
    }

    private void rest() {
        lowerLimit.setText("");
        upperLimit.setText("");
        quantity.setText("");
        nonrp.setChecked(false);
        sort.setChecked(false);
        randomNo.setText(getResources().getString(R.string.na));
    }

    private int checkBigger() {
        if (Integer.parseInt(lowerLimit.getText().toString()) > Integer.parseInt(upperLimit.getText().toString())) {
            return 1;
        }
        return 0;
    }

    private int notEmpty() {
        if (lowerLimit.getText().toString().isEmpty()) {
            return 0;
        }
        if (upperLimit.getText().toString().isEmpty()) {
            return 1;
        }
        return 2;
    }

    private int validateLower() {
        if (Integer.parseInt(lowerLimit.getText().toString()) < -2147483647) {
            return 1;
        }
        return 0;
    }

    private int validateUpper() {
        if (Integer.parseInt(lowerLimit.getText().toString()) > 2147483647) {
            return 1;
        }
        return 0;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!randomNo.getText().toString().equalsIgnoreCase(getResources().getString(R.string.na))) {
            outState.putString("random", randomNo.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }

    private class GetNum extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] params) {
            for (int i = 0; i < getQuantity(); i++) {
                Random rand = new Random();
                int randomNum = rand.nextInt((getUpperLimit - getLowerLimit) + 1) + getLowerLimit;
                mRandomQuantity[i] = randomNum;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (nonrp.isChecked()) {
                chk();
            }
            if (sort.isChecked()) {
                sortNo();
            }
            setRandom();
            super.onPostExecute(o);
        }
    }
}
