package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.Vector;

import edu.byu.cs.superasteroids.importer.IGameDataImporter;
import edu.byu.cs.superasteroids.models.Asteroid;
import edu.byu.cs.superasteroids.models.BackgroundObject;
import edu.byu.cs.superasteroids.models.Cannon;
import edu.byu.cs.superasteroids.models.Engine;
import edu.byu.cs.superasteroids.models.ExtraPart;
import edu.byu.cs.superasteroids.models.Level;
import edu.byu.cs.superasteroids.models.MainBody;
import edu.byu.cs.superasteroids.models.PowerCore;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by bergeson on 7/13/2016.
 */
public class DataImporter implements IGameDataImporter {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private SQLiteDatabase mDatabase;
    private DBOpenHelper mDBOpenHelper;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Default Constructor. Creates a database.
     * @param context
     */
    public DataImporter(Context context){
        mDBOpenHelper = new DBOpenHelper(context);
        mDatabase = mDBOpenHelper.getWritableDatabase();
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Imports the necessary game data.
     * @param dataInputReader The InputStreamReader connected to the .json file needing to be imported.
     * @return true if successful, false if not.
     */
    @Override
    public boolean importData(InputStreamReader dataInputReader) {
        StringBuilder sb = new StringBuilder();
        try {
            char c = (char)dataInputReader.read();
            while(c != (char)-1) {
                sb.append(c);
                c = (char)dataInputReader.read();
            }
        } catch(IOException io) {
            io.printStackTrace();
        }

        clearAllTables();

        JSONObject root;
        try {
            root = new JSONObject(sb.toString());
            boolean successful = importAsteroidsGame(root);
            if(!successful) return false;
        } catch (JSONException json) {
            json.printStackTrace();
        }


        return true;
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private void clearAllTables() {
        AsteroidTypesDAO.getInstance().clearAll();
        CannonsDAO.getInstance().clearAll();
        EnginesDAO.getInstance().clearAll();
        ExtraPartsDAO.getInstance().clearAll();
        LevelAsteroidsDAO.getInstance().clearAll();
        LevelObjectsDAO.getInstance().clearAll();
        LevelsDAO.getInstance().clearAll();
        MainBodiesDAO.getInstance().clearAll();
        ObjectsDAO.getInstance().clearAll();
        PowerCoresDAO.getInstance().clearAll();
    }

    private boolean importAsteroidsGame(JSONObject obj) throws JSONException {
        JSONObject asteroidsGame = obj.getJSONObject("asteroidsGame");
        boolean successful = importObjects(asteroidsGame);
        if(!successful) return false;
        successful = importAsteroids(asteroidsGame);
        if(!successful) return false;
        successful = importLevels(asteroidsGame);
        if(!successful) return false;
        successful = importMainBodies(asteroidsGame);
        if(!successful) return false;
        successful = importCannons(asteroidsGame);
        if(!successful) return false;
        successful = importExtraParts(asteroidsGame);
        if(!successful) return false;
        successful = importEngines(asteroidsGame);
        if(!successful) return false;
        successful = importPowerCores(asteroidsGame);
        if(!successful) return false;

        return true;
    }

    private boolean importObjects(JSONObject asteroidsGame) throws JSONException {
        JSONArray objects = asteroidsGame.getJSONArray("objects");
        for(int i = 0; i < objects.length(); i++) {
            BackgroundObject backgroundObject = new BackgroundObject(objects.getString(i));
            boolean successful = ObjectsDAO.getInstance().addObject(backgroundObject);
            if(!successful) return false;
        }
        return true;
    }

    private boolean importAsteroids(JSONObject asteroidsGame) throws JSONException {
        JSONArray asteroids = asteroidsGame.getJSONArray("asteroids");
        for(int i = 0; i < asteroids.length(); ++i) {
            Asteroid asteroid = new Asteroid(asteroids.getJSONObject(i));
            boolean successful = AsteroidTypesDAO.getInstance().addAsteroid(asteroid);
            if(!successful) return false;
        }
        return true;
    }

    private boolean importLevels(JSONObject asteroidsGame) throws JSONException {
        JSONArray levels = asteroidsGame.getJSONArray("levels");
        for(int i = 0; i < levels.length(); ++i) {
            JSONObject levelObject = levels.getJSONObject(i);
            Level level = new Level(levelObject);
            boolean successful = LevelsDAO.getInstance().addLevel(level);
            if(!successful) return false;
            successful = importLevelAsteroids(levelObject);
            if(!successful) return false;
            successful = importLevelObjects(levelObject);
            if(!successful) return false;
        }
        return true;
    }

    private boolean importLevelAsteroids(JSONObject levelObject) throws JSONException {
        int levelValue = levelObject.getInt("number");
        JSONArray levelAsteroids = levelObject.getJSONArray("levelAsteroids");

        for(int i = 0; i < levelAsteroids.length(); i++) {
            JSONObject levelAsteroidObject = levelAsteroids.getJSONObject(i);
            int count = levelAsteroidObject.getInt("number");
            int id = levelAsteroidObject.getInt("asteroidId");
            boolean successful = LevelAsteroidsDAO.addAsteroid(count, id, levelValue);
            if(!successful) return false;
        }

        return true;
    }

    private boolean importLevelObjects(JSONObject levelJSONObject) throws JSONException {
        int levelValue = levelJSONObject.getInt("number");
        JSONArray levelObjects = levelJSONObject.getJSONArray("levelObjects");

        for(int i = 0; i < levelObjects.length(); i++) {
            JSONObject levelObject = levelObjects.getJSONObject(i);
            String position = levelObject.getString("position");
            int id = levelObject.getInt("objectId");
            double scale = levelObject.getDouble("scale");
            boolean successful = LevelObjectsDAO.addLevelObject(position, id, scale,levelValue);
            if(!successful) return false;
        }
        return true;
    }

    private boolean importMainBodies(JSONObject asteroidsGame) throws JSONException {
        JSONArray mainBodies = asteroidsGame.getJSONArray("mainBodies");
        for(int i = 0; i < mainBodies.length(); ++i) {
            MainBody mainbody = new MainBody(mainBodies.getJSONObject(i));
            boolean successful = MainBodiesDAO.getInstance().addMainBody(mainbody);
            if(!successful) return false;
        }

        return true;
    }

    private boolean importCannons(JSONObject asteroidsGame) throws JSONException {
        JSONArray cannons = asteroidsGame.getJSONArray("cannons");
        for(int i = 0; i < cannons.length(); ++i) {
            Cannon cannon = new Cannon(cannons.getJSONObject(i));
            boolean successful = CannonsDAO.getInstance().addCannon(cannon);
            if(!successful) return false;
        }

        return true;
    }

    private boolean importExtraParts(JSONObject asteroidsGame) throws JSONException {
        JSONArray extraParts = asteroidsGame.getJSONArray("extraParts");
        for(int i = 0; i < extraParts.length(); ++i) {
            ExtraPart extraPart = new ExtraPart(extraParts.getJSONObject(i));
            boolean successful = ExtraPartsDAO.getInstance().addExtraPart(extraPart);
            if(!successful) return false;
        }

        return true;
    }

    private boolean importEngines(JSONObject asteroidsGame) throws JSONException {
        JSONArray engines = asteroidsGame.getJSONArray("engines");
        for(int i = 0; i < engines.length(); ++i) {
            Engine engine = new Engine(engines.getJSONObject(i));
            boolean successful = EnginesDAO.getInstance().addEngine(engine);
            if(!successful) return false;
        }

        return true;
    }

    private boolean importPowerCores(JSONObject asteroidsGame) throws JSONException {
        JSONArray powerCores = asteroidsGame.getJSONArray("powerCores");
        for(int i = 0; i < powerCores.length(); ++i) {
            PowerCore powerCore = new PowerCore(powerCores.getJSONObject(i));
            boolean successful = PowerCoresDAO.getInstance().addPowerCore(powerCore);
            if(!successful) return false;
        }

        return true;
    }
//endregion PRIVATE METHODS
}
