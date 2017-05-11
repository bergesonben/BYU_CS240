package edu.byu.cs.familymap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import edu.byu.cs.familymap.Model.Event;
import edu.byu.cs.familymap.Model.FamilyMapData;
import edu.byu.cs.familymap.Model.Person;

/**
 * Created by User on 8/3/2016.
 */
public class PersonFragment extends Fragment {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private final int MENU_ICON_SIZE = 40;
    private static final String ARG_PERSON_ID = "person_id";

    private ExpandableListAdapter mListAdapter;
    private ExpandableListView mListView;
    private Person mPerson;
    private FamilyMapData mData;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

    public static PersonFragment newInstance(String personId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PERSON_ID, personId);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

        String personId = (String) getArguments().getSerializable(ARG_PERSON_ID);
        mPerson = FamilyMapData.get().getPerson(personId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, container, false);

        updatePersonDetailsView(v);

        mListView = (ExpandableListView) v.findViewById(R.id.person_details_list);
        List<String> headers = new ArrayList<>();
        headers.add("LIFE EVENTS");
        headers.add("FAMILY");

        Map<String, List<Object>> eventsAndFamily = new HashMap<>();
        List<Object> lifeEvents = new Vector<Object>(mData.getEventsByOrder(mPerson));
        eventsAndFamily.put(headers.get(0), lifeEvents);
        List<Object> family = new Vector<Object>(mData.getFamily(mPerson));
        eventsAndFamily.put(headers.get(1), family);

        mListAdapter = new ExpandableListAdapter(getActivity(), headers, eventsAndFamily);
        mListView.setAdapter(mListAdapter);
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    Event event = (Event) mListAdapter.getChild(groupPosition, childPosition);
                    Intent intent = MapActivity.newIntent(getActivity(), event.getEventId());
                    startActivity(intent);
                } else {
                    Person person = (Person) mListAdapter.getChild(groupPosition, childPosition);
                    Intent intent = PersonActivity.newIntent(getActivity(), person.getId());
                    startActivity(intent);
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.fragment_person, menu);

        Drawable upIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_angle_double_up)
                .colorRes(R.color.black).sizeDp(MENU_ICON_SIZE);
        menu.findItem(R.id.menu_item_up).setIcon(upIcon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_up:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;
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

    private void updatePersonDetailsView(View view) {
        View firstNameLayout = view.findViewById(R.id.person_details_first_name_layout);
        TextView firstNameField = (TextView) firstNameLayout.findViewById(R.id.person_details_name);
        firstNameField.setText("FIRST NAME");
        TextView firstNameValue = (TextView) firstNameLayout.findViewById(R.id.person_details_value);
        firstNameValue.setText(mPerson.getFirstName());

        View lastNameLayout = view.findViewById(R.id.person_details_last_name_layout);
        TextView lastNameField = (TextView) lastNameLayout.findViewById(R.id.person_details_name);
        lastNameField.setText("LAST NAME");
        TextView lastNameValue = (TextView) lastNameLayout.findViewById(R.id.person_details_value);
        lastNameValue.setText(mPerson.getLastName());

        View genderLayout = view.findViewById(R.id.person_details_gender_layout);
        TextView genderField = (TextView) genderLayout.findViewById(R.id.person_details_name);
        genderField.setText("GENDER");
        TextView genderValue = (TextView) genderLayout.findViewById(R.id.person_details_value);
        if (mPerson.getGender() == Person.Gender.MALE)
            genderValue.setText("Male");
        else
            genderValue.setText("Female");
    }

//endregion PRIVATE METHODS
}
