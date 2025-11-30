package com.examination_system.core.utils;

import java.util.ArrayList;
import java.util.Collections;

public class ArrayListUtils {
    public ArrayList<?> shuffle(ArrayList<?> list) {
        ArrayList<Object> shuffledList = new ArrayList<>(list);
        Collections.shuffle(shuffledList);
        return shuffledList;
    }
}
