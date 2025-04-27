package ru.mirea.AleevAV.myapplication;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressBarClass extends ProgressDialog {

    public ProgressBarClass(Context context) {
        super(context);
        configureDialog();
    }

    public ProgressBarClass(Context context, int theme) {
        super(context, theme);
        configureDialog();
    }

    private void configureDialog() {
        setTitle("ProgressDialog");
        setMessage("Loading...");
        setCancelable(false);

    }


    public void showWithSafetyCheck() {
        if (!isShowing()) {
            show();
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }
}