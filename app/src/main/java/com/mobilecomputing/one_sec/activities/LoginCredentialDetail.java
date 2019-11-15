package com.mobilecomputing.one_sec.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mobilecomputing.one_sec.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginCredentialDetail extends AppCompatActivity {
    private EditText txtValueName;
    private EditText txtValueUsername;
    private EditText txtValuePassword;
    private ImageView imgFavicon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logindetails_layout);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        txtValueName = findViewById(R.id.txtValueName);
        txtValueUsername = findViewById(R.id.txtValueUsername);
        txtValuePassword = findViewById(R.id.txtValuePassword);
        imgFavicon = findViewById(R.id.imgFavicon);

        txtValueName.setFocusable(false);
        txtValueUsername.setFocusable(false);
        txtValuePassword.setFocusable(false);

        txtValueName.setText(getIntent().getStringExtra("NAME"));
        txtValueUsername.setText(getIntent().getStringExtra("USERNAME"));
        txtValuePassword.setText(getIntent().getStringExtra("PASSWORD"));

        try{
            String url = "https://favicongrabber.com/api/grab/";
            url += getIntent().getStringExtra("URL");
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
            System.out.println(content);
            JSONObject myResponse = new JSONObject(content.toString());
            JSONObject image = ((JSONArray)myResponse.get("icons")).getJSONObject(0);
            String imageURL = image.get("src").toString();
//            System.out.println(imageURL);
            Picasso.get().load(imageURL).into(imgFavicon);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


}
