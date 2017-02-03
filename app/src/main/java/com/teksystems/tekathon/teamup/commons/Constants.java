package com.teksystems.tekathon.teamup.commons;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Mayank Tiwari on 16/05/16.
 */
public class Constants {
    public static String str_latitude = "";
    public static String str_longitude = "";

    public static final String CARCREW_DRIVER_API_BASE = "carcrewuber_driverapp";
    public static final String CARCREW_GARAGE_API_BASE = "carcrewuber_garageapp";

    public static final String FULL_DATE_FORMAT = "EEEE, d MMM yyyy";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat DATE_FORMAT1 = new SimpleDateFormat("dd MMM yyyy");
    public static final SimpleDateFormat DATE_FORMAT2 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final String USER_LOGIN_KEY = "USER_LOGIN_KEY";
    public static final String USER_GARAGE_ID = "USER_GARAGE_ID";
    public static final String USER_GARAGE_USER_NAME = "USER_GARAGE_USER_NAME";
    public static final String USER_PHONE_NUMBER_KEY = "USER_PHONE_NUMBER_KEY";

    public static final String SAVED_CART_ID = "SAVED_CART_ID";
    public static final String DISPLAY_REVIEW_POPUP = "DISPLAY_REVIEW_POPUP";
    public static final String LAST_CART_REVIEWED = "LAST_CART_REVIEWED";
    public static final String SAVED_DRIVER_NAME = "SAVED_DRIVER_NAME";
    public static final String SAVED_DRIVER_ID = "SAVED_DRIVER_ID";
    public static final String SAVED_CAR_IMAGE = "SAVED_CAR_IMAGE";
    public static final String SAVED_CAR_ID = "SAVED_CAR_ID";
    public static final String SAVED_UNIQUE_CAR_ID = "SAVED_UNIQUE_CAR_ID";
    public static final String SAVED_MILES = "SAVED_MILES";
    public static final String SAVED_CAR_REGISTRATION_NUMBER = "SAVED_CAR_REGISTRATION_NUMBER";
    public static final String SAVED_CAR_NAME = "SAVED_CAR_NAME";
    public static final String SAVED_COMPANY_NAME = "SAVED_COMPANY_NAME";

    public static final String JWT_TOKEN = "JWT_TOKEN";

    public static final List<Integer> JWT_ERROR_CODES = new ArrayList<>(Arrays.asList(new Integer[]{401}));

    private static final String TAG = "Constants";

    public static byte[] imageCarrier = null;

    public static Date createDateObjectForFormattedString(String dateString) {
        Date parsedDate = new Date();
        if (dateString == null || dateString.isEmpty()) {
            Log.i(TAG, "parseDateString: Date N/A");
        } else {
            try {
                Date tempParsedDate = Constants.DATE_FORMAT.parse(dateString);
                if (parsedDate != null) {
                    parsedDate = tempParsedDate;
                }
            } catch (ParseException e) {
                Log.w(TAG, "parseDateString: Error parsing date" + dateString);
            }
        }
        return parsedDate;
    }

    public static int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void showServiceExceptionMessageAlert(Context context, String exceptionMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error while retrieving Events from Web Service");
        builder.setMessage("Cause: " + exceptionMessage);
        builder.setCancelable(true);
        builder.setNegativeButton("OK", null);
        builder.show();
    }

}
