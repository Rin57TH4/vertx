package com.rin.vertx.common.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollectionUtils {

    private static Random random = new Random();

    public static int[] shuffle(int[] arr) {
        int lenght = arr.length;
        int index = 0;
        int tmp = 0;
        for (int i = 0; i < lenght; i++) {
            index = random.nextInt(lenght);
            tmp = arr[index];
            arr[index] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    public static <T> T[] shuffle(T[] arr) {
        int lenght = arr.length;
        int index = 0;
        T tmp;
        for (int i = 0; i < lenght; i++) {
            index = random.nextInt(lenght);
            tmp = arr[index];
            arr[index] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    public static <T> boolean isEmpty(T... cl) {
        if (cl == null) {
            return true;
        }
        return cl.length == 0;
    }

    public static boolean isEmpty(int[] cl) {
        if (cl == null) {
            return true;
        }
        return cl.length == 0;
    }

    public static <T> boolean isEmpty(List<T> cl) {
        if (cl == null) {
            return true;
        }
        return cl.size() == 0;
    }

    public static <T> List<T> cpy(List<T> cards) {
        List<T> rs = new ArrayList<T>();
        int size = cards.size();
        for (int i = 0; i < size; i++) {
            rs.add(cards.get(i));
        }
        return rs;
    }

    public static <T> List<T> cpy(T[] input) {
        List<T> rs = new ArrayList<T>();
        int size = input.length;
        for (int i = 0; i < size; i++) {
            rs.add(input[i]);
        }
        return rs;
    }

    public static int[] cpy(int[] origins) {
        int[] rs = new int[origins.length];
        int size = origins.length;
        for (int i = 0; i < size; i++) {
            rs[i] = origins[i];
        }
        return rs;
    }

    public static long[] cpy(long[] origins) {
        long[] rs = new long[origins.length];
        int size = origins.length;
        for (int i = 0; i < size; i++) {
            rs[i] = origins[i];
        }
        return rs;
    }

    public static <T> T[] cpy(T[] origins, T[] outs) {
        int size = origins.length;
        for (int i = 0; i < size; i++) {
            outs[i] = origins[i];
        }
        return outs;
    }

    public static String log(long[] wm) {
        if (wm != null) {
            String s = "[";
            for (int i = 0; i < wm.length; i++) {
                s += wm[i] + " ,";
            }
            s += "]";
            return s;
        }
        return null;
    }

    public static int[] toArrayInt(List<Integer> input) {
        int[] rs = new int[input.size()];
        int size = input.size();
        for (int i = 0; i < size; i++) {
            rs[i] = input.get(i);
        }
        return rs;
    }

    public static String logInt(int[] winCases) {
        String s = "[";
        int size = winCases.length;
        for (int i = 0; i < size; i++) {
            s += winCases[i] + (i == size - 1 ? "" : ",");
        }
        s += "]";
        return s;
    }
}
