package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bergeson on 7/7/2016.
 */
public class ExtraPart extends AttachablePart {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private PointF mExtraPartAttachPoint;
    private String mExtraPartImagePath;
    private int mExtraPartImageWidth;
    private int mExtraPartImageHeight;
    private int mID;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic Constructor.
     */
    public ExtraPart(){
        mExtraPartAttachPoint = new PointF();
        mExtraPartImagePath = "";
        mExtraPartImageWidth = 0;
        mExtraPartImageHeight = 0;
    }

    /**
     * Constructor that initializes all variables.
     * @param obj JSONObject representing an ExtraPart.
     */
    public ExtraPart(JSONObject obj) throws JSONException {
        String attachPoint = obj.getString("attachPoint");
        mExtraPartAttachPoint = Spaceship.convertStringToPointF(attachPoint);
        mExtraPartImagePath = obj.getString("image");
        mExtraPartImageWidth = obj.getInt("imageWidth");
        mExtraPartImageHeight = obj.getInt("imageHeight");
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns the extra part attach point.
     * @return PointF representing the extra part attach point.
     */
    @Override
    public PointF getAttachPoint() {
        return mExtraPartAttachPoint;
    }

    /**
     * Returns the extra part image path.
     * @return String representing the extra part image path.
     */
    @Override
    public String getImagePath() {
        return mExtraPartImagePath;
    }

    /**
     * Returns the extra part image width.
     * @return int representing the extra part image width.
     */
    @Override
    public int getImageWidth() {
        return mExtraPartImageWidth;
    }

    /**
     * Returns the extra part image height.
     * @return int representing the extra part image height.
     */
    @Override
    public int getImageHeight() {
        return mExtraPartImageHeight;
    }

    /**
     * Returns the id number that the content manager uses to keep track of images.
     * @return
     */
    public int getID() {
        return mID;
    }

    /**
     * Returns the center point of the ExtraPart.
     * @return PointF representing the center point of the ExtraPart.
     */
    public PointF getCenterPoint() {
        PointF center = new PointF();
        center.x = mExtraPartImageWidth/2;
        center.y = mExtraPartImageHeight/2;
        return center;
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Sets the extra part attach point.
     * @param extraPartAttachPoint PointF representing the extra part attach point.
     */
    @Override
    public void setAttachPoint(PointF extraPartAttachPoint) {
        mExtraPartAttachPoint = extraPartAttachPoint;
    }

    /**
     * Sets the extra part image path.
     * @param extraPartImagePath String representing the extra part image path.
     */
    @Override
    public void setImagePath(String extraPartImagePath) {
        mExtraPartImagePath = extraPartImagePath;
    }

    /**
     * Sets the extra part image width.
     * @param extraPartImageWidth int representing the extra part image width.
     */
    @Override
    public void setImageWidth(int extraPartImageWidth) {
        mExtraPartImageWidth = extraPartImageWidth;
    }

    /**
     * Sets the extra part image height.
     * @param extraPartImageHeight int representing the extra part image height.
     */
    @Override
    public void setImageHeight(int extraPartImageHeight) {
        mExtraPartImageHeight = extraPartImageHeight;
    }

    /**
     * Sets the id number that the content manager uses to keep track of images.
     * @param id
     */
    public void setID(int id) {
        mID = id;
    }

    public boolean equals(ExtraPart extraPart) {
        if(!mExtraPartAttachPoint.equals(extraPart.getAttachPoint()))
            return false;
        if(!mExtraPartImagePath.equals(extraPart.getImagePath()))
            return false;
        if(mExtraPartImageHeight != extraPart.getImageHeight())
            return false;
        if(mExtraPartImageWidth != extraPart.getImageWidth())
            return false;

        return true;
    }
//endregion COMMANDS
}
