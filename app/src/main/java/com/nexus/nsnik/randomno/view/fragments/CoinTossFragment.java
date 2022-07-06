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


import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import androidx.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nexus.nsnik.randomno.BuildConfig;
import com.nexus.nsnik.randomno.MyApplication;
import com.nexus.nsnik.randomno.R;
import com.nexus.nsnik.randomno.viewmodel.CoinTossViewModel;
import com.squareup.leakcanary.RefWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class CoinTossFragment extends Fragment {

    @BindView(R.id.coinImage)
    ImageView mCoinImage;
    @BindView(R.id.coinText)
    TextView mCoinText;

    private Unbinder mUnbinder;
    private CompositeDisposable mCompositeDisposable;
    private Resources mResources;
    private CoinTossViewModel mCoinTossViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coin_toss, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        listener();
        return view;
    }

    private void initialize() {
        mCompositeDisposable = new CompositeDisposable();
        if (getActivity() != null) {
            mResources = getActivity().getResources();
        }
        mCoinTossViewModel = ViewModelProviders.of(this).get(CoinTossViewModel.class);
        mCoinTossViewModel.getCoinTossValue().observe(this, this::animateCoin);
        mCoinTossViewModel.getCoinTossValue().observe(this, this::animateCoin);
    }

    private void animateCoin(Integer integer) {

        float scale = mResources.getDisplayMetrics().density;
        mCoinImage.setCameraDistance(8000 * scale);

        final AnimatorSet coinFlipOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_out);
        final AnimatorSet coinFlipIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_in);

        final AnimatorSet textFlipOut = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_out);
        final AnimatorSet textFlipIn = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.flip_in);

        coinFlipOut.setTarget(mCoinImage);
        coinFlipIn.setTarget(mCoinImage);
        textFlipIn.setTarget(mCoinText);
        textFlipOut.setTarget(mCoinText);

        textFlipOut.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setCoinFace(integer);
                coinFlipIn.start();
                textFlipIn.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        coinFlipOut.start();
        textFlipOut.start();

    }

    private void setCoinFace(Integer integer) {
        if (integer != null && integer == 0) {
            mCoinText.setText(mResources.getString(R.string.coinHeads));
            return;
        }
        mCoinText.setText(mResources.getString(R.string.coinTails));
    }

    private void listener() {
        mCompositeDisposable.add(RxView.clicks(mCoinImage).subscribe(v -> mCoinTossViewModel.tossCoin(), Throwable::printStackTrace));
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
