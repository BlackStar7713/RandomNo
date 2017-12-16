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

package com.nexus.nsnik.randomno.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerationUtil {

    /**
     * @param minimum the start of range
     * @param maximum the end of range
     * @return the random number generated within the range
     */
    public static int generateRandomNumber(int minimum, int maximum) {
        return ThreadLocalRandom.current().nextInt(minimum, maximum + 1);
    }

    public static List<Integer> generateRandomNumbers(int minimum, int maximum, int quantity, boolean willRepeat, boolean isSorted) {
        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            int generatedNumber = generateRandomNumber(minimum, maximum);
            if (willRepeat) {
                while (checkIfRepetitionExists(generatedNumber, numberList)) {
                    generatedNumber = generateRandomNumber(minimum, maximum);
                }
            }
            numberList.add(generatedNumber);
        }
        if (isSorted) Collections.sort(numberList);
        return numberList;
    }

    private static boolean checkIfRepetitionExists(int number, List<Integer> list) {
        return list.indexOf(number) != -1;
    }
}
