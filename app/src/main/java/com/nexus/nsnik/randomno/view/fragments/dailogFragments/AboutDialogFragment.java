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

package com.nexus.nsnik.randomno.view.fragments.dailogFragments;


import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nexus.nsnik.randomno.BuildConfig;
import com.nexus.nsnik.randomno.MyApplication;
import com.nexus.nsnik.randomno.R;
import com.nexus.nsnik.randomno.model.LibraryObject;
import com.squareup.leakcanary.RefWatcher;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public class AboutDialogFragment extends DialogFragment {

    private final List<LibraryObject> mLibraryList = Arrays.asList(

            LibraryObject
                    .builder()
                    .libraryName("Android Support Library")
                    .libraryLink("https://github.com/android/platform_frameworks_support")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("Android Testing Support Library")
                    .libraryLink("https://google.github.io/android-testing-support-library/")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("Android Lifecycle Components")
                    .libraryLink("https://developer.android.com/topic/libraries/architecture/lifecycle.html")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("AutoValue")
                    .libraryLink("https://github.com/google/auto/tree/master/value")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("Block Canary")
                    .libraryLink("https://github.com/markzhai/AndroidPerformanceMonitor")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("Butter Knife")
                    .libraryLink("https://github.com/JakeWharton/butterknife")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("FireBase")
                    .libraryLink("https://firebase.google.com/terms/")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("Jetbrains Annotation")
                    .libraryLink("https://mvnrepository.com/artifact/org.jetbrains/annotations/13.0")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("jUnit4")
                    .libraryLink("https://github.com/junit-team/junit4")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("Leak Canary")
                    .libraryLink("https://github.com/square/leakcanary")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("Mockito")
                    .libraryLink("https://github.com/mockito/mockito")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("Robelectric")
                    .libraryLink("https://github.com/robolectric/robolectric")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("RollBar")
                    .libraryLink("https://github.com/rollbar/rollbar-android")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("RxAndroid")
                    .libraryLink("https://github.com/ReactiveX/RxAndroid")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("RxBinding")
                    .libraryLink("https://github.com/JakeWharton/RxBinding")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("RxJava")
                    .libraryLink("https://github.com/ReactiveX/RxJava")
                    .build(),

            LibraryObject
                    .builder()
                    .libraryName("Timber")
                    .libraryLink("https://github.com/JakeWharton/timber")
                    .build()
    );

    @BindView(R.id.aboutApplication)
    TextView mAboutApplication;
    @BindView(R.id.aboutNikhil)
    TextView mAboutNikhil;
    @BindView(R.id.aboutSourceCodeLink)
    TextView mSourceCodeLink;
    @BindView(R.id.aboutLicenseLink)
    Button mLicenseLink;
    @BindView(R.id.aboutLibraryLink)
    Button mLibraryLink;

    private Unbinder mUnbinder;
    private CompositeDisposable mCompositeDisposable;
    private Resources mResources;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_dialog, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        listeners();
        return view;
    }

    private void initialize() {
        if (getActivity() != null) {
            mResources = getActivity().getResources();
        }
        mCompositeDisposable = new CompositeDisposable();
        mAboutNikhil.setMovementMethod(LinkMovementMethod.getInstance());
        mSourceCodeLink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void listeners() {
        mCompositeDisposable.add(RxView.clicks(mLicenseLink).subscribe(v -> openUrl(mResources.getString(R.string.aboutLicenseUrl)), Throwable::printStackTrace));
        mCompositeDisposable.add(RxView.clicks(mLibraryLink).subscribe(v -> showLibrariesList(), Throwable::printStackTrace));
    }

    private void showLibrariesList() {
        if (getActivity() != null) {
            AlertDialog.Builder choosePath = new AlertDialog.Builder(getActivity());
            choosePath.setTitle(getActivity().getResources().getString(R.string.aboutLibrariesHead));
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
            for (LibraryObject libraryObject : mLibraryList) {
                arrayAdapter.add(libraryObject.libraryName());
            }
            choosePath.setAdapter(arrayAdapter, (dialog, position) -> openUrl(mLibraryList.get(position).libraryLink()));
            choosePath.create().show();
        }
    }

    private void openUrl(String url) {
        Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (getActivity() != null && urlIntent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivity(urlIntent);
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

}
