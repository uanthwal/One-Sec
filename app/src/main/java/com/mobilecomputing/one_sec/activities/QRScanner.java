package com.mobilecomputing.one_sec.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.mobilecomputing.one_sec.R;

public class QRScanner extends AppCompatActivity {

    SurfaceView cameraPreview;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    boolean once = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscanner_layout);

        cameraPreview = findViewById(R.id.cameraPreview);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE | Barcode.DATA_MATRIX).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector).build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                try{
                    cameraSource.start(surfaceHolder);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }


            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if(qrCodes.size() != 0 && once){
                    once = false;
                    String secretKey = qrCodes.valueAt(0).displayValue;
                    Intent intent = new Intent(getApplicationContext(), LoginCredentialDetail.class);
                    intent.putExtra("NAME", getIntent().getStringExtra("NAME"));
                    intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                    intent.putExtra("PASSWORD", getIntent().getStringExtra("PASSWORD"));
                    intent.putExtra("WEBSITE", getIntent().getStringExtra("WEBSITE"));
                    intent.putExtra("SECRETKEY", secretKey);
                    startActivity(intent);
                    finish();
                    return;


//                    Toast.makeText(getApplicationContext(), qrCodes.valueAt(0).displayValue, Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1234) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Showing the toast message
                Toast.makeText(QRScanner.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(QRScanner.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
