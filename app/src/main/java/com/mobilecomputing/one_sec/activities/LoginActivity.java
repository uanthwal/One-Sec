package com.example.one_sec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username_et,password_et;
    TextView textbox;
    //DbHandler dbHandler;
    MyDBHandler dbHandler;
    Button login_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
        textbox = findViewById(R.id.textView2);
        login_bt = findViewById(R.id.login_bt);
        //dbHandler = new DbHandler(this,null,null,1);
        dbHandler = new MyDBHandler(this, null, null, 1);
        //printUserInfo();


    }

    public void printUserInfo() {
        //String dbString = dbHandler.printUserInfo();
        String dbString = dbHandler.printUserInfo();
        //Toast.makeText(this, "user "+dbString+" created", Toast.LENGTH_LONG).show();  // all users
        username_et.setText("");
        password_et.setText("");
    }
    public void addUserInfo(View view){
        //Login login = new Login(username_et.getText().toString(),password_et.getText().toString());
        Login_Class login = new Login_Class(username_et.getText().toString(),password_et.getText().toString());
        Toast.makeText(this,"user "+login.get_username()+" created",Toast.LENGTH_LONG).show();
        //dbHandler.addUserInfo(login);
        dbHandler.addUser(login);
        printUserInfo();
    }




}
