package edu.byu.cs.familymap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by User on 8/6/2016.
 */
public class SettingsActivity extends AppCompatActivity {

    //**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private Toolbar mToolbar;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

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
            fragment = new SettingsFragment();
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
