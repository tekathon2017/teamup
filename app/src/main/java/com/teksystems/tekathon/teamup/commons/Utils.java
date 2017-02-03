package com.teksystems.tekathon.teamup.commons;

/**
 * Created by Mayank Tiwari on 18/06/16.
 */

public class Utils {

    private static final String TAG = "Utils";

    public static double parseDouble(String data) {
        double price = 0;
        if (data != null) {
            try {
                return Double.parseDouble(data);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    public static float parseFloat(String data) {
        float price = 0;
        if (data != null) {
            try {
                return Float.parseFloat(data);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return price;
    }

    public static String capitalizeWords(String line) {
        if (line == null) {
            return "";
        }
        line = line.trim().toLowerCase();
        String data[] = line.split("\\s");
        line = "";
        for (int i = 0; i < data.length; i++) {
            if (data[i].length() > 1)
                line = line + data[i].substring(0, 1).toUpperCase() + data[i].substring(1) + " ";
            else
                line = line + data[i].toUpperCase();
        }
        return line.trim();
    }
}
