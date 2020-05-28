package com.moustick.sheepzic.utils.data;

public class SuiteUtils {

    public static String[] generate(int from, int to) {
        String[] data = new String[to];
        if (to > 10) {
            for (int i = from; i < 10; i++) {
                data[i] = "0" + i;
            }
            for (int i = 10; i < to; i++) {
                data[i] = "" + i;
            }
        } else {
            for (int i = from; i < to; i++) {
                data[i] = "0" + i;
            }
        }
        return data;
    }

}
