package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import edu.byu.cs.superasteroids.content.Sound;
import edu.byu.cs.superasteroids.database.AsteroidTypesDAO;
import edu.byu.cs.superasteroids.database.ObjectsDAO;

/**
 * Created by bergeson on 7/7/2016.
 */
public class Level {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private int mLevelValue;
    private String mLevelTitle;
    private String mLevelHint;
    private int mLevelWidth;
    private int mLevelHeight;
    private Sound mLevelMusic;
    private ArrayList<BackgroundObject> mLevelObjects;
    private ArrayList<Asteroid> mLevelAsteroids;
    private ArrayList<Asteroid> mTempAsteroids = new ArrayList<>();
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic Constructor.
     */
    public Level(){
        mLevelValue = 0;
        mLevelTitle = "";
        mLevelHint = "";
        mLevelWidth = 0;
        mLevelHeight = 0;
    }

    /**
     * Constructor that initializes all variables.
     * @param obj JSONObject representing a level.
     */
    public Level(JSONObject obj) throws JSONException {
        mLevelValue = obj.getInt("number");
        mLevelTitle = obj.getString("title");
        mLevelHint = obj.getString("hint");
        mLevelWidth = obj.getInt("width");
        mLevelHeight = obj.getInt("height");
        mLevelMusic = new Sound(-1, obj.getString("music"));
        mLevelObjects = new ArrayList<>();
        addBackgroundObjects(obj.getJSONArray("levelObjects"));
        mLevelAsteroids = new ArrayList<>();
        addLevelAsteroids(obj.getJSONArray("levelAsteroids"));
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns the level number
     * @return int representing the level number.
     */
    public int getLevelValue() {
        return mLevelValue;
    }

    /**
     * Returns the level title.
     * @return String representing the level title.
     */
    public String getLevelTitle() {
        return mLevelTitle;
    }

    /**
     * Returns the level hint.
     * @return String representing the level hint.
     */
    public String getLevelHint() {
        return mLevelHint;
    }

    /**
     * Returns the level width.
     * @return int representing the level width.
     */
    public int getLevelWidth() {
        return mLevelWidth;
    }

    /**
     * Returns the level height.
     * @return int representing the level height.
     */
    public int getLevelHeight() {
        return mLevelHeight;
    }

    /**
     * Returns the level music.
     * @return Sound representing the level music.
     */
    public Sound getLevelMusic() {
        return mLevelMusic;
    }

    //TODO: remove
//    /**
//     * Returns an array of background objects.
//     * @return BackgroundObject[] representing an array of background objects.
//     */
//    public ArrayList<BackgroundObject> getLevelObjects() {
//        return mLevelObjects;
//    }

    /**
     * Returns an array of asteroids in the level.
     * @return Asteroid[] representing an array of asteroids.
     */
    public ArrayList<Asteroid> getLevelAsteroids() {
        return mLevelAsteroids;
    }

    /**
     * Returns true if the Level is out of Asteroids.
     * @return
     */
    public boolean outOfAsteroids() {
        return mLevelAsteroids.isEmpty();
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Sets the level number.
     * @param levelValue int representing the level number.
     */
    public void setLevelValue(int levelValue) {
        mLevelValue = levelValue;
    }

    /**
     * Sets the level title.
     * @param levelTitle String representing the level title.
     */
    public void setLevelTitle(String levelTitle) {
        mLevelTitle = levelTitle;
    }

    /**
     * Sets the level hint.
     * @param levelHint String representing the level hint.
     */
    public void setLevelHint(String levelHint) {
        mLevelHint = levelHint;
    }

    /**
     * Sets the level width.
     * @param levelWidth int representing the level width.
     */
    public void setLevelWidth(int levelWidth) {
        mLevelWidth = levelWidth;
    }

    /**
     * Sets the level height.
     * @param levelHeight int representing the level height.
     */
    public void setLevelHeight(int levelHeight) {
        mLevelHeight = levelHeight;
    }

    //TODO: remove
//    /**
//     * Sets the level music.
//     * @param levelMusic Sound representing the level music.
//     */
//    public void setLevelMusic(Sound levelMusic) {
//        mLevelMusic = levelMusic;
//    }

    /**
     * Sets the level background objects.
     * @param levelObjects BackgroundObject[] representing an array of background objects.
     */
    public void setLevelObjects(ArrayList<BackgroundObject> levelObjects) {
        mLevelObjects = levelObjects;
    }

    /**
     * Sets the level asteroids.
     * @param levelAsteroids Asteroids[] representing an array of asteroids.
     */
    public void setLevelAsteroids(ArrayList<Asteroid> levelAsteroids) {
        mLevelAsteroids = levelAsteroids;
    }

    /**
     * Updates the asteroids
     */
    public void update(){
        //updates the positions of the asteroids

        Iterator<Asteroid> it = mLevelAsteroids.iterator();
        while(it.hasNext()) {
            Asteroid asteroid = it.next();
            asteroid.update();
            if(asteroid.wasShot()) it.remove();
        }

        mLevelAsteroids.addAll(mTempAsteroids);
        mTempAsteroids.clear();
    }

    /**
     * Draws the asteroids.
     */
    public void draw(){
        //draws the background objects
        for(BackgroundObject obj : mLevelObjects) {
            obj.draw();
        }

        //draws the asteroids
        for(Asteroid asteroid : mLevelAsteroids) {
            asteroid.draw();
        }
    }

    /**
     * Initializes the values for all the asteroids on the level.
     */
    public void initializeAsteroids() {
        for(Asteroid asteroid : mLevelAsteroids) {
            asteroid.setLevel(this);
            asteroid.setAsteroidRotation((int)(Math.random() * 1000.0) % 360);
            asteroid.setAsteroidDirection((int)(Math.random() * 1000.0) % 360);
            asteroid.setAsteroidSpeed((int)(Math.random() * 1000.0) % 500);
            PointF randomPosition = new PointF();
            int tempX = (int)(Math.random() * 10000.0) % (mLevelWidth/2 - 100);
            if((Math.random() * 10.0 % 2) == 1) tempX = tempX + (mLevelWidth/2 + 100);
            randomPosition.x = tempX;
            int tempY = (int)(Math.random() * 10000.0) % (mLevelHeight/2 - 100);
            if((Math.random() * 10.0 % 2) == 1) tempY = tempY + (mLevelHeight/2 + 100);
            randomPosition.y = tempY;
            asteroid.setAsteroidPosition(randomPosition);
            asteroid.setAsteroidScale((float)Math.random() + 1);
            asteroid.setAsteroidBound();
            asteroid.setIsOriginal(true);
        }
    }

    /**
     * Adds an asteroid to the level.
     * @param asteroid
     */
    public void addAsteroid(Asteroid asteroid) {
        mTempAsteroids.add(asteroid);
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private void addBackgroundObjects(JSONArray levelObjects) throws JSONException {
        for(int i = 0; i < levelObjects.length(); i++) {
            JSONObject oneObject = levelObjects.getJSONObject(i);
            BackgroundObject object = ObjectsDAO.getObject(oneObject.getInt("objectId"));
            object.setObjectCoordinates(Spaceship.convertStringToPointF(oneObject.getString("position")));
            object.setObjectScale(oneObject.getDouble("scale"));
            mLevelObjects.add(object);
        }
    }

    private void addLevelAsteroids(JSONArray levelAsteroids) throws JSONException {
        for(int i = 0; i < levelAsteroids.length(); i++) {
            JSONObject asteroidObject = levelAsteroids.getJSONObject(i);
            Asteroid asteroid = AsteroidTypesDAO.getAsteroid(asteroidObject.getInt("asteroidId"));
            for(int j = 0; j < asteroidObject.getInt("number"); j++) {
                mLevelAsteroids.add(asteroid);
            }
        }
    }
//endregion PRIVATE METHODS
}
