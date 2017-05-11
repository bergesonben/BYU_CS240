package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.game.GameController;

/**
 * Created by User on 7/12/2016.
 */
public class Missile extends ImageObject {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private String mMissileImagePath;
    private int mMissileImageWidth;
    private int mMissileImageHeight;
    private int mMissileRotation;
    private PointF mMissileCoordinate;
    private RectF mMissileBound;
    private int mMissileSpeed;
    private int mID;
    private boolean mHit = false;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic constructor.
     */
    public Missile(){
        mMissileImagePath = "";
        mMissileImageHeight = 0;
        mMissileImageWidth = 0;
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns the String representing the missile image path.
     * @return String representing the missile image path.
     */
    @Override
    public String getImagePath() {
        return mMissileImagePath;
    }

    /**
     * Returns the image missile width.
     * @return int representing the missile image width.
     */
    @Override
    public int getImageWidth() {
        return mMissileImageWidth;
    }

    /**
     * Returns the missile image height.
     * @return int representing the missile image height.
     */
    @Override
    public int getImageHeight() {
        return mMissileImageHeight;
    }

    //TODO: remove
//    /**
//     * Returns the missile speed.
//     * @return int representing the missile speed.
//     */
//    public int getMissileSpeed() {
//        return mMissileSpeed;
//    }

    /**
     * Returns the missile coordinate.
     * @return PointF representing the missile coordinate.
     */
    public PointF getMissileCoordinate() {
        return mMissileCoordinate;
    }

    /**
     * Returns the missile rotation.
     * @return int representing the missile rotation.
     */
    public int getMissileRotation() {
        return mMissileRotation;
    }

    /**
     * Returns the missile image id.
     * @return
     */
    public int getID() {
        return mID;
    }

    /**
     * Sees if the Missile is visible on the screen.
     * @return true if the Missile is visible, false if not.
     */
    public boolean isVisible() {
        return ViewPort.isVisible(mMissileCoordinate);
    }

    /**
     * Returns the RectF bounding the Missile object.
     * @return
     */
    public RectF getMissileBound() {
        return mMissileBound;
    }

    /**
     * Returns true if this Missile has hit an asteroid.
     * @return
     */
    public boolean getHit() {
        return mHit;
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Sets the path for the missile image.
     * @param imagePath String representing the path for the missile image.
     */
    @Override
    public void setImagePath(String imagePath) {
        mMissileImagePath = imagePath;
    }

    /**
     * Sets the width for the missile image.
     * @param imageWidth int representing the width for the missile image.
     */
    @Override
    public void setImageWidth(int imageWidth) {
        mMissileImageWidth = imageWidth;
    }

    /**
     * Sets the height for the missile image.
     * @param imageHeight int representing the height for the missile image.
     */
    @Override
    public void setImageHeight(int imageHeight) {
        mMissileImageHeight = imageHeight;
    }

    /**
     * Sets the missile speed.
     * @param missileSpeed int representing the missile speed.
     */
    public void setMissileSpeed(int missileSpeed) {
        mMissileSpeed = missileSpeed;
    }

    /**
     * Sets the missile rotation.
     * @param missileRotation int representing the missile rotation.
     */
    public void setMissileRotation(int missileRotation) {
        mMissileRotation = missileRotation;
    }

    /**
     * Sets the missile coordinates.
     * @param missileCoordinate PointF representing the missile coordinate.
     */
    public void setMissileCoordinate(PointF missileCoordinate) {
        mMissileCoordinate = missileCoordinate;

        float boundWidth = Math.max(mMissileImageHeight, mMissileImageWidth);
        mMissileBound = new RectF(mMissileCoordinate.x - boundWidth/2.0f,
                mMissileCoordinate.y - boundWidth/2.0f, mMissileCoordinate.x + boundWidth/2.0f,
                mMissileCoordinate.y + boundWidth/2.0f);
    }

    /**
     * Sets the id used by the content manager to access media.
     * @param id
     */
    public void setID(int id) {
        mID = id;
    }

    /**
     * Updates the position of the missile
     */
    public void update() {
        GraphicsUtils.MoveObjectResult result  = GraphicsUtils.moveObject(mMissileCoordinate,
                mMissileBound, mMissileSpeed, GraphicsUtils.degreesToRadians(mMissileRotation-90),
                GameController.ELAPSED_TIME);

        mMissileCoordinate = result.getNewObjPosition();
        mMissileBound = result.getNewObjBounds();
    }

    /**
     * This is called by the Asteroid object that got hit by this Missile.
     */
    public void hit() {
        mHit = true;
    }

    /**
     * Checks if the Missiles have the same values.
     * @param missile Missile to check against.
     * @return true if the Missiles have the same values, false if not.
     */
    public boolean equals(Missile missile) {
        if(mMissileImageHeight != missile.getImageHeight())
            return false;
        if(!mMissileImagePath.equals(missile.getImagePath()))
            return false;
        if(mMissileImageWidth != missile.getImageWidth())
            return false;

        return true;
    }
//endregion COMMANDS
}
