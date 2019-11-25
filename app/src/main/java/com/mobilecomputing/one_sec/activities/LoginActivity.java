package com.mobilecomputing.one_sec.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mobilecomputing.one_sec.R;
import com.mobilecomputing.one_sec.db.AppDbHandler;
import com.mobilecomputing.one_sec.model.LoginInfo;
import com.mobilecomputing.one_sec.utils.SpUtil;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    AppDbHandler dbHandler;
    Button buttonLogin;
    ImageView showHidePass, mainLogo;
    boolean isPassHidden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        isPassHidden = true;
        mainLogo = findViewById(R.id.logo_imgvw);
        setMainLogoAnimation(3000);
        editTextUsername = findViewById(R.id.edit_txt_username);
        editTextPassword = findViewById(R.id.edit_txt_password);
        showHidePass = findViewById(R.id.show_hide_pass);
        buttonLogin = findViewById(R.id.login_btn);
        dbHandler = new AppDbHandler(this, null, null, 1);
        if (SpUtil.getInstance().getString("username", "-1").equals("-1")) {
            SpUtil.getInstance().clear();
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
//        dbHandler.addDummyUser();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flag = 0;
                setMainLogoAnimation(1000);
                if (editTextUsername.getText().toString().equals("")) {
                    editTextUsername.setError("Please enter username");
                    flag = -1;
                }
                if (editTextPassword.getText().toString().equals("")) {
                    editTextPassword.setError("Please enter password");
                    flag = -1;
                }
                if (flag == 0) {
                    LoginInfo loginInfo = dbHandler.getUser(editTextUsername.getText().toString(), editTextPassword.getText().toString());
                    if (loginInfo.getUsername() != null) {
                        SpUtil.getInstance().putString("username", loginInfo.getUsername());
                        SpUtil.getInstance().putString("dob", loginInfo.getDob());
                        SpUtil.getInstance().putString("mob_num", loginInfo.getMobNum());
                        SpUtil.getInstance().putString("email", loginInfo.getEmail());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        onShakeImage();
                    }
                }
            }
        });
        showHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPassHidden) {
                    isPassHidden = false;
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showHidePass.setImageResource(R.mipmap.visible);
                } else {
                    isPassHidden = true;
                    showHidePass.setImageResource(R.mipmap.hide);
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    private void setMainLogoAnimation(int duration) {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(duration);
        rotate.setInterpolator(new LinearInterpolator());
        mainLogo.startAnimation(rotate);
    }

    public void onShakeImage() {
        Animation shake;
        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.linear_interpolator);
        mainLogo.startAnimation(shake);
    }
}
