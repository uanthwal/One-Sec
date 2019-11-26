package com.mobilecomputing.one_sec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.mobilecomputing.one_sec.R;

public class SignupActivity extends AppCompatActivity {

    EditText username_et2, password_et2, email_et2;
    Button signup_btn;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        username_et2 = findViewById(R.id.username_et2);
        password_et2 =  findViewById(R.id.password_et2);
        email_et2 = findViewById(R.id.email_et2);
        signup_btn =findViewById(R.id.signup_btn);
        dbHandler = new MyDBHandler(this, null, null, 1);

        //signup button check
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),username_et2.getText().toString(),Toast.LENGTH_LONG).show();
                int flag=dbHandler.checkUserInfo(username_et2.getText().toString());
                if(flag!=1) {
                    Login_Class login = new Login_Class(username_et2.getText().toString(),password_et2.getText().toString(),email_et2.getText().toString());
                    dbHandler.addUserInfo(login);
                    Toast.makeText(getApplicationContext(),"User "+login.get_username()+" Created",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"User Already Exists",Toast.LENGTH_LONG).show();
                }
                resetUserInfo();
            }
        });
    }

    public void resetUserInfo() {
        //String dbString = dbHandler.printUserInfo();
        //String dbString = dbHandler.printUserInfo();
        //Toast.makeText(this, "user "+dbString+" created", Toast.LENGTH_LONG).show();  // all users
        username_et2.setText("");
        password_et2.setText("");
        email_et2.setText("");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}
