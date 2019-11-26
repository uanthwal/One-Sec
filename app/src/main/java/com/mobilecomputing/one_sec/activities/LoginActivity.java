package com.mobilecomputing.one_sec.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilecomputing.one_sec.R;


public class LoginActivity extends AppCompatActivity {

    EditText username_et,password_et;
    TextView textbox;
    //DbHandler dbHandler;
    MyDBHandler dbHandler;
    Button login_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
        textbox = findViewById(R.id.textView2);
        login_bt = findViewById(R.id.login_bt);
        dbHandler = new MyDBHandler(this, null, null, 1);
        login_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag=dbHandler.checkUserInfo(username_et.getText().toString());
                if(flag==0){
                    Toast.makeText(getApplicationContext(), "User Do not Exist", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    // print all users list
                    String dbString = dbHandler.printUserInfo();
                    Toast.makeText(getApplicationContext(),dbString,Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void signupPage(View view) {
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(intent);
    }

}
