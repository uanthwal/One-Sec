package com.mobilecomputing.one_sec.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobilecomputing.one_sec.R;

public class AddCredential extends AppCompatActivity {

    private EditText inputName;
    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnSaveCredentials;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcredential_layout);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        inputName = findViewById(R.id.inputName);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        btnSaveCredentials = findViewById(R.id.btnSaveCredentials);
        myDB = new DatabaseHelper(this);

        btnSaveCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(inputName.getText() + " " + inputUsername.getText() + " " + inputPassword.getText());

                if (inputName.length() != 0 && inputUsername.length() != 0 && inputPassword.length() != 0) {
                    AddData(inputName.getText().toString(), inputUsername.getText().toString(), inputPassword.getText().toString());
                } else {
                    Toast.makeText(AddCredential.this, "Fields are empty", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(AddCredential.this, ViewCredentials.class);
                startActivity(intent);

            }

        });

    }



    public void AddData(String name, String username, String password) {
        boolean insertData = myDB.addData(name, username, password);

        if (insertData) {
            Toast.makeText(AddCredential.this, "Data successfully inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(AddCredential.this, "Data insertion error. Check log for details", Toast.LENGTH_LONG).show();
        }
    }


}



