package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.models.Asteroid;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by bergeson on 7/13/2016.
 */
public class LevelAsteroidsDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static LevelAsteroidsDAO instance;
    private static String sTableName = "levelAsteroids";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private LevelAsteroidsDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of an LevelAsteroidsDAO.
     * @return LevelAsteroidsDAO
     */
    public static LevelAsteroidsDAO getInstance(){
        if(instance == null){
            instance = new LevelAsteroidsDAO();
        }
        return instance;
    }

    //TODO: remove
//    /**
//     * Returns a list of all Asteroid objects in the levelAsteroids table.
//     * @return List of all asteroids in the levelAsteroids table.
//     */
//    public static List<Asteroid> getAll(){
//        return null;
//    }

    /**
     * Returns an ArrayList of all asteroids related to the specified level.
     * @param level the level to be queried
     * @return ArrayList with all the asteroids related to the level.
     */
    public static ArrayList<Asteroid> getAllForLevel(int level) {
        ArrayList<Asteroid> retval = new ArrayList<>();
        Cursor cursor = sDatabase.query(sTableName, null, null, null,null,null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                if(cursor.getInt(cursor.getColumnIndex("level")) == level) {
                    int count = cursor.getInt(cursor.getColumnIndex("count"));
                    for(int i = 0; i < count; i++) {
                        int asteroidId = cursor.getInt(cursor.getColumnIndex("asteroidId"));
                        Asteroid tempAsteroid = AsteroidTypesDAO.getAsteroid(asteroidId);
                        retval.add(tempAsteroid);
                    }
                }
                cursor.moveToNext();
            }
        }
        finally{
            cursor.close();
        }

        return retval;
    }

    //TODO: remove
//    /**
//     * Returns an Asteroid object specified by the <code>id</code>.
//     * @param id int representing the Asteroid wanted.
//     * @return Asteroid specified by the <code>id</code>.
//     */
//    public static Asteroid getAsteroid(int id){
//        return null;
//    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Clears all rows in the levelAsteroids table.
     */
    public static void clearAll(){
        sDatabase.delete("levelAsteroids", null, null);
    }

    /**
     * Adds an Asteroid to the levelAsteroids table.
     * @param count number of asteroids of this asteroid type for the specified level
     * @param asteroidId the id of the asteroid
     * @param level the level the asteroids belong to.
     * @return true if successful, false if not.
     */
    public static boolean addAsteroid(int count, int asteroidId, int level){
        ContentValues values = new ContentValues();
        values.put("count", count);
        values.put("asteroidId", asteroidId);
        values.put("level", level);

        long id = sDatabase.insert("levelAsteroids", null, values);

        if(id >= 0) return true;
        else return false;
    }
//endregion COMMANDS
}
