package edu.byu.cs.familymap.Model;

/**
 * Created by User on 8/6/2016.
 */
public class FamilyMapFilters {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private boolean mShowBaptismEvents;
    private boolean mShowBirthEvents;
    private boolean mShowCensusEvents;
    private boolean mShowChristeningEvents;
    private boolean mShowDeathEvents;
    private boolean mShowMarriageEvents;
    private boolean mShowFathersSide;
    private boolean mShowMothersSide;
    private boolean mShowMaleEvents;
    private boolean mShowFemaleEvents;
    private static FamilyMapFilters instance;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

    private FamilyMapFilters() {
        mShowBaptismEvents = true;
        mShowBirthEvents = true;
        mShowCensusEvents = true;
        mShowChristeningEvents = true;
        mShowDeathEvents = true;
        mShowMarriageEvents = true;
        mShowFathersSide = true;
        mShowMothersSide = true;
        mShowMaleEvents = true;
        mShowFemaleEvents = true;
    }

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

    public static FamilyMapFilters get() {
        if (instance == null) {
            instance = new FamilyMapFilters();
        }

        return instance;
    }

    public boolean isShowBaptismEvents() {
        return mShowBaptismEvents;
    }

    public boolean isShowBirthEvents() {
        return mShowBirthEvents;
    }

    public boolean isShowCensusEvents() {
        return mShowCensusEvents;
    }

    public boolean isShowChristeningEvents() {
        return mShowChristeningEvents;
    }

    public boolean isShowDeathEvents() {
        return mShowDeathEvents;
    }

    public boolean isShowMarriageEvents() {
        return mShowMarriageEvents;
    }

    public boolean isShowFathersSide() {
        return mShowFathersSide;
    }

    public boolean isShowMothersSide() {
        return mShowMothersSide;
    }

    public boolean isShowMaleEvents() {
        return mShowMaleEvents;
    }

    public boolean isShowFemaleEvents() {
        return mShowFemaleEvents;
    }

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

    public void setShowBaptismEvents(boolean showBaptismEvents) {
        mShowBaptismEvents = showBaptismEvents;
    }

    public void setShowBirthEvents(boolean showBirthEvents) {
        mShowBirthEvents = showBirthEvents;
    }

    public void setShowCensusEvents(boolean showCensusEvents) {
        mShowCensusEvents = showCensusEvents;
    }

    public void setShowChristeningEvents(boolean showChristeningEvents) {
        mShowChristeningEvents = showChristeningEvents;
    }

    public void setShowDeathEvents(boolean showDeathEvents) {
        mShowDeathEvents = showDeathEvents;
    }

    public void setShowMarriageEvents(boolean showMarriageEvents) {
        mShowMarriageEvents = showMarriageEvents;
    }

    public void setShowFathersSide(boolean showFathersSide) {
        mShowFathersSide = showFathersSide;
    }

    public void setShowMothersSide(boolean showMothersSide) {
        mShowMothersSide = showMothersSide;
    }

    public void setShowMaleEvents(boolean showMaleEvents) {
        mShowMaleEvents = showMaleEvents;
    }

    public void setShowFemaleEvents(boolean showFemaleEvents) {
        mShowFemaleEvents = showFemaleEvents;
    }

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
//endregion PRIVATE METHODS

}
