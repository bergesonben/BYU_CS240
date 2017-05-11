package edu.byu.cs.superasteroids.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bergeson on 7/7/2016.
 */
public class PowerCore {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private int mCannonBoost;
    private int mEngineBoost;
    private String mPowerCoreImagePath;
    private int mID;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic Constructor.
     */
    public PowerCore(){
        mCannonBoost = 0;
        mEngineBoost = 0;
        mPowerCoreImagePath = "";
    }

    /**
     * Constructor that initializes all variables.
     * @param obj JSONObject representing a PowerCore
     */
    public PowerCore(JSONObject obj) throws JSONException {
        mCannonBoost = obj.getInt("cannonBoost");
        mEngineBoost = obj.getInt("engineBoost");
        mPowerCoreImagePath = obj.getString("image");
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns the cannon boost.
     * @return int representing the cannon boost.
     */
    public int getCannonBoost() {
        return mCannonBoost;
    }

    /**
     * Returns the engine boost.
     * @return int representing the engine boost.
     */
    public int getEngineBoost() {
        return mEngineBoost;
    }

    /**
     * returns the power core image path.
     * @return String representing the power core image path.
     */
    public String getPowerCoreImagePath() {
        return mPowerCoreImagePath;
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Sets the cannon boost.
     * @param cannonBoost int representing the cannon boost.
     */
    public void setCannonBoost(int cannonBoost) {
        mCannonBoost = cannonBoost;
    }

    /**
     * Sets the engine boost.
     * @param engineBoost int representing the engine boost.
     */
    public void setEngineBoost(int engineBoost) {
        mEngineBoost = engineBoost;
    }

    /**
     * Sets the power core image path.
     * @param powerCoreImagePath String representing the power core image path.
     */
    public void setPowerCoreImagePath(String powerCoreImagePath) {
        mPowerCoreImagePath = powerCoreImagePath;
    }

    /**
     * Sets the id number that the content manager uses to keep track of images.
     * @param id
     */
    public void setID(int id) {
        mID = id;
    }

    /**
     * Checks if the PowerCores have the same values.
     * @param powerCore PowerCore to compare against.
     * @return true if the PowerCores have the same values, false if not.
     */
    public boolean equals(PowerCore powerCore) {
        if(mEngineBoost != powerCore.getEngineBoost())
            return false;
        if(mCannonBoost != powerCore.getCannonBoost())
            return false;
        if(!mPowerCoreImagePath.equals(powerCore.getPowerCoreImagePath()))
            return false;

        return true;
    }
//endregion COMMANDS
}
