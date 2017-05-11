package edu.byu.cs.familymap.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 8/1/2016.
 */
public class Event {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    @SerializedName("eventID")
    String mEventId;
    @SerializedName("personID")
    String mPersonId;
    @SerializedName("latitude")
    Double mLatitude;
    @SerializedName("longitude")
    Double mLongitude;
    @SerializedName("country")
    String mCountry;
    @SerializedName("city")
    String mCity;
    @SerializedName("description")
    String mDescription;
    @SerializedName("year")
    int mYear = -1;
    @SerializedName("descendant")
    String mDescendant;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

    public String getEventId() {
        return mEventId;
    }

    public String getPersonId() {
        return mPersonId;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getCity() {
        return mCity;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getYear() {
        return mYear;
    }

    public String getDescendant() {
        return mDescendant;
    }

    public LatLng getPosition() {
        LatLng retval = new LatLng(mLatitude, mLongitude);
        return retval;
    }

    public String getDetails() {
        String retval = mDescription.toLowerCase() + ": " + mCity + ", " + mCountry
                + " (" + mYear + ")";
        return retval;
    }

    public boolean isEarlierThan(Event event) {
        if (event == null) return true;
        if (event.getYear() == -1) return true;
        if (mDescription == "death") return false;
        if (mYear <= event.getYear()) return true;
        return false;
    }

    public boolean contains(String s) {
        if (mCity.toLowerCase().contains(s.toLowerCase()))
            return true;
        if (mCountry.toLowerCase().contains(s.toLowerCase()))
            return true;
        if (mDescription.toString().toLowerCase().contains(s.toLowerCase()))
            return true;
        if (Integer.toString(mYear).contains(s.toLowerCase()))
            return true;

        return false;
    }

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
