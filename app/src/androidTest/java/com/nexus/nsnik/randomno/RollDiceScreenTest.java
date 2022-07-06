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

package com.nexus.nsnik.randomno;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.contrib.ViewPagerActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.nexus.nsnik.randomno.view.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RollDiceScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void scrollToRollDiceFragment() {
        onView(withId(R.id.mainViewPager)).perform(ViewPagerActions.scrollToPage(1, true));
    }

    @Test
    public void rollSingleDiceTest() {
        onView(withId(R.id.rollDice)).perform(click());
        onView(withId(R.id.rollDiceList)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void rollNineDiceTest() {
        onView(withId(R.id.rollDiceQuantity)).perform(typeText("9"));
        onView(withId(R.id.rollDice)).perform(click());
        onView(withId(R.id.rollDiceList)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
    }

}
