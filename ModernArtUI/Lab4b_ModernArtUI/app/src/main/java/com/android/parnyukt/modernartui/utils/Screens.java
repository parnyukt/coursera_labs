package com.android.parnyukt.modernartui.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;

/**
 * Created by tparnyuk on 16.07.15.
 */
public class Screens {
    public static void showConfirmationDialog(FragmentActivity a, String title, String msg, DialogInterface.OnClickListener[] ll, int negativeString, int positiveString) {
        AlertDialog.Builder b = new AlertDialog.Builder(a)
                .setTitle(title)
                .setMessage(msg)
                .setNegativeButton(negativeString, ll[0])
                .setPositiveButton(positiveString, ll[1])
                ;

        b.create().show();
    }
}
