package com.mobilecomputing.one_sec.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.Manifest;
import android.media.Image;
import android.os.Build;
import android.os.CancellationSignal;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.mobilecomputing.one_sec.R;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context context;
    ImageView fingerprintImage;

    public FingerprintHandler(Context mContext) {
        context = mContext;
        fingerprintImage = ((Activity)context).findViewById(R.id.imageView);
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override


    public void onAuthenticationError(int errMsgId, CharSequence errString) {


//        Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }

    @Override


    public void onAuthenticationFailed() {
        fingerprintImage.setImageResource(R.mipmap.fingerprint_fail);
        shakeImage();
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();
    }

    public void shakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(context, R.anim.linear_interpolator);
        fingerprintImage.startAnimation(shake);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }@Override


    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {
        fingerprintImage.setImageResource(R.mipmap.fingerprint_success);
        Toast.makeText(context, "Authentication success!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

}