package com.teksystems.tekathon.teamup.commons;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * In Case of fragment you can use
 * Fragment f = getActivity().getSupportFragmentManager().findFragmentById(R.id.frame_container);
 * if (f instanceof MyAccountFragment) {
 * marshMallowPermissionFragment = new MarshMallowPermission(mContext, f);
 * }
 * <p>
 * In Case of activity
 * marshMallowPermissionFragment = new MarshMallowPermission(mContext);
 * <p>
 * <p>
 * Also you should in your Override in your activity or in fragment
 *
 * @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * if (requestCode == marshMallowPermissionFragment.READ_STORAGE_PERMISSION_REQUEST_CODE) {
 * if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
 * // Permission granted.
 * <p>
 * } else {
 * // User refused to grant permission.
 * }
 * }
 * }
 */
public class MarshMallowPermission {

    public static final int RECORD_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 4;
    public static final int READ_CONTACT_PERMISSION_REQUEST_CODE = 5;
    public static final int READ_PHONE_STATE_PERMISSION_REQUEST_CODE = 6;
    public static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 7;
    public static final int GET_ACCOUNTS_PERMISSIONS_REQUEST_CODE = 8;
    public static final int RECEIVE_SMS_PERMISSIONS_REQUEST_CODE = 9;
    public static final int READ_SMS_PERMISSIONS_REQUEST_CODE = 10;
    public static final int READ_RECEIVE_SMS_PERMISSIONS_REQUEST_CODE = 11;
    public static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 12;
    private final Context context;

    Fragment fragment;
    private String TAG = MarshMallowPermission.class.getSimpleName();

    /**
     * @param context
     * @param fragment
     */
    public MarshMallowPermission(Context context, Fragment fragment) {
        Log.e(TAG, "MarshMallowPermission() called with: " + "context = [" + context + "], activity = [" + fragment + "]");
        this.fragment = fragment;
        this.context = context;
    }

    /**
     * @param context
     */
    public MarshMallowPermission(Context context) {
        this.context = context;
    }

    public void requestPermissionForFragmentRecord() {
        fragment.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_REQUEST_CODE);
    }


    public void requestPermissionForAudioRecording() {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECORD_AUDIO},
                RECORD_PERMISSION_REQUEST_CODE);
    }

    public boolean isAudioRecordingPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    public void requestPermissionForFragmentLocation() throws Exception {
        try {
            fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "requestPermissionForFragmentLocation: ", e);
            throw e;
        }
    }

    public void requestPermissionForLocation() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isLocationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean isAccountsPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.GET_ACCOUNTS);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    public void requestPermissionForFragmentContact() throws Exception {
        try {
            fragment.requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACT_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestPermissionForAccount() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.GET_ACCOUNTS},
                    GET_ACCOUNTS_PERMISSIONS_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestSmsRead_ReceivePermission() throws Exception {
        try {
            if (fragment == null) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.READ_SMS}, READ_RECEIVE_SMS_PERMISSIONS_REQUEST_CODE);
            } else {
                fragment.requestPermissions(
                        new String[]{Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.READ_SMS}, READ_RECEIVE_SMS_PERMISSIONS_REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isSmsRead_ReceivePermissionGranted() throws Exception {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS)
                    == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
                            == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestSmsRead_ReceivePermission();
                return false;
            }
        } else {
            return true;
        }
    }

    public void requestPermissionForContact() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_CONTACTS},
                    READ_CONTACT_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestPermissionForCall() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isCallPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean isContactPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    public void requestPermissionForFragmentReadPhone() throws Exception {
        try {
            fragment.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestPermissionForReadPhoneState() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE},
                    READ_PHONE_STATE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isReadPhoneStatePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    public void requestPermissionForFragmentReadExtertalStorage() throws Exception {
        try {
            fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isReadExtertalStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    public void requestPermissionForFragmentWriteExternalStorage() throws Exception {
        try {
            fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestPermissionForWriteExternalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isWriteExternalStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    public void requestPermissionForFragmentCamera() throws Exception {

        try {
            fragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void requestPermissionForCamera() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.CAMERA);
            int result_external_storage = context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED && result_external_storage == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }
}
