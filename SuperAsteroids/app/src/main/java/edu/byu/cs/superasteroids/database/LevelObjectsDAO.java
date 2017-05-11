package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.models.BackgroundObject;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by bergeson on 7/13/2016.
 */
public class LevelObjectsDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static LevelObjectsDAO instance;
    private static String sTableName = "levelObjects";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private LevelObjectsDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of an LevelObjectsDAO.
     * @return LevelObjectsDAO
     */
    public static LevelObjectsDAO getInstance(){
        if(instance == null){
            instance = new LevelObjectsDAO();
        }
        return instance;
    }

    //TODO: remove
//    /**
//     * Returns a list of all BackgroundObject objects in the levelObjects table.
//     * @return List of all BackgroundObjects in the levelObjects table.
//     */
//    public static List<BackgroundObject> getAll(){
//        return null;
//    }

    /**
     * Returns an ArrayList of all BackgroundObjects related to the specified level.
     * @param level the level to be queried.
     * @return ArrayList
     */
    public static ArrayList<BackgroundObject> getAllForLevel(int level) {
        ArrayList<BackgroundObject> retval = new ArrayList<>();
        Cursor cursor = sDatabase.query(sTableName, null, null,null,null, null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                if(cursor.getInt(cursor.getColumnIndex("level")) == level) {
                    int objectId = cursor.getInt(cursor.getColumnIndex("objectId"));
                    BackgroundObject tempObject = ObjectsDAO.getObject(objectId);
                    String position = cursor.getString(cursor.getColumnIndex("position"));
                    tempObject.setObjectCoordinates(Spaceship.convertStringToPointF(position));
                    tempObject.setObjectScale(cursor.getDouble(cursor.getColumnIndex("scale")));
                    retval.add(tempObject);
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
//     * Returns an BackgroundObject object specified by the <code>id</code>.
//     * @param id int representing the BackgroundObject wanted.
//     * @return BackgroundObject specified by the <code>id</code>.
//     */
//    public static BackgroundObject getLevelObject(int id){
//        return null;
//    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Clears all rows in the levelObjects table.
     */
    public static void clearAll(){
        sDatabase.delete(sTableName, null, null);
    }

    /**
     * Adds a BackgroundObject to the levelObjects table.
     * @param position String representing the coordinates of the background object.
     * @param objectId the id of the background object.
     * @param scale the scale of the background object.
     * @param level the level this background object belongs to.
     * @return true if successful, false if not.
     */
    public static boolean addLevelObject(String position, int objectId, double scale, int level){
        //final String createLevelObjectsTable = "CREATE TABLE levelObjects(position, objectId, scale, level)";
        ContentValues values = new ContentValues();
        values.put("position", position);
        values.put("objectId", objectId);
        values.put("scale", scale);
        values.put("level", level);

        long id = sDatabase.insert(sTableName, null, values);

        if(id >= 0) return true;
        else return false;

    }
//endregion COMMANDS
}
