package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bergeson on 7/7/2016.
 */
public class Engine extends AttachablePart {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private int mEngineSpeed;
    private int mEngineTurnRate;
    private PointF mEngineAttachPoint;
    private String mEngineImagePath;
    private int mEngineImageWidth;
    private int mEngineImageHeight;
    private int mID;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic Constructor.
     */
    public Engine(){
        mEngineSpeed = 0;
        mEngineTurnRate = 0;
        mEngineAttachPoint = new PointF();
        mEngineImagePath = "";
        mEngineImageWidth = 0;
        mEngineImageHeight = 0;
    }

    /**
     * Constructor that initializes all variables.
     * @param obj JSONObject representing an Engine object.
     */
    public Engine(JSONObject obj) throws JSONException {
        mEngineSpeed = obj.getInt("baseSpeed");
        mEngineTurnRate = obj.getInt("baseTurnRate");
        String attachPoint = obj.getString("attachPoint");
        mEngineAttachPoint = Spaceship.convertStringToPointF(attachPoint);
        mEngineImagePath = obj.getString("image");
        mEngineImageWidth = obj.getInt("imageWidth");
        mEngineImageHeight = obj.getInt("imageHeight");
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns the base engine speed.
     * @return int representing the base engine speed.
     */
    public int getEngineSpeed() {
        return mEngineSpeed;
    }

    /**
     * Returns the base engine turn rate.
     * @return int representing the base engine turn rate.
     */
    public int getEngineTurnRate() {
        return mEngineTurnRate;
    }

    /**
     * Returns the engine attach point.
     * @return PointF representing the engine attach point.
     */
    @Override
    public PointF getAttachPoint() {
        return mEngineAttachPoint;
    }

    /**
     * Returns the engine image path.
     * @return String representing the engine image path.
     */
    @Override
    public String getImagePath() {
        return mEngineImagePath;
    }

    /**
     * Returns the engine image width.
     * @return int representing the engine image width.
     */
    @Override
    public int getImageWidth() {
        return mEngineImageWidth;
    }

    /**
     * Returns the engine image height.
     * @return int representing the engine image height.
     */
    @Override
    public int getImageHeight() {
        return mEngineImageHeight;
    }

    /**
     * Returns the id number that the content manager uses to keep track of images.
     * @return
     */
    public int getID() {
        return  mID;
    }

    /**
     * Returns the center point for the Engine.
     * @return PointF representing the center point of the Engine.
     */
    public PointF getCenterPoint() {
        PointF center = new PointF();
        center.x = mEngineImageWidth/2;
        center.y = mEngineImageHeight/2;
        return center;
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Sets the base engine speed.
     * @param engineSpeed int representing the base engine speed.
     */
    public void setEngineSpeed(int engineSpeed) {
        mEngineSpeed = engineSpeed;
    }

    /**
     * Sets the base engine turn rate.
     * @param engineTurnRate int representing the base engine turn rate.
     */
    public void setEngineTurnRate(int engineTurnRate) {
        mEngineTurnRate = engineTurnRate;
    }

    /**
     * Sets the engine attach point.
     * @param engineAttachPoint PointF representing the engine attach point.
     */
    @Override
    public void setAttachPoint(PointF engineAttachPoint) {
        mEngineAttachPoint = engineAttachPoint;
    }

    /**
     * Sets the engine image path.
     * @param engineImagePath String representing the engine image path.
     */
    @Override
    public void setImagePath(String engineImagePath) {
        mEngineImagePath = engineImagePath;
    }

    /**
     * Sets the engine image width.
     * @param engineImageWidth int representing the engine image width.
     */
    @Override
    public void setImageWidth(int engineImageWidth) {
        mEngineImageWidth = engineImageWidth;
    }

    /**
     * Sets the engine image height.
     * @param engineImageHeight int representing the engine image height.
     */
    @Override
    public void setImageHeight(int engineImageHeight) {
        mEngineImageHeight = engineImageHeight;
    }

    /**
     * Sets the id number that the content manager uses to keep track of images.
     * @param id
     */
    public void setID(int id) {
        mID = id;
    }

    public boolean equals(Engine engine) {
        if(!mEngineAttachPoint.equals(engine.getAttachPoint()))
            return false;
        if(mEngineImageHeight != engine.getImageHeight())
            return false;
        if(mEngineImageWidth != engine.getImageWidth())
            return false;
        if(!mEngineImagePath.equals(engine.getImagePath()))
            return false;
        if(mEngineSpeed != engine.getEngineSpeed())
            return false;
        if(mEngineTurnRate != engine.getEngineTurnRate())
            return false;

        return true;
    }
//endregion COMMANDS
}
