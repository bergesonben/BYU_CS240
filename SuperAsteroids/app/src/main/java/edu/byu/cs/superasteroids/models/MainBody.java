package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bergeson on 7/7/2016.
 */
public class MainBody extends ImageObject {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private PointF mCannonAttachPoint;
    private PointF mEngineAttachPoint;
    private PointF mExtraPartAttachPoint;
    private String mMainBodyImagePath;
    private int mMainBodyImageWidth;
    private int mMainBodyImageHeight;
    private int mID;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic Constructor
     */
    public MainBody(){
        mCannonAttachPoint = new PointF();
        mEngineAttachPoint = new PointF();
        mExtraPartAttachPoint = new PointF();
        mMainBodyImagePath = "";
        mMainBodyImageWidth = 0;
        mMainBodyImageHeight = 0;
        mID = -1;
    }

    /**
     * Constructor that initializes all variables.
     * @param obj JSONObject representing a MainBody.
     */
    public MainBody(JSONObject obj) throws JSONException{
        String cannonAttachPoint = obj.getString("cannonAttach");
        mCannonAttachPoint = Spaceship.convertStringToPointF(cannonAttachPoint);
        String engineAttachPoint = obj.getString("engineAttach");
        mEngineAttachPoint = Spaceship.convertStringToPointF(engineAttachPoint);
        String extraAttachPoint = obj.getString("extraAttach");
        mExtraPartAttachPoint = Spaceship.convertStringToPointF(extraAttachPoint);
        mMainBodyImagePath = obj.getString("image");
        mMainBodyImageWidth = obj.getInt("imageWidth");
        mMainBodyImageHeight = obj.getInt("imageHeight");
        mID = -1;
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns the cannon attach point.
     * @return PointF representing the cannon attach point.
     */
    public PointF getCannonAttachPoint() {
        return mCannonAttachPoint;
    }

    /**
     * Returns the engine attach point.
     * @return PointF representing the engine attach point.
     */
    public PointF getEngineAttachPoint() {
        return mEngineAttachPoint;
    }

    /**
     * Returns the extra part attach point.
     * @return PointF representing the extra part attach point.
     */
    public PointF getExtraPartAttachPoint() {
        return mExtraPartAttachPoint;
    }

    /**
     * Returns the mainbody image path.
     * @return String representing the mainbody image path.
     */
    @Override
    public String getImagePath() {
        return mMainBodyImagePath;
    }

    /**
     * Returns the mainbody image width.
     * @return int representing the mainbody image width.
     */
    @Override
    public int getImageWidth() {
        return mMainBodyImageWidth;
    }

    /**
     * Returns the mainbody image height.
     * @return int representing the mainbody image height.
     */
    @Override
    public int getImageHeight() {
        return mMainBodyImageHeight;
    }

    /**
     * Returns the ID that the content manager uses to keep track.
     * @return
     */
    public int getID() {
        return mID;
    }

    /**
     * Returns the PointF representing the center point of the MainBody
     * @return PointF representing the center point of the MainBody
     */
    public PointF getCenterPoint() {
        PointF center = new PointF();
        center.x = mMainBodyImageWidth/2;
        center.y = mMainBodyImageHeight/2;
        return center;
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Sets the cannon attach point.
     * @param cannonAttachPoint PointF representing the cannon attach point.
     */
    public void setCannonAttachPoint(PointF cannonAttachPoint) {
        mCannonAttachPoint = cannonAttachPoint;
    }

    /**
     * Sets the engine attach point.
     * @param engineAttachPoint PointF representing the engine attach point.
     */
    public void setEngineAttachPoint(PointF engineAttachPoint) {
        mEngineAttachPoint = engineAttachPoint;
    }

    /**
     * Sets the extra part attach point.
     * @param extraPartAttachPoint PointF representing the extra part attach point.
     */
    public void setExtraPartAttachPoint(PointF extraPartAttachPoint) {
        mExtraPartAttachPoint = extraPartAttachPoint;
    }

    /**
     * Sets the mainbody image path.
     * @param mainBodyImagePath String representing the mainbody image path.
     */
    @Override
    public void setImagePath(String mainBodyImagePath) {
        mMainBodyImagePath = mainBodyImagePath;
    }

    /**
     * Sets the mainbody image width.
     * @param mainBodyImageWidth int representing the mainbody image width.
     */
    @Override
    public void setImageWidth(int mainBodyImageWidth) {
        mMainBodyImageWidth = mainBodyImageWidth;
    }

    /**
     * Sets the mainbody image height.
     * @param mainBodyImageHeight int representing the mainbody image height.
     */
    @Override
    public void setImageHeight(int mainBodyImageHeight) {
        mMainBodyImageHeight = mainBodyImageHeight;
    }

    /**
     * Sets the ID that the content manager uses to keep track.
     * @param id
     */
    public void setID(int id) {
        mID = id;
    }

    /**
     * Checks if the MainBodies have the same values.
     * @param mainBody MainBody to compare against.
     * @return true if the MainBodies have the same values, false if not.
     */
    public boolean equals(MainBody mainBody) {
        if(!mCannonAttachPoint.equals(mainBody.getCannonAttachPoint()))
            return false;
        if(!mEngineAttachPoint.equals(mainBody.getEngineAttachPoint()))
            return false;
        if(!mExtraPartAttachPoint.equals(mainBody.getExtraPartAttachPoint()))
            return false;
        if(!mMainBodyImagePath.equals(mainBody.getImagePath()))
            return false;
        if(mMainBodyImageHeight != mainBody.getImageHeight())
            return false;
        if(mMainBodyImageWidth != mainBody.getImageWidth())
            return false;

        return true;
    }
//endregion COMMANDS
}
