package com.example.thom.googlemapstest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Contact extends ActionBarActivity {
    private ArrayList<String> contacts;
    private ArrayAdapter<String> contactsAdapter;
    private ListView lvContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        lvContacts = (ListView) findViewById(R.id.lvContacts);
        contacts = new ArrayList<String>();
        contactsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
        lvContacts.setAdapter(contactsAdapter);

        // Populate Contacts List with example contacts
        contacts.add("Alex Smith");
        contacts.add("Bart Simpson");
        contacts.add("Calvin Cassidy");
        contacts.add("Danielle Foster");
        contacts.add("Erica Gomez");
        contacts.add("Franklin Harris");
        contacts.add("Gwen Stacy");
        contacts.add("Harry Thompson");
        contacts.add("Jerica Smiles");
        contacts.add("Kirk Captain");
        contacts.add("Lenny Kabasinski");
        contacts.add("Maya Fujiwara");
        contacts.add("Nicole Cohen");
        contacts.add("Walt Junior");
        contacts.add("Zelda Fitz");

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemsSelected(MenuItem item){
        int id = item.getIemId();
        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemsSelected(item);
    }
    */
}
