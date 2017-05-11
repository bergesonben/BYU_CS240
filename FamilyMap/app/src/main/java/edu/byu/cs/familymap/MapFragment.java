package edu.byu.cs.familymap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import edu.byu.cs.familymap.Model.Event;
import edu.byu.cs.familymap.Model.FamilyMapData;
import edu.byu.cs.familymap.Model.FamilyMapFilters;
import edu.byu.cs.familymap.Model.FamilyMapSettings;
import edu.byu.cs.familymap.Model.Person;


/**
 * Created by User on 8/1/2016.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private static final String ARG_EVENT_ID = "event_id";
    private static final String ARG_PARENT_ACTIVITY = "parent_activity";
    private final int MENU_ICON_SIZE = 40;
    private final int GENDER_ICON_SIZE = 40;

    private GoogleMap mMap;
    private LinearLayout mEventDetails;
    private TextView mEventDetailsTop;
    private TextView mEventDetailsBottom;
    private ImageView mGenderImageView;

    private Map<String, Event> mMarkers = new HashMap<>();
    private Map<String, Polyline> mPolylines = new HashMap<>();
    private FamilyMapData mData;
    private Event mCurrentEvent;
    private Person mCurrentPerson;
    private String mParentActivity;
    private FamilyMapSettings mSettings;
    private FamilyMapFilters mFilters;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

    public static MapFragment newInstance(String eventId, String parentActivity) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID, eventId);
        args.putSerializable(ARG_PARENT_ACTIVITY, parentActivity);

        MapFragment fragment = new MapFragment();
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
        mSettings = FamilyMapSettings.get();
        mFilters = FamilyMapFilters.get();
        mParentActivity = (String) getArguments().getSerializable(ARG_PARENT_ACTIVITY);
        String eventId = (String) getArguments().getSerializable(ARG_EVENT_ID);
        if (eventId != null) {
            mCurrentEvent = mData.getEvent(eventId);
            if (mCurrentEvent != null)
                mCurrentPerson = mData.getPerson(mCurrentEvent.getPersonId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = ((SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        initializeView(v);

        mEventDetails = (LinearLayout) v.findViewById(R.id.event_details);
        mEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = PersonActivity.newIntent(getActivity(), mCurrentPerson.getId());
                startActivity(intent);
            }
        });


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(mSettings.getMapType());
        drawPoints();
        if (mCurrentEvent != null) {
            CameraUpdate center = CameraUpdateFactory.newLatLng(mCurrentEvent.getPosition());
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(5);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            updatePolylines();
        }

        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);

        if (mParentActivity.equals(MainActivity.ACTIVITY_NAME)) {
            inflater.inflate(R.menu.fragment_map_login, menu);

            Drawable searchIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_search)
                    .colorRes(R.color.black).sizeDp(MENU_ICON_SIZE);
            menu.findItem(R.id.menu_item_search).setIcon(searchIcon);

            Drawable filterIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_filter)
                    .colorRes(R.color.black).sizeDp(MENU_ICON_SIZE);
            menu.findItem(R.id.menu_item_filter).setIcon(filterIcon);

            Drawable settingsIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_gear)
                    .colorRes(R.color.black).sizeDp(MENU_ICON_SIZE);
            menu.findItem(R.id.menu_item_settings).setIcon(settingsIcon);
        } else {
            inflater.inflate(R.menu.fragment_map_map, menu);

            Drawable upIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_angle_double_up)
                    .colorRes(R.color.black).sizeDp(MENU_ICON_SIZE);
            menu.findItem(R.id.menu_item_up).setIcon(upIcon);
        }

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        mCurrentEvent = mMarkers.get(marker.getId());
        mCurrentPerson = mData.getPerson(mCurrentEvent.getPersonId());
        updateEventDetailsView();
        updatePolylines();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_item_settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_item_filter:
                intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_item_up:
                intent = new Intent(getActivity(), MainActivity.class);
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

    @Override
    public void onResume() {
        super.onResume();

        if (mMap != null) {
            mMap.clear();
            updatePolylines();
            mMap.setMapType(mSettings.getMapType());
            drawPoints();
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

    private void initializeView(View view) {
        mEventDetailsTop = (TextView) view.findViewById(R.id.event_details_top_text_view);
        mEventDetailsBottom = (TextView) view.findViewById(R.id.event_details_bottom_text_view);
        mGenderImageView = (ImageView) view.findViewById(R.id.gender_image_view);
        if (mCurrentEvent != null)
            updateEventDetailsView();
    }

    private void updateEventDetailsView() {
        Person.Gender gender = mCurrentPerson.getGender();
        if (gender == Person.Gender.MALE) {
            Drawable maleIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_male)
                    .colorRes(R.color.male).sizeDp(GENDER_ICON_SIZE);
            mGenderImageView.setImageDrawable(maleIcon);
        } else {
            Drawable femaleIcon = new IconDrawable(getActivity(), Iconify.IconValue.fa_female)
                    .colorRes(R.color.female).sizeDp(GENDER_ICON_SIZE);
            mGenderImageView.setImageDrawable(femaleIcon);
        }
        mEventDetailsTop.setText(mCurrentPerson.getFullName());
        mEventDetailsBottom.setText(mCurrentEvent.getDetails());
    }

    private void updatePolylines() {
        for(String key : mPolylines.keySet()) {
            mPolylines.get(key).remove();
        }
        if (mCurrentEvent == null || mCurrentPerson == null) return;
        updateSpouseLines();
        drawParentsLines(mCurrentPerson, mCurrentEvent, 20);
        updateLifeStoryLines();
    }

    private void updateSpouseLines() {
        if (mSettings.isDrawSpouseLines()) {
            Event spouseLineEvent = mData.getSpouseLineEvent(mCurrentPerson);
            if (spouseLineEvent != null) {
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(mCurrentEvent.getPosition(), spouseLineEvent.getPosition())
                        .width(15)
                        .color(mSettings.getSpouseLinesColor()));
                mPolylines.put(line.getId(), line);
            }
        }
    }

    private void drawParentsLines(Person person, Event event, int width) {
        if (mSettings.isDrawFamilyTreeLines()) {
            if (person == null) return;
            Event fatherEvent = mData.getFatherLineEvent(person);
            Event motherEvent = mData.getMotherLineEvent(person);
            if (fatherEvent != null) {
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(event.getPosition(), fatherEvent.getPosition())
                        .width(width)
                        .color(mSettings.getFamilyTreeLinesColor()));
                mPolylines.put(line.getId(), line);
                Person father = mData.getPerson(person.getFatherId());
                drawParentsLines(father, fatherEvent, width / 2);
            }
            if (motherEvent != null) {
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(event.getPosition(), motherEvent.getPosition())
                        .width(width)
                        .color(mSettings.getFamilyTreeLinesColor()));
                mPolylines.put(line.getId(), line);
                Person mother = mData.getPerson(person.getMotherId());
                drawParentsLines(mother, motherEvent, width / 2);
            }
        }
    }

    private void updateLifeStoryLines() {
        if (mSettings.isDrawLifeStoryLines()) {
            Vector<Event> events = mData.getEventsByOrder(mCurrentPerson);
            for (int i = 0; i < events.size() - 1; i++) {
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(events.elementAt(i).getPosition(), events.elementAt(i + 1).getPosition())
                        .width(15)
                        .color(mSettings.getLifeStoryLinesColor()));
                mPolylines.put(line.getId(), line);
            }
        }
    }

    private void drawPoints() {
        FamilyMapData data = FamilyMapData.get();
        Map<String, Event> events = data.getEvents();
        for(String key : events.keySet()) {
            Event event = events.get(key);
            String type = event.getDescription();
            Person person = mData.getPerson(event.getPersonId());
            Person.Gender gender = person.getGender();
            boolean isOnFathersSide = person.isOnFathersSide();

            MarkerOptions marker = new MarkerOptions().position(event.getPosition());
            boolean shouldBeAdded = false;

            switch (type) {
                case "birth":
                    if (mFilters.isShowBirthEvents()) {
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        shouldBeAdded = true;
                    }
                    break;
                case "death":
                    if (mFilters.isShowDeathEvents()) {
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        shouldBeAdded = true;
                    }
                    break;
                case "christening":
                    if (mFilters.isShowChristeningEvents()) {
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        shouldBeAdded = true;
                    }
                    break;
                case "baptism":
                    if (mFilters.isShowBaptismEvents()) {
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                        shouldBeAdded = true;
                    }
                    break;
                case "census":
                    if (mFilters.isShowCensusEvents()) {
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        shouldBeAdded = true;
                    }
                    break;
                case "marriage":
                    if (mFilters.isShowMarriageEvents()) {
                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                        shouldBeAdded = true;
                    }
                    break;
                default:
                    shouldBeAdded = true;
                    break;
            }

            if (!shouldBeAdded)
                continue;

            if (gender == Person.Gender.MALE) {
                if (mFilters.isShowMaleEvents())
                    shouldBeAdded = true;
                else
                    continue;
            } else {
                if (mFilters.isShowFemaleEvents())
                    shouldBeAdded = true;
                else
                    continue;
            }

            if (isOnFathersSide) {
                if (mFilters.isShowFathersSide())
                    shouldBeAdded = true;
                else
                    continue;
            } else {
                if (mFilters.isShowMothersSide())
                    shouldBeAdded = true;
                else
                    continue;
            }

            if (shouldBeAdded) {
                Marker m = mMap.addMarker(marker);
                mMarkers.put(m.getId(), event);
            }
        }
    }

//endregion PRIVATE METHODS
}
