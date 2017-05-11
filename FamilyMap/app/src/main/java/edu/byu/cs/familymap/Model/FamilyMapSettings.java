package edu.byu.cs.familymap.Model;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by User on 8/6/2016.
 */
public class FamilyMapSettings {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private boolean mDrawLifeStoryLines;
    private int mLifeStoryLinesColor;
    private boolean mDrawFamilyTreeLines;
    private int mFamilyTreeLinesColor;
    private boolean mDrawSpouseLines;
    private int mSpouseLinesColor;
    private int mMapType;
    private static FamilyMapSettings instance;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

    private FamilyMapSettings() {
        mDrawLifeStoryLines = true;
        mLifeStoryLinesColor = Color.GREEN;
        mDrawFamilyTreeLines = true;
        mFamilyTreeLinesColor = Color.BLUE;
        mDrawSpouseLines = true;
        mSpouseLinesColor = Color.RED;
        mMapType = GoogleMap.MAP_TYPE_NORMAL;
    }

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

    public static FamilyMapSettings get() {
        if (instance == null) {
            instance = new FamilyMapSettings();
        }

        return instance;
    }

    public boolean isDrawLifeStoryLines() {
        return mDrawLifeStoryLines;
    }

    public int getLifeStoryLinesColor() {
        return mLifeStoryLinesColor;
    }

    public boolean isDrawFamilyTreeLines() {
        return mDrawFamilyTreeLines;
    }

    public int getFamilyTreeLinesColor() {
        return mFamilyTreeLinesColor;
    }

    public boolean isDrawSpouseLines() {
        return mDrawSpouseLines;
    }

    public int getSpouseLinesColor() {
        return mSpouseLinesColor;
    }

    public int getMapType() {
        return mMapType;
    }

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

    public void setDrawLifeStoryLines(boolean drawLifeStoryLines) {
        mDrawLifeStoryLines = drawLifeStoryLines;
    }

    public void setLifeStoryLinesColor(int lifeStoryLinesColor) {
        mLifeStoryLinesColor = lifeStoryLinesColor;
    }

    public void setDrawFamilyTreeLines(boolean drawFamilyTreeLines) {
        mDrawFamilyTreeLines = drawFamilyTreeLines;
    }

    public void setFamilyTreeLinesColor(int familyTreeLinesColor) {
        mFamilyTreeLinesColor = familyTreeLinesColor;
    }

    public void setDrawSpouseLines(boolean drawSpouseLines) {
        mDrawSpouseLines = drawSpouseLines;
    }

    public void setSpouseLinesColor(int spouseLinesColor) {
        mSpouseLinesColor = spouseLinesColor;
    }

    public void setMapType(int mapType) {
        mMapType = mapType;
    }

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
//endregion PRIVATE METHODS
}
