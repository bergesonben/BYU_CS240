package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import org.json.JSONException;
import org.json.JSONObject;

import edu.byu.cs.superasteroids.content.Sound;

/**
 * Created by bergeson on 7/7/2016.
 */
public class Cannon extends AttachablePart {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private PointF mCannonAttachPoint;
    private String mCannonImagePath;
    private int mCannonImageWidth;
    private int mCannonImageHeight;
    private PointF mAttackEmitPoint;
    private Missile mMissile;
    private Sound mAttackSound;
    private int mAttackDamage;
    private int mID;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic constructor.
     */
    public Cannon() {
        mCannonAttachPoint = new PointF();
        mCannonImagePath = "";
        mCannonImageWidth = 0;
        mCannonImageHeight = 0;
        mAttackEmitPoint = new PointF();
        mMissile = new Missile();
        mAttackSound = null;
        mAttackDamage = 0;
    }

    /**
     * Constructor that initializes all variables.
     * @param obj JSONObject representing a Cannon object.
     */
    public Cannon(JSONObject obj) throws JSONException {
        String cannonAttachPoint = obj.getString("attachPoint");
        mCannonAttachPoint = Spaceship.convertStringToPointF(cannonAttachPoint);
        String attackEmitPoint = obj.getString("emitPoint");
        mAttackEmitPoint = Spaceship.convertStringToPointF(attackEmitPoint);
        mCannonImagePath = obj.getString("image");
        mCannonImageWidth = obj.getInt("imageWidth");
        mCannonImageHeight = obj.getInt("imageHeight");
        mMissile = new Missile();
        mMissile.setImagePath(obj.getString("attackImage"));
        mMissile.setImageWidth(obj.getInt("attackImageWidth"));
        mMissile.setImageHeight(obj.getInt("attackImageHeight"));
        mAttackSound = new Sound(-1, obj.getString("attackSound"));
        mAttackDamage = obj.getInt("damage");
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Gets the cannon attach point.
     * @return PointF representing the cannon attach point.
     */
    @Override
    public PointF getAttachPoint() {
        return mCannonAttachPoint;
    }

    /**
     * Returns the cannon image path.
     * @return String representing the image path.
     */
    @Override
    public String getImagePath() {
        return mCannonImagePath;
    }

    /**
     * Returns the cannon image width.
     * @return int representing the cannon image width.
     */
    @Override
    public int getImageWidth() {
        return mCannonImageWidth;
    }

    /**
     * Returns the cannon image height.
     * @return int representing the cannon image height.
     */
    @Override
    public int getImageHeight() {
        return mCannonImageHeight;
    }

    /**
     * Returns the attack emit point.
     * @return PointF representing the attack emit point.
     */
    public PointF getAttackEmitPoint() {
        return mAttackEmitPoint;
    }

    /**
     * Returns the missile object that belongs to this cannon.
     * @return Missile the belongs to this cannon.
     */
    public Missile getMissile() {
        return mMissile;
    }

    /**
     * Returns the sound made by the attack.
     * @return Sound made by the attack.
     */
    public Sound getAttackSound() {
        return mAttackSound;
    }

    /**
     * Returns the amount of damage done by the attack from this cannon.
     * @return int representing the amount of damage done by this cannon.
     */
    public int getAttackDamage() {
        return mAttackDamage;
    }

    /**
     * Returns the id number that the content manager uses to keep track of images.
     * @return
     */
    public int getID() {
        return mID;
    }

    /**
     * Returns the center point of the Cannon.
     * @return PointF representing the center point of the Cannon.
     */
    public PointF getCenterPoint() {
        PointF center = new PointF();
        center.x = mCannonImageWidth/2;
        center.y = mCannonImageHeight/2;
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
    @Override
    public void setAttachPoint(PointF cannonAttachPoint) {
        mCannonAttachPoint = cannonAttachPoint;
    }

    /**
     * Sets the cannon image path.
     * @param cannonImagePath String representing the cannon image path.
     */
    @Override
    public void setImagePath(String cannonImagePath) {
        mCannonImagePath = cannonImagePath;
    }

    /**
     * Sets the cannon image width.
     * @param cannonImageWidth int representing the cannon image width.
     */
    @Override
    public void setImageWidth(int cannonImageWidth) {
        mCannonImageWidth = cannonImageWidth;
    }

    /**
     * Sets the cannon image height.
     * @param cannonImageHeight int representing the cannon image height.
     */
    @Override
    public void setImageHeight(int cannonImageHeight) {
        mCannonImageHeight = cannonImageHeight;
    }

    /**
     * Sets the attack emit point.
     * @param attackEmitPoint PointF representing the attack emit point.
     */
    public void setAttackEmitPoint(PointF attackEmitPoint) {
        mAttackEmitPoint = attackEmitPoint;
    }

    /**
     * Sets the missile used by this cannon.
     * @param missile Missile object used by this cannon.
     */
    public void setMissile(Missile missile) {
        mMissile = missile;
    }

    /**
     * Set the attack sound.
     * @param attackSound Sound representing the attack sound.
     */
    public void setAttackSound(Sound attackSound) {
        mAttackSound = attackSound;
    }

    /**
     * Set the attack damage.
     * @param attackDamage int represnting the attack damage.
     */
    public void setAttackDamage(int attackDamage) {
        mAttackDamage = attackDamage;
    }

    /**
     * Sets the id number that the content manager uses to keep track of images.
     * @param id
     */
    public void setID(int id) {
        mID = id;
    }

    /**
     * Checks the Cannons have the same values.
     * @param cannon Cannon to be check against.
     * @return true if the Cannons have the same values, false if not.
     */
    public boolean equals(Cannon cannon) {
        if(mAttackDamage != cannon.getAttackDamage())
            return false;
        if(!mAttackEmitPoint.equals(cannon.getAttackEmitPoint()))
            return false;
        if(!mAttackSound.filePath.equals(cannon.getAttackSound().filePath))
            return false;
        if(!mCannonAttachPoint.equals(cannon.getAttachPoint()))
            return false;
        if(mCannonImageHeight != cannon.getImageHeight())
            return false;
        if(!mCannonImagePath.equals(cannon.getImagePath()))
            return false;
        if(mCannonImageWidth != cannon.getImageWidth())
            return false;
        if(!mMissile.equals(cannon.getMissile()))
            return false;

        return true;
    }
//endregion COMMANDS
}
