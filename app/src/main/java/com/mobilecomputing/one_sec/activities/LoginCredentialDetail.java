package com.mobilecomputing.one_sec.activities;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.mobilecomputing.one_sec.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class LoginCredentialDetail extends AppCompatActivity implements Serializable {
    private EditText txtValueName;
    private EditText txtValueUsername;
    private EditText txtValuePassword;
    private EditText txtValueWebsite;
    private ImageView imgFavicon;

    private MaterialButton btnUpdateCredentials;
    private ImageView btnGeneratePassword;
    private TextView txtLengthValue;
    private TextView txtGeneratedPassword;
    private SeekBar seekPasswordLength;
    private Button btnUsePassword;

    Drawable originalDrawable;
    KeyListener originalListener;
    DatabaseHelper myDB;

    private Cryptography cryptography;
    private int itemID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logindetails_layout);

        cryptography = Cryptography.getInstance();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        txtValueName = findViewById(R.id.txtValueName);
        txtValueUsername = findViewById(R.id.txtValueUsername);
        txtValuePassword = findViewById(R.id.txtValuePassword);
        txtValueWebsite = findViewById(R.id.txtValueWebsite);
        btnUpdateCredentials = findViewById(R.id.btnUpdateCredentials);
        imgFavicon = findViewById(R.id.imgFavicon);
        btnGeneratePassword = findViewById(R.id.btnGeneratePassword);
        txtLengthValue = findViewById(R.id.txtLengthValue);
        seekPasswordLength = findViewById(R.id.seekPasswordLength);
        txtGeneratedPassword = findViewById(R.id.txtGeneratedPassword);
        btnUsePassword = findViewById(R.id.btnUsePassword);

        String password = generatePassword(8);
        txtGeneratedPassword.setText(password);


        btnUpdateCredentials.setVisibility(View.INVISIBLE);
        btnUpdateCredentials.setEnabled(false);

        originalListener = txtValueName.getKeyListener();
        originalDrawable = txtValueName.getBackground();
        disableEditText(txtValueName);
        disableEditText(txtValueUsername);
        disableEditText(txtValuePassword);
        disableEditText(txtValueWebsite);

        String name = getIntent().getStringExtra("NAME");

        txtValueName.setText(name);
        txtValueUsername.setText(getIntent().getStringExtra("USERNAME"));
        txtValuePassword.setText(cryptography.decrypt(getIntent().getStringExtra("PASSWORD")));
        txtValueWebsite.setText(getIntent().getStringExtra("WEBSITE"));
        myDB = new DatabaseHelper(getApplicationContext());
        itemID = myDB.getIDFromName(name);

        btnUsePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtValuePassword.setText(txtGeneratedPassword.getText());
                System.out.println(itemID);
                String name = txtValueName.getText().toString();
                String username = txtValueUsername.getText().toString();
                String password = txtValuePassword.getText().toString();
                String website = txtValueWebsite.getText().toString();
                myDB.updateCredentials(itemID, name, username, cryptography.encrypt(password), website);
                Toast.makeText(getApplicationContext(), "Details updated", Toast.LENGTH_LONG).show();
                disableEditText(txtValueName);
                disableEditText(txtValueUsername);
                disableEditText(txtValuePassword);
                disableEditText(txtValueWebsite);
            }
        });

        btnGeneratePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = generatePassword(seekPasswordLength.getProgress());
                txtGeneratedPassword.setText(password);
            }
        });

        seekPasswordLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtLengthValue.setText(String.valueOf(i));
//                System.out.println(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String password = generatePassword(seekBar.getProgress());
                txtGeneratedPassword.setText(password);
            }
        });


        btnUpdateCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(itemID);
                String name = txtValueName.getText().toString();
                String username = txtValueUsername.getText().toString();
                String password = txtValuePassword.getText().toString();
                String website = txtValueWebsite.getText().toString();
                myDB.updateCredentials(itemID, name, username, cryptography.encrypt(password), website);
                Toast.makeText(getApplicationContext(), "Details updated", Toast.LENGTH_LONG).show();
                disableEditText(txtValueName);
                disableEditText(txtValueUsername);
                disableEditText(txtValuePassword);
                disableEditText(txtValueWebsite);


            }
        });

        try {
            String url = "https://favicongrabber.com/api/grab/";
            url += getIntent().getStringExtra("WEBSITE");
            System.out.println(url);
            URL faviconURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) faviconURL.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            JSONObject myResponse = new JSONObject(content.toString());
            JSONObject image = ((JSONArray) myResponse.get("icons")).getJSONObject(0);
            String imageURL = image.get("src").toString();
            Picasso.get().load(imageURL).into(imgFavicon);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String generatePassword(int length){
          final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
          final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
          final String NUMBER = "0123456789";
          final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

          String ALLOWED_CHAR = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
          int len = ALLOWED_CHAR.length();
          String result = "";
          Random rand = new Random();


          for(int i=0; i<length; i++){
              int pos = rand.nextInt(len);
              result += ALLOWED_CHAR.charAt(pos);
          }
          return result;

    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

    private void enableEditText(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setKeyListener(originalListener);
        editText.setBackground(originalDrawable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_item) {
            btnUpdateCredentials.setVisibility(View.VISIBLE);
            btnUpdateCredentials.setEnabled(true);
            enableEditText(txtValueName);
            enableEditText(txtValueUsername);
            enableEditText(txtValuePassword);
            enableEditText(txtValueWebsite);


//            String secretKey = "GAUD NDRV NY6E ZISK 7V66 BH6H 3YL7 I75D PQ3V QLVP EPRM BFY3 7YTQ";
//            System.setProperty("com.warrenstrange.googleauth.rng.algorithmProvider", "AndroidKeyStore");
//            System.setProperty("com.warrenstrange.googleauth.rng.algorithm", "HmacSHA1");
//
//            GoogleAuthenticator gAuth = new GoogleAuthenticator();
//            int code = gAuth.getTotpPassword(secretKey);
//
//            Toast.makeText(getApplicationContext(), "code :"+(code),Toast.LENGTH_LONG).show();
            return true;
        }

        else if (id == R.id.delete_item) {
            myDB.deleteCredential(txtValueName.getText().toString());
            Toast.makeText(getApplicationContext(), "Login deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), ViewCredentials.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


}
