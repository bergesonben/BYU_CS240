package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.models.Asteroid;

/**
 * Created by bergeson on 7/13/2016.
 */
public class AsteroidTypesDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static AsteroidTypesDAO instance;
    private static String sTableName = "asteroidTypes";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private AsteroidTypesDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of an AsteroidTypesDAO.
     * @return AsteroidTypesDAO
     */
    public static AsteroidTypesDAO getInstance(){
        if(instance == null){
            instance = new AsteroidTypesDAO();
        }
        return instance;
    }

    /**
     * Returns a list of all asteroid type image paths.
     * @return
     */
    public static List<String> getImages() {
        ArrayList<String> retval = new ArrayList<>();
        Cursor cursor = sDatabase.query(sTableName,null,null,null,null,null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                retval.add(cursor.getString(cursor.getColumnIndex("imagePath")));
                cursor.moveToNext();
            }
        }
        finally{
            cursor.close();
        }

        return retval;
    }

//    /**
//     * Returns a list of all Asteroid objects in the asteroidTypes table.
//     * @return List of all asteroids in the asteroidTypes table.
//     */
//    public static List<Asteroid> getAll(){
//        //TODO: remove
//        return null;
//    }

    /**
     * Returns an Asteroid object specified by the <code>id</code>.
     * @param id int representing the Asteroid wanted.
     * @return Asteroid specified by the <code>id</code>.
     */
    public static Asteroid getAsteroid(int id){
        Asteroid output = new Asteroid();

        Cursor cursor = sDatabase.query("asteroidTypes",
                null,
                "rowid = ?",
                new String[]{Integer.toString(id)},
                null, null, null);
        try{
            cursor.moveToFirst();
            output.setAsteroidName(cursor.getString(cursor.getColumnIndex("name")));
            output.setImagePath(cursor.getString(cursor.getColumnIndex("imagePath")));
            output.setImageWidth(cursor.getInt(cursor.getColumnIndex("imageWidth")));
            output.setImageHeight(cursor.getInt(cursor.getColumnIndex("imageHeight")));
            output.setAsteroidType(cursor.getString(cursor.getColumnIndex("type")));
        }
        finally{
            cursor.close();
        }
        return output;
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Clears all rows in the asteroidTypes table.
     */
    public static void clearAll(){
        sDatabase.delete("asteroidTypes", null, null);
    }

    /**
     * Adds an Asteroid to the asteroidTypes table.
     * @param asteroid Asteroid representing the asteroid to be added to the asteroidTypes table.
     */
    public static boolean addAsteroid(Asteroid asteroid){
        ContentValues values = new ContentValues();
        values.put("name", asteroid.getAsteroidName());
        values.put("imagePath", asteroid.getImagePath());
        values.put("imageWidth", asteroid.getImageWidth());
        values.put("imageHeight", asteroid.getImageHeight());
        String type;
        if(asteroid.getAsteroidType() == Asteroid.ASTEROID_TYPE.REGULAR)
            type = "regular";
        else if(asteroid.getAsteroidType() == Asteroid.ASTEROID_TYPE.GROWING)
            type = "growing";
        else
            type = "octeroid";
        values.put("type", type);

        long id = sDatabase.insert("asteroidTypes", null, values);

        if(id >= 0) return true;
        else return false;
    }
//endregion COMMANDS
}
