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

package com.nexus.nsnik.randomno.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nexus.nsnik.randomno.util.RandomGenerationUtil;

import java.util.Arrays;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CoinTossViewModel extends ViewModel {

    private MutableLiveData<Integer> mCoinTossValue;

    CoinTossViewModel() {
        mCoinTossValue = new MutableLiveData<>();
    }

    public void tossCoin() {
        Single<Integer> single = Single.fromCallable(() -> RandomGenerationUtil.generateRandomNumber(0, 1)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                mCoinTossValue.setValue(integer);
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(Arrays.toString(e.getStackTrace()));
            }
        });
    }

    public MutableLiveData<Integer> getCoinTossValue() {
        return mCoinTossValue;
    }
}
