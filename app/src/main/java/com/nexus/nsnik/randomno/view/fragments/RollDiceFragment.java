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


import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.nexus.nsnik.randomno.BuildConfig;
import com.nexus.nsnik.randomno.MyApplication;
import com.nexus.nsnik.randomno.R;
import com.nexus.nsnik.randomno.view.adapters.DiceListAdapter;
import com.nexus.nsnik.randomno.viewmodel.RollDiceViewModel;
import com.squareup.leakcanary.RefWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class RollDiceFragment extends Fragment {

    @BindView(R.id.rollDiceQuantity)
    EditText mDiceQuantity;
    @BindView(R.id.rollDice)
    Button mDiceRoll;
    @BindView(R.id.rollDiceList)
    RecyclerView mDiceList;

    private Unbinder mUnbinder;
    private CompositeDisposable mCompositeDisposable;
    private RollDiceViewModel mRollDiceViewModel;
    private DiceListAdapter mDiceListAdapter;
    private Resources mResources;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dice_roll, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        listeners();
        return view;
    }

    private void initialize() {
        mCompositeDisposable = new CompositeDisposable();

        if (getActivity() != null) {
            mResources = getActivity().getResources();
        }

        mDiceListAdapter = new DiceListAdapter(getActivity(), null);
        mDiceList.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        mDiceList.setItemAnimator(new DefaultItemAnimator());
        mDiceList.setAdapter(mDiceListAdapter);

        mRollDiceViewModel = ViewModelProviders.of(this).get(RollDiceViewModel.class);
        mRollDiceViewModel.getDiceList().observe(this, integers -> mDiceListAdapter.modifyList(integers));
    }

    private void listeners() {
        mCompositeDisposable.add(RxView.clicks(mDiceRoll).subscribe(v -> {
            if (validateQuantity())
                mRollDiceViewModel.rollDice(getQuantity());
            else
                Toast.makeText(getActivity(), mResources.getString(R.string.rollErrorQuantityOutOfRange), Toast.LENGTH_SHORT).show();
        }, Throwable::printStackTrace));
    }

    private void cleanUp() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    private int getQuantity() {
        if (mDiceQuantity.getText().toString().isEmpty()) return 1;
        return Integer.parseInt(mDiceQuantity.getText().toString());
    }

    private boolean validateQuantity() {
        return mDiceQuantity.getText().toString().isEmpty() || Integer.parseInt(mDiceQuantity.getText().toString()) > 0 && Integer.parseInt(mDiceQuantity.getText().toString()) < 100;
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
