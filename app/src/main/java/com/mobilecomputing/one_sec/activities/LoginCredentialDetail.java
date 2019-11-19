package com.mobilecomputing.one_sec.activities;


import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class LoginCredentialDetail extends AppCompatActivity implements Serializable {
    private EditText txtValueName;
    private EditText txtValueUsername;
    private EditText txtValuePassword;
    private EditText txtValueWebsite;
    private ImageView imgFavicon;
    int itemID;
    MaterialButton btnUpdateCredentials;

    Drawable originalDrawable;
    KeyListener originalListener;
    DatabaseHelper myDB;

    private Cryptography cryptography;


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
        btnUpdateCredentials.setVisibility(View.GONE);
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

        return super.onOptionsItemSelected(item);
    }


}
