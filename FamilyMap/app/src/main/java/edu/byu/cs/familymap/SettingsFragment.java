package edu.byu.cs.familymap;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import edu.byu.cs.familymap.Model.FamilyMapData;
import edu.byu.cs.familymap.Model.FamilyMapSettings;

/**
 * Created by User on 8/6/2016.
 */
public class SettingsFragment extends Fragment {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private static final String TAG = "SettingsActivity";

    private FamilyMapData mData;
    private FamilyMapSettings mSettings;
    private View mLifeStoryLinesView;
    private View mFamilyTreeLinesView;
    private View mSpouseLinesView;
    private View mMapTypeView;
    private View mResyncDataView;
    private View mLogoutView;


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mData = FamilyMapData.get();
        mSettings = FamilyMapSettings.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        initializeSettingsView(v);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.blank_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    private void initializeSettingsView(View view) {

        initializeLifeStoryLines(view);
        initializeFamilyTreeLines(view);
        initializeSpouseLines(view);
        initializeMapType(view);
        initializeResyncData(view);
        initializeLogout(view);
    }

    private void initializeLifeStoryLines(View view) {
        mLifeStoryLinesView = view.findViewById(R.id.settings_life_story_lines);
        ((TextView) mLifeStoryLinesView.findViewById(R.id.settings_value)).setText("Life Story Lines");
        ((TextView) mLifeStoryLinesView.findViewById(R.id.settings_description)).setText("SHOW LIFE STORY LINES");
        Spinner colorSpinner = (Spinner) mLifeStoryLinesView.findViewById(R.id.settings_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
        switch (mSettings.getLifeStoryLinesColor()) {
            case Color.GREEN:
                colorSpinner.setSelection(0);
                break;
            case Color.BLUE:
                colorSpinner.setSelection(1);
                break;
            case Color.RED:
                colorSpinner.setSelection(2);
                break;
            default:
                colorSpinner.setSelection(0);
                break;
        }
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSettings.setLifeStoryLinesColor(Color.GREEN);
                        break;
                    case 1:
                        mSettings.setLifeStoryLinesColor(Color.BLUE);
                        break;
                    case 2:
                        mSettings.setLifeStoryLinesColor(Color.RED);
                        break;
                    default:
                        mSettings.setLifeStoryLinesColor(Color.GREEN);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Switch settingsSwitch = (Switch) mLifeStoryLinesView.findViewById(R.id.settings_switch);
        if (mSettings.isDrawLifeStoryLines())
            settingsSwitch.setChecked(true);
        else
            settingsSwitch.setChecked(false);

        settingsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mSettings.setDrawLifeStoryLines(true);
                else
                    mSettings.setDrawLifeStoryLines(false);
            }
        });

    }

    private void initializeFamilyTreeLines(View view) {
        mFamilyTreeLinesView = view.findViewById(R.id.settings_family_tree_lines);
        ((TextView) mFamilyTreeLinesView.findViewById(R.id.settings_value)).setText("Family Tree Lines");
        ((TextView) mFamilyTreeLinesView.findViewById(R.id.settings_description)).setText("SHOW TREE LINES");
        Spinner colorSpinner = (Spinner) mFamilyTreeLinesView.findViewById(R.id.settings_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
        switch (mSettings.getFamilyTreeLinesColor()) {
            case Color.GREEN:
                colorSpinner.setSelection(0);
                break;
            case Color.BLUE:
                colorSpinner.setSelection(1);
                break;
            case Color.RED:
                colorSpinner.setSelection(2);
                break;
            default:
                colorSpinner.setSelection(1);
                break;
        }
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSettings.setFamilyTreeLinesColor(Color.GREEN);
                        break;
                    case 1:
                        mSettings.setFamilyTreeLinesColor(Color.BLUE);
                        break;
                    case 2:
                        mSettings.setFamilyTreeLinesColor(Color.RED);
                        break;
                    default:
                        mSettings.setFamilyTreeLinesColor(Color.BLUE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Switch settingsSwitch = (Switch) mFamilyTreeLinesView.findViewById(R.id.settings_switch);
        if (mSettings.isDrawFamilyTreeLines()) settingsSwitch.setChecked(true);
        else settingsSwitch.setChecked(false);
        settingsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mSettings.setDrawFamilyTreeLines(true);
                else
                    mSettings.setDrawFamilyTreeLines(false);
            }
        });
    }

    private void initializeSpouseLines(View view) {
        mSpouseLinesView = view.findViewById(R.id.settings_spouse_lines);
        ((TextView) mSpouseLinesView.findViewById(R.id.settings_value)).setText("Spouse Lines");
        ((TextView) mSpouseLinesView.findViewById(R.id.settings_description)).setText("SHOW SPOUSE LINES");
        Spinner colorSpinner = (Spinner) mSpouseLinesView.findViewById(R.id.settings_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.colors_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter);
        switch (mSettings.getSpouseLinesColor()) {
            case Color.GREEN:
                colorSpinner.setSelection(0);
                break;
            case Color.BLUE:
                colorSpinner.setSelection(1);
                break;
            case Color.RED:
                colorSpinner.setSelection(2);
                break;
            default:
                colorSpinner.setSelection(2);
                break;
        }
        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSettings.setSpouseLinesColor(Color.GREEN);
                        break;
                    case 1:
                        mSettings.setSpouseLinesColor(Color.BLUE);
                        break;
                    case 2:
                        mSettings.setSpouseLinesColor(Color.RED);
                        break;
                    default:
                        mSettings.setSpouseLinesColor(Color.RED);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Switch settingsSwitch = (Switch) mSpouseLinesView.findViewById(R.id.settings_switch);
        if (mSettings.isDrawSpouseLines())
            settingsSwitch.setChecked(true);
        else
            settingsSwitch.setChecked(false);
        settingsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mSettings.setDrawSpouseLines(true);
                else
                    mSettings.setDrawSpouseLines(false);
            }
        });
    }

    private void initializeMapType(View view) {
        mMapTypeView = view.findViewById(R.id.settings_map_type);
        ((TextView) mMapTypeView.findViewById(R.id.settings_value)).setText("Map Type");
        ((TextView) mMapTypeView.findViewById(R.id.settings_description)).setText("BACKGROUND DISPLAY ON MAP");
        Spinner mapTypeSpinner = (Spinner) mMapTypeView.findViewById(R.id.settings_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.map_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(adapter);
        switch (mSettings.getMapType()) {
            case GoogleMap.MAP_TYPE_NORMAL:
                mapTypeSpinner.setSelection(0);
                break;
            case GoogleMap.MAP_TYPE_HYBRID:
                mapTypeSpinner.setSelection(1);
                break;
            case GoogleMap.MAP_TYPE_SATELLITE:
                mapTypeSpinner.setSelection(2);
                break;
            case GoogleMap.MAP_TYPE_TERRAIN:
                mapTypeSpinner.setSelection(3);
                break;
            default:
                mapTypeSpinner.setSelection(0);
                break;
        }
        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mSettings.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        mSettings.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 2:
                        mSettings.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 3:
                        mSettings.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    default:
                        mSettings.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mMapTypeView.findViewById(R.id.settings_switch).setVisibility(View.GONE);
    }

    private void initializeResyncData(View view) {
        mResyncDataView = view.findViewById(R.id.settings_resync_data);
        ((TextView) mResyncDataView.findViewById(R.id.settings_value)).setText("Re-sync Data");
        ((TextView) mResyncDataView.findViewById(R.id.settings_description)).setText("FROM FAMILYMAP SERVICE");
        mResyncDataView.findViewById(R.id.settings_spinner).setVisibility(View.GONE);
        mResyncDataView.findViewById(R.id.settings_switch).setVisibility(View.GONE);
        mResyncDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.clearAll();
                new DataSyncTask().execute();
            }
        });
    }

    private void initializeLogout(View view) {
        mLogoutView = view.findViewById(R.id.settings_logout);
        ((TextView) mLogoutView.findViewById(R.id.settings_value)).setText("Logout");
        ((TextView) mLogoutView.findViewById(R.id.settings_description)).setText("RETURNS TO LOGIN SCREEN");
        mLogoutView.findViewById(R.id.settings_spinner).setVisibility(View.GONE);
        mLogoutView.findViewById(R.id.settings_switch).setVisibility(View.GONE);
        mLogoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.clearAll();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

//endregion PRIVATE METHODS



//**************************************************************************************************
//                                           PRIVATE CLASS
//**************************************************************************************************
//region PRIVATE CLASS

    private class DataSyncTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return new FamilyMapConnector().syncData();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(getActivity(), R.string.data_sync_error, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }
    }

//endregion
}
