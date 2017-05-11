package edu.byu.cs.superasteroids.models;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import org.json.JSONException;
import org.json.JSONObject;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.GameController;

/**
 * Created by bergeson on 7/7/2016.
 */
public class Asteroid extends ImageObject {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private String mAsteroidName;
    private String mAsteroidImagePath;
    private int mAsteroidImageWidth;
    private int mAsteroidImageHeight;
    private ASTEROID_TYPE mAsteroidType;
    private int mAsteroidRotation;
    private int mAsteroidDirection;
    private int mAsteroidSpeed;
    private PointF mAsteroidPosition;
    private float mAsteroidScale;
    private RectF mAsteroidBound;
    private Level mLevel;
    private boolean mWasShot = false;
    private boolean mIsOriginal;

    public enum ASTEROID_TYPE {
        REGULAR,
        GROWING,
        OCTEROID
    }
//endregion DOMAIN

//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic Constructor.
     */
    public Asteroid(){}

    /**
     * Constructor that initializes all variables.
     * @param obj JSONObject representing an asteroid.
     */
    public  Asteroid(JSONObject obj) throws JSONException{
        mAsteroidName = obj.getString("name");
        mAsteroidImagePath = obj.getString("image");
        mAsteroidImageWidth = obj.getInt("imageWidth");
        mAsteroidImageHeight = obj.getInt("imageHeight");
        String type = obj.getString("type");
        if(type.equals("regular"))
            mAsteroidType = ASTEROID_TYPE.REGULAR;
        else if(type.equals("growing"))
            mAsteroidType = ASTEROID_TYPE.GROWING;
        else
            mAsteroidType = ASTEROID_TYPE.OCTEROID;
    }
//endregion CONSTRUCTORS

//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns the asteroid name.
     * @return String representing the asteroid name.
     */
    public String getAsteroidName() {
        return mAsteroidName;
    }

    /**
     * Returns the asteroid image path.
     * @return String representing the asteroid image path.
     */
    @Override
    public String getImagePath() {
        return mAsteroidImagePath;
    }

    /**
     * Returns the asteroid image width.
     * @return int representing the asteroid image width.
     */
    @Override
    public int getImageWidth() {
        return mAsteroidImageWidth;
    }

    /**
     * Returns the asteroid image height.
     * @return int representing the asteroid image height.
     */
    @Override
    public int getImageHeight() {
        return mAsteroidImageHeight;
    }

    /**
     * Returns the asteroid type.
     * @return ASTEROID_TYPE
     */
    public ASTEROID_TYPE getAsteroidType() {
        return mAsteroidType;
    }

    /**
     * Returns the current asteroid position.
     * @return
     */
    public PointF getAsteroidPosition() {
        return mAsteroidPosition;
    }

    /**
     * Returns whether or not this Asteroid was shot.
     * @return true if the Asteroid has been shot, false if not.
     */
    public boolean wasShot() { // TODO: remove
        return mWasShot;
    }

    /**
     * Check if the Asteroids have the same values.
     * @param asteroid the Asteroid to check against.
     * @return true if the Asteroids have the same values, false if not.
     */
    public boolean equals(Asteroid asteroid) {
        if(!mAsteroidName.equals(asteroid.getAsteroidName()))
            return false;
        if(!mAsteroidImagePath.equals(asteroid.getImagePath()))
            return false;
        if(mAsteroidImageWidth != asteroid.getImageWidth())
            return false;
        if(mAsteroidImageHeight != asteroid.getImageHeight())
            return false;
        if(mAsteroidType != asteroid.getAsteroidType())
            return false;
        return true;
    }
//endregion QUERIES

//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Sets the asteroid name.
     * @param asteroidName String representing the asteroid name.
     */
    public void setAsteroidName(String asteroidName) {
        mAsteroidName = asteroidName;
    }

    /**
     * Sets the asteroid image path.
     * @param asteroidImagePath String representing the asteroid image path.
     */
    @Override
    public void setImagePath(String asteroidImagePath) {
        mAsteroidImagePath = asteroidImagePath;
    }

    /**
     * Sets the asteroid image width.
     * @param asteroidImageWidth int representing the asteroid image width.
     */
    @Override
    public void setImageWidth(int asteroidImageWidth) {
        mAsteroidImageWidth = asteroidImageWidth;
    }

    /**
     * Sets the asteroid image height.
     * @param asteroidImageHeight int representing the asteroid image height.
     */
    @Override
    public void setImageHeight(int asteroidImageHeight) {
        mAsteroidImageHeight = asteroidImageHeight;
    }

    /**
     * Sets the asteroid type.
     * @param asteroidType ASTEROID_TYPE
     */
    public void setAsteroidType(ASTEROID_TYPE asteroidType) {
        mAsteroidType = asteroidType;
    }

    /**
     * Sets the asteroid type.
     * @param asteroidType String representing the asteroid type.
     */
    public void setAsteroidType(String asteroidType) {
        if(asteroidType.equals("regular"))
            mAsteroidType = ASTEROID_TYPE.REGULAR;
        else if(asteroidType.equals("growing"))
            mAsteroidType = ASTEROID_TYPE.GROWING;
        else
            mAsteroidType = ASTEROID_TYPE.OCTEROID;
    }

    /**
     * Sets the orientation of the asteroid.
     * @param rotation the rotation of the image.
     */
    public void setAsteroidRotation(int rotation) {
        mAsteroidRotation = rotation;
    }

    /**
     * Sets the direction the asteroid is heading in.
     * @param direction
     */
    public void setAsteroidDirection(int direction) {
        mAsteroidDirection = direction;
    }

    /**
     * Sets the speed the asteroid is moving at.
     * @param speed
     */
    public void setAsteroidSpeed(int speed) {
        mAsteroidSpeed = speed;
    }

    /**
     * Sets the asteroid starting position.
     * @param position PointF
     */
    public void setAsteroidPosition(PointF position) {
        mAsteroidPosition = position;
    }

    /**
     * Sets the asteroid scale.
     * @param scale
     */
    public void setAsteroidScale(float scale) {
        mAsteroidScale = scale;
    }

    /**
     * Sets the level that this asteroid belongs to.
     * @param level
     */
    public void setLevel(Level level) {
        mLevel = level;
    }

    /**
     * If this Asteroid is an original Asteroid created with a level set to true. Set to false if
     * this Asteorid is an Asteroid that was generated from another Asteroid.
     * @param isOriginal
     */
    public void setIsOriginal(boolean isOriginal) {
        mIsOriginal = isOriginal;
    }

    /**
     * Sets the RectF that is the bound for the asteroid. Only call this after the position is set.
     */
    public void setAsteroidBound() {
        float leftBound = mAsteroidPosition.x - ((float)mAsteroidImageWidth * mAsteroidScale)/4.0f;
        float topBound = mAsteroidPosition.y - ((float)mAsteroidImageHeight * mAsteroidScale)/4.0f;
        float rightBound = mAsteroidPosition.x + ((float)mAsteroidImageWidth * mAsteroidScale)/4.0f;
        float bottomBound = mAsteroidPosition.y + ((float)mAsteroidImageHeight * mAsteroidScale)/4.0f;
        mAsteroidBound = new RectF(leftBound, topBound, rightBound, bottomBound);
    }

    /**
     * Updates the information about the asteroid.
     */
    public void update(){
        GraphicsUtils.RicochetObjectResult ricochetObjectResult = GraphicsUtils.ricochetObject(
                mAsteroidPosition, mAsteroidBound, GraphicsUtils.degreesToRadians(mAsteroidDirection),
                mLevel.getLevelWidth(), mLevel.getLevelHeight());
        mAsteroidBound = ricochetObjectResult.getNewObjBounds();
        mAsteroidDirection = (int)GraphicsUtils.radiansToDegrees(ricochetObjectResult.getNewAngleRadians());
        mAsteroidPosition = ricochetObjectResult.getNewObjPosition();

        GraphicsUtils.MoveObjectResult moveObjectResult = GraphicsUtils.moveObject(
                mAsteroidPosition, mAsteroidBound, mAsteroidSpeed,
                GraphicsUtils.degreesToRadians(mAsteroidDirection), GameController.ELAPSED_TIME);
        mAsteroidBound = moveObjectResult.getNewObjBounds();
        mAsteroidPosition = moveObjectResult.getNewObjPosition();

        if(mAsteroidType == ASTEROID_TYPE.GROWING) {
            mAsteroidScale += 0.005;
            setAsteroidBound();
        }
        checkForCollision();
    }

    /**
     * Draws the asteroid
     */
    public void draw() {
        int id = ContentManager.getInstance().getImageId(mAsteroidImagePath);
        PointF viewCoordinates = ViewPort.getViewPortCoordinate(mAsteroidPosition);
        DrawingHelper.drawImage(id, viewCoordinates.x, viewCoordinates.y, mAsteroidRotation,
                mAsteroidScale, mAsteroidScale, 255);
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private void checkForCollision() {
        for(Missile missile : Spaceship.getMissiles()) {
            if(RectF.intersects(mAsteroidBound, missile.getMissileBound())) {
                gotShot(missile);
            }
        }

        if(RectF.intersects(mAsteroidBound, Spaceship.getSpaceshipBound())) {
            Spaceship.hit();
        }
    }

    private void gotShot(Missile missile) {
        mWasShot = true;
        missile.hit();
        if(mIsOriginal) {
            if(mAsteroidType == ASTEROID_TYPE.REGULAR) {
                split(2);
            }
            else if(mAsteroidType == ASTEROID_TYPE.GROWING) {
                split(2);
            }
            else {
                split(8);
            }
        }
    }

    private void split(int pieces) {
        for(int i = 0; i < pieces; i++) {
            Asteroid newAsteroid = new Asteroid();
            newAsteroid.setAsteroidName(mAsteroidName);
            newAsteroid.setImagePath(mAsteroidImagePath);
            newAsteroid.setImageWidth(mAsteroidImageWidth);
            newAsteroid.setImageHeight(mAsteroidImageHeight);
            newAsteroid.setAsteroidType(mAsteroidType);
            newAsteroid.setLevel(mLevel);
            newAsteroid.setAsteroidRotation((int)(Math.random() * 1000.0) % 360);
            newAsteroid.setAsteroidDirection((int)(Math.random() * 1000.0) % 360);
            newAsteroid.setAsteroidSpeed((int)(Math.random() * 1000.0) % 500);
            newAsteroid.setAsteroidPosition(mAsteroidPosition);
            newAsteroid.setAsteroidScale(mAsteroidScale/(float)pieces);
            newAsteroid.setAsteroidBound();
            newAsteroid.setIsOriginal(false);
            mLevel.addAsteroid(newAsteroid);
        }
    }
//endregion PRIVATE METHODS
}
