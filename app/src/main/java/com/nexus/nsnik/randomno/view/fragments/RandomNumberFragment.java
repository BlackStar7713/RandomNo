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

package com.nexus.nsnik.randomno.view.fragments;


import androidx.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.nexus.nsnik.randomno.BuildConfig;
import com.nexus.nsnik.randomno.MyApplication;
import com.nexus.nsnik.randomno.R;
import com.nexus.nsnik.randomno.viewmodel.RandomNumberViewModel;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class RandomNumberFragment extends Fragment {

    @BindView(R.id.randomNumber)
    TextView mRandomNumber;
    @BindView(R.id.randomLowerLimit)
    EditText mLowerLimit;
    @BindView(R.id.randomUpperLimit)
    EditText mUpperLimit;
    @BindView(R.id.randomGenerateRandomNumber)
    Button mGenerateNumber;
    @BindView(R.id.randomResetFields)
    Button mResetFields;
    @BindView(R.id.randomNumberQuantity)
    EditText mQuantity;
    @BindView(R.id.randomNonRepeatingFlag)
    CheckBox mRepeatingFlag;
    @BindView(R.id.randomSortedFlag)
    CheckBox mSortedFlag;

    private Unbinder mUnbinder;
    private CompositeDisposable mCompositeDisposable;
    private RandomNumberViewModel mRandomNumberViewModel;
    private Resources mResources;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random_number, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        listeners();
        return view;
    }

    private void initialize() {
        if (getActivity() != null) {
            mResources = getActivity().getResources();
        }
        mRandomNumber.setMovementMethod(new ScrollingMovementMethod());
        mCompositeDisposable = new CompositeDisposable();
        mRandomNumberViewModel = ViewModelProviders.of(this).get(RandomNumberViewModel.class);
        mRandomNumberViewModel.getRandomNumberList().observe(this, integers -> mRandomNumber.setText(buildNumberString(integers, 0)));
    }

    private void listeners() {
        mCompositeDisposable.add(RxView.clicks(mGenerateNumber).subscribe(v -> {
            if (checkAllErrorCondition()) {
                mRandomNumberViewModel.generateRandomNumbers(
                        Integer.parseInt(mLowerLimit.getText().toString()),
                        Integer.parseInt(mUpperLimit.getText().toString()),
                        getQuantity(),
                        mRepeatingFlag.isChecked(),
                        mSortedFlag.isChecked());
            }
        }, Throwable::printStackTrace));
        mCompositeDisposable.add(RxView.clicks(mResetFields).subscribe(v -> resetFields(), Throwable::printStackTrace));
    }

    private boolean checkAllErrorCondition() {
        if (!notEmpty()) {
            Toast.makeText(getActivity(), mResources.getString(R.string.randomErrorNoLimit), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateLowerLimit()) {
            Toast.makeText(getActivity(), mResources.getString(R.string.randomErrorLowerLimitOutOfRange), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateUpperLimit()) {
            Toast.makeText(getActivity(), mResources.getString(R.string.randomErrorUpperLimitOutOfRange), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateIntegrity()) {
            Toast.makeText(getActivity(), mResources.getString(R.string.randomErrorLowerLimitGreaterThanUpper), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validateQuantity()) {
            Toast.makeText(getActivity(), mResources.getString(R.string.randomErrorQuantityOutOfRange), Toast.LENGTH_SHORT).show();
            return false;
        }
        return displayError();
    }

    @NonNull
    private String buildNumberString(List<Integer> numberList, int index) {
        if (index == numberList.size() - 1) return "" + numberList.get(index);
        return numberList.get(index) + ", " + buildNumberString(numberList, ++index);
    }

    private int getQuantity() {
        if (mQuantity.getText().toString().isEmpty()) return 1;
        return Integer.parseInt(mQuantity.getText().toString());
    }

    private boolean displayError() {
        if (mRepeatingFlag.isChecked() && (Integer.parseInt(mUpperLimit.getText().toString()) - Integer.parseInt(mLowerLimit.getText().toString())) < getQuantity()) {
            Toast.makeText(getActivity(),
                    getResources().getStringArray(R.array.randomErrorMoreDistinctThanQuantity)[0]
                            + " "
                            + getQuantity()
                            + " "
                            + getResources().getStringArray(R.array.randomErrorMoreDistinctThanQuantity)[1]
                            + " "
                            + Integer.parseInt(mUpperLimit.getText().toString())
                            + " "
                            + getResources().getStringArray(R.array.randomErrorMoreDistinctThanQuantity)[2]
                            + " "
                            + Integer.parseInt(mLowerLimit.getText().toString())
                    , Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validateIntegrity() {
        return Integer.parseInt(mUpperLimit.getText().toString()) > Integer.parseInt(mLowerLimit.getText().toString());
    }

    private boolean notEmpty() {
        return !mLowerLimit.getText().toString().isEmpty() && !mUpperLimit.getText().toString().isEmpty();
    }

    private boolean validateLowerLimit() {
        return Integer.parseInt(mLowerLimit.getText().toString()) >= Integer.MIN_VALUE;
    }

    private boolean validateUpperLimit() {
        return Integer.parseInt(mUpperLimit.getText().toString()) <= Integer.MAX_VALUE;
    }

    private boolean validateQuantity() {
        return mQuantity.getText().toString().isEmpty() || Integer.parseInt(mQuantity.getText().toString()) > 0 && Integer.parseInt(mQuantity.getText().toString()) < 100;
    }

    private void resetFields() {
        mLowerLimit.setText("");
        mUpperLimit.setText("");
        mQuantity.setText("");
        mRepeatingFlag.setChecked(false);
        mSortedFlag.setChecked(false);
        mRandomNumber.setText(getResources().getString(R.string.randomNumberStart));
    }

    private void cleanUp() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cleanUp();
        if (getActivity() != null && BuildConfig.DEBUG) {
            RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
            refWatcher.watch(this);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);
        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        }
        if (animation != null) {
            if (getView() != null) {
                getView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    if (getView() != null) {
                        getView().setLayerType(View.LAYER_TYPE_NONE, null);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
        return animation;
    }
}