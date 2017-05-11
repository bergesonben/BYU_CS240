package edu.byu.cs.familymap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import edu.byu.cs.familymap.Model.FamilyMapFilters;

/**
 * Created by User on 8/6/2016.
 */
public class FilterFragment extends Fragment {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private FamilyMapFilters mFilters;

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

        mFilters = FamilyMapFilters.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        initializeBaptismFilter(v);
        initializeBirthEvents(v);
        initializeCensusEvents(v);
        initializeChristeningEvents(v);
        initializeDeathEvents(v);
        initializeMarriageEvents(v);
        initializeFathersSide(v);
        initializeMothersSide(v);
        initializeMaleEvents(v);
        initializeFemaleEvents(v);

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

    private void initializeBaptismFilter(View view) {
        View baptismFilter = view.findViewById(R.id.filter_baptism_events);
        ((TextView) baptismFilter.findViewById(R.id.filter_value)).setText("Baptism Events");
        ((TextView) baptismFilter.findViewById(R.id.filter_description)).setText("FILTER BY BAPTISM EVENTS");

        final Switch filterSwitch = (Switch) baptismFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowBaptismEvents())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowBaptismEvents(true);
                else
                    mFilters.setShowBaptismEvents(false);
            }
        });

        baptismFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowBaptismEvents(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowBaptismEvents(true);
                }
            }
        });
    }

    private void initializeBirthEvents(View view) {
        View birthFilter = view.findViewById(R.id.filter_birth_events);
        ((TextView) birthFilter.findViewById(R.id.filter_value)).setText("Birth Events");
        ((TextView) birthFilter.findViewById(R.id.filter_description)).setText("FILTER BY BIRTH EVENTS");

        final Switch filterSwitch = (Switch) birthFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowBirthEvents())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowBirthEvents(true);
                else
                    mFilters.setShowBirthEvents(false);
            }
        });

        birthFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowBirthEvents(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowBirthEvents(true);
                }
            }
        });
    }

    private void initializeCensusEvents(View view) {
        View censusFilter = view.findViewById(R.id.filter_census_events);
        ((TextView) censusFilter.findViewById(R.id.filter_value)).setText("Census Events");
        ((TextView) censusFilter.findViewById(R.id.filter_description)).setText("FILTER BY CENSUS EVENTS");

        final Switch filterSwitch = (Switch) censusFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowCensusEvents())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowCensusEvents(true);
                else
                    mFilters.setShowCensusEvents(false);
            }
        });

        censusFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowCensusEvents(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowCensusEvents(true);
                }
            }
        });
    }

    private void initializeChristeningEvents(View view) {
        View christeningFilter = view.findViewById(R.id.filter_christening_events);
        ((TextView) christeningFilter.findViewById(R.id.filter_value)).setText("Christening Events");
        ((TextView) christeningFilter.findViewById(R.id.filter_description)).setText("FILTER BY CHRISTENING EVENTS");

        final Switch filterSwitch = (Switch) christeningFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowChristeningEvents())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowChristeningEvents(true);
                else
                    mFilters.setShowChristeningEvents(false);
            }
        });

        christeningFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowChristeningEvents(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowChristeningEvents(true);
                }
            }
        });
    }

    private void initializeDeathEvents(View view) {
        View deathFilter = view.findViewById(R.id.filter_death_events);
        ((TextView) deathFilter.findViewById(R.id.filter_value)).setText("Death Events");
        ((TextView) deathFilter.findViewById(R.id.filter_description)).setText("FILTER BY DEATH EVENTS");

        final Switch filterSwitch = (Switch) deathFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowDeathEvents())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowDeathEvents(true);
                else
                    mFilters.setShowDeathEvents(false);
            }
        });

        deathFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowDeathEvents(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowDeathEvents(true);
                }
            }
        });
    }

    private void initializeMarriageEvents(View view) {
        View marriageFilter = view.findViewById(R.id.filter_marriage_events);
        ((TextView) marriageFilter.findViewById(R.id.filter_value)).setText("Marriage Events");
        ((TextView) marriageFilter.findViewById(R.id.filter_description)).setText("FILTER BY MARRIAGE EVENTS");

        final Switch filterSwitch = (Switch) marriageFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowMarriageEvents())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowMarriageEvents(true);
                else
                    mFilters.setShowMarriageEvents(false);
            }
        });

        marriageFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowMarriageEvents(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowMarriageEvents(true);
                }
            }
        });
    }

    private void initializeFathersSide(View view) {
        View fathersSideFilter = view.findViewById(R.id.filter_fathers_side);
        ((TextView) fathersSideFilter.findViewById(R.id.filter_value)).setText("Father's Side");
        ((TextView) fathersSideFilter.findViewById(R.id.filter_description)).setText("FILTER BY FATHER'S SIDE OF FAMILY");

        final Switch filterSwitch = (Switch) fathersSideFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowFathersSide())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowFathersSide(true);
                else
                    mFilters.setShowFathersSide(false);
            }
        });

        fathersSideFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowFathersSide(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowFathersSide(true);
                }
            }
        });
    }

    private void initializeMothersSide(View view) {
        View mothersSideFilter = view.findViewById(R.id.filter_mothers_side);
        ((TextView) mothersSideFilter.findViewById(R.id.filter_value)).setText("Mother's Side");
        ((TextView) mothersSideFilter.findViewById(R.id.filter_description)).setText("FILTER BY MOTHER'S SIDE OF FAMILY");

        final Switch filterSwitch = (Switch) mothersSideFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowMothersSide())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowMothersSide(true);
                else
                    mFilters.setShowMothersSide(false);
            }
        });

        mothersSideFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowMothersSide(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowMothersSide(true);
                }
            }
        });
    }

    private void initializeMaleEvents(View view) {
        View maleEventsFilter = view.findViewById(R.id.filter_male_events);
        ((TextView) maleEventsFilter.findViewById(R.id.filter_value)).setText("Male Events");
        ((TextView) maleEventsFilter.findViewById(R.id.filter_description)).setText("FILTER EVENTS BASED ON GENDER");

        final Switch filterSwitch = (Switch) maleEventsFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowMaleEvents())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowMaleEvents(true);
                else
                    mFilters.setShowMaleEvents(false);
            }
        });

        maleEventsFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowMaleEvents(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowMaleEvents(true);
                }
            }
        });
    }

    private void initializeFemaleEvents(View view) {
        View femaleEventsFilter = view.findViewById(R.id.filter_female_events);
        ((TextView) femaleEventsFilter.findViewById(R.id.filter_value)).setText("Female Events");
        ((TextView) femaleEventsFilter.findViewById(R.id.filter_description)).setText("FILTER EVENTS BASED ON GENDER");

        final Switch filterSwitch = (Switch) femaleEventsFilter.findViewById(R.id.filter_switch);
        if (mFilters.isShowFemaleEvents())
            filterSwitch.setChecked(true);
        else
            filterSwitch.setChecked(false);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilters.setShowFemaleEvents(true);
                else
                    mFilters.setShowFemaleEvents(false);
            }
        });

        femaleEventsFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSwitch.isChecked()) {
                    filterSwitch.setChecked(false);
                    mFilters.setShowFemaleEvents(false);
                } else {
                    filterSwitch.setChecked(true);
                    mFilters.setShowFemaleEvents(true);
                }
            }
        });
    }

//endregion PRIVATE METHODS
}
