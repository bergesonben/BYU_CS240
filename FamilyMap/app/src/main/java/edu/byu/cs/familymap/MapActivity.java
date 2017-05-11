package edu.byu.cs.familymap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by User on 8/4/2016.
 */
public class MapActivity extends AppCompatActivity {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private static final String EXTRA_EVENT_ID = "edu.byu.cs.familymap.event_id";
    public static final String ACTIVITY_NAME = "Map_Activity";

    private Toolbar mToolbar;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

    public static Intent newIntent(Context packageContext, String personId) {
        Intent intent = new Intent(packageContext, MapActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, personId);
        return intent;
    }

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           REACTIVE METHODS
//**************************************************************************************************
//region REACTIVE METHODS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mToolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null) {
            String eventId = (String) getIntent().getSerializableExtra(EXTRA_EVENT_ID);
            fragment = MapFragment.newInstance(eventId, ACTIVITY_NAME);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

//endregion REACTIVE METHODS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
//endregion PRIVATE METHODS
}
