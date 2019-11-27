package com.mobilecomputing.one_sec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilecomputing.one_sec.R;

import java.util.List;

public class CreditCardActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    Adapter adapter;
    TextView noItemText;
    SimpleDatabase simpleDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_card_activity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("One-Sec Credit Card Storage");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        noItemText = findViewById(R.id.noItemText);
        simpleDatabase = new SimpleDatabase(this);
        List<Card> allCards = simpleDatabase.getAllNotes();
        recyclerView = findViewById(R.id.allNotesList);

        if(allCards.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(allCards);
        }

    }

    private void displayList(List<Card> allCards) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, allCards);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add){
            Toast.makeText(this, "Add New card", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, AddCard.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Card> getAllCards = simpleDatabase.getAllNotes();
        if(getAllCards.isEmpty()){
            noItemText.setVisibility(View.VISIBLE);
        }else {
            noItemText.setVisibility(View.GONE);
            displayList(getAllCards);
        }


    }

}