package com.mobilecomputing.one_sec.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobilecomputing.one_sec.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewCredentials extends AppCompatActivity {

    DatabaseHelper myDB;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        fab  = findViewById(R.id.fab);

        myDB = new DatabaseHelper(this);
        System.out.println("test0");

//        ListView listView = findViewById(R.id.listView);

        final ArrayList<String> list = new ArrayList<>();
        final Cursor data = myDB.getListContents();

        if(data.getCount() == 0){
            Toast.makeText(ViewCredentials.this, "Database is empty", Toast.LENGTH_LONG).show();
        }
        else{
            while(data.moveToNext()){
                String name = data.getString(1);
                String username = data.getString(2);
                String password = data.getString(3);
                list.add(name + " " + username + " "+ password);
//                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
//                listView.setAdapter(listAdapter);

                mNames.add(name);
                String imageURL = getImageURL(name);
                if (imageURL!="")
                    mImageUrls.add(imageURL);
                else
                    System.out.println("Error retrieving image");

                System.out.println(mNames.size());
                System.out.println("im" + mImageUrls.size());

                initRecyclerView(myDB);
            }
        }

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println(list.get(i));
//                String[] values = list.get(i).split(" ");
//                Intent intent = new Intent();
//                intent.setClass( ViewCredentials.this, LoginCredentialDetail.class);
//                intent.putExtra("NAME", values[0]);
//                intent.putExtra("USERNAME", values[1]);
//                intent.putExtra("PASSWORD", values[2]);
//                intent.putExtra("URL", values[0]);
//                startActivity(intent);
//            }
//        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ViewCredentials.this, AddCredential.class);
                startActivity(intent);
            }
        });
    }

    private String getImageURL(String name){

        try{
            String url = "https://favicongrabber.com/api/grab/";
            url += name;
            System.out.println(url);
            URL faviconURL = new URL(url);
            HttpURLConnection con = (HttpURLConnection) faviconURL.openConnection();
            con.setRequestMethod("GET");
            System.out.println("picasso test");
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
            return imageURL;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    private void initRecyclerView(DatabaseHelper myDB){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNames, mImageUrls, this, myDB);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}
