package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by bergeson on 7/7/2016.
 */
public class BackgroundObject extends ImageObject {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private String mObjectPath;
    private double mObjectScale;
    private PointF mObjectCoordinates;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic constructor that initializes the variables to unusable values.
     */
    public BackgroundObject(){
        mObjectPath = "";
        mObjectScale = 0;
        mObjectCoordinates = null;
    }

    /**
     * Constructor that initializes the image path to put into the database.
     * @param path String that represents a BackgroundObject image path.
     */
    public BackgroundObject(String path){
        mObjectPath = path;
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns the image path.
     * @return String representing the image path.
     */
    @Override
    public String getImagePath() {
        return mObjectPath;
    }

    //TODO: remove
//    /**
//     * Returns the object scale.
//     * @return double representing the object scale.
//     */
//    public double getObjectScale() {
//        return mObjectScale;
//    }
//
//    /**
//     * Returns the object coordinates.
//     * @return PointF representing the object coordinates.
//     */
//    public PointF getObjectCoordinates() {
//        return mObjectCoordinates;
//    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Sets the path for the image.
     * @param imagePath String representing the path for the image.
     * @return true if successful, false if not.
     */
    @Override
    public void setImagePath(String imagePath) {
        mObjectPath = imagePath;
    }

    /**
     * Sets the object coordinates.
     * @param objectCoordinates PointF representing the object coordinates.
     */
    public void setObjectCoordinates(PointF objectCoordinates) {
        mObjectCoordinates = objectCoordinates;
    }

    /**
     * Sets the object scale.
     * @param objectScale double representing the object scale.
     */
    public void setObjectScale(double objectScale) {
        mObjectScale = objectScale;
    }

    /**
     * Draws the BackgroundObject
     */
    public void draw() {
        int id = ContentManager.getInstance().getImageId(mObjectPath);
        PointF viewCoordinates = ViewPort.getViewPortCoordinate(mObjectCoordinates);
        DrawingHelper.drawImage(id, viewCoordinates.x, viewCoordinates.y, 0,
                (float)mObjectScale, (float)mObjectScale, 255);
    }

    /**
     * Compares if the BackgroundObjects have the same values.
     * @param object BackgroundObject to compare against.
     * @return true if the BackgroundObjects have the same values, false if not.
     */
    public boolean equals(BackgroundObject object) {
        if(!mObjectPath.equals(object.getImagePath()))
            return false;

        return true;
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           UNUSED METHODS
//**************************************************************************************************
//region UNUSED METHODS
    /**
     * Does nothing.
     * @return 0.
     */
    @Override
    int getImageWidth() {
        return 0;
    }

    /**
     * Does nothing.
     * @return 0.
     */
    @Override
    int getImageHeight() {
        return 0;
    }

    /**
     * Does nothing.
     * @param imageWidth not used.
     * @return false.
     */
    @Override
    void setImageWidth(int imageWidth) {}

    /**
     * Does nothing.
     * @param imageHeight not used.
     * @return false.
     */
    @Override
    void setImageHeight(int imageHeight) {}
//endregion UNUSED METHODS
}
