package edu.byu.cs.familymap.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by User on 8/1/2016.
 */
public class Person {
//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    @SerializedName("descendant")
    String mDescendant;
    @SerializedName("personID")
    String mPersonId;
    @SerializedName("firstName")
    String mFirstName;
    @SerializedName("lastName")
    String mLastName;

    public enum Gender {
        @SerializedName("m")
        MALE,
        @SerializedName("f")
        FEMALE
    };

    @SerializedName("gender")
    Gender mGender;
    @SerializedName("father")
    String mFatherId;
    @SerializedName("mother")
    String mMotherId;
    @SerializedName("spouse")
    String mSpouseId;

    private String mRelationship;
    private Set<String> mChildrenIds = new HashSet<>();
    private boolean mOnFathersSide;

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

    public String getId() {
        return mPersonId;
    }

    public String getDescendant() {
        return mDescendant;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public Gender getGender() {
        return mGender;
    }

    public String getSpouseId() {
        return mSpouseId;
    }

    public String getFatherId() {
        return mFatherId;
    }

    public String getMotherId() {
        return mMotherId;
    }

    public String getFullName() { return mFirstName + " " + mLastName; }

    public String getRelationship() { return mRelationship; }

    public Set<String> getChildrenIds() { return mChildrenIds; }

    public boolean contains(String s) {
        if (mFirstName.toLowerCase().contains(s.toLowerCase()))
            return true;
        if (mLastName.toLowerCase().contains(s.toLowerCase()))
            return true;
        return false;
    }

    public boolean isOnFathersSide() { return mOnFathersSide; }

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

    public void setRelationship(String relationship) {
        mRelationship = relationship;
    }

    public void addChild(String id) {
        if (mChildrenIds == null) mChildrenIds = new HashSet<>();
        mChildrenIds.add(id);
    }

    public void setOnFathersSide(boolean b) {
        mOnFathersSide = b;
    }

    public void setId(String id) {
        mPersonId = id;
    }

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
//endregion PRIVATE METHODS
}
