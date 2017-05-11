package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.models.Engine;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by bergeson on 7/13/2016.
 */
public class EnginesDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static EnginesDAO instance;
    private static String sTableName = "engines";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private EnginesDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of an EnginesDAO.
     * @return EnginesDAO
     */
    public static EnginesDAO getInstance(){
        if(instance == null){
            instance = new EnginesDAO();
        }
        return instance;
    }

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

    //TODO: remove
//    /**
//     * Returns a list of all Engine objects in the engines table.
//     * @return List of all engines in the engines table.
//     */
//    public static List<Engine> getAll(){
//        //engines(baseSpeed, baseTurnRate, attachPoint, imagePath, imageWidth, imageHeight)";
//
//        ArrayList<Engine> retval = new ArrayList<>();
//        Cursor cursor = sDatabase.query(sTableName,null,null,null,null,null,null);
//        try{
//            cursor.moveToFirst();
//            while(!cursor.isAfterLast()){
//                Engine tempEngine = fillOutEngineInfo(cursor);
//                retval.add(tempEngine);
//                cursor.moveToNext();
//            }
//        }
//        finally{
//            cursor.close();
//        }
//
//        return retval;
//    }

    /**
     * Returns an Engine object specified by the <code>id</code>.
     * @param id int representing the Engine wanted.
     * @return Engine specified by the <code>id</code>.
     */
    public static Engine getEngine(int id){
        Engine retval;

        Cursor cursor = sDatabase.query(sTableName,
                null,
                "rowid = ?",
                new String[]{Integer.toString(id)},
                null, null, null);
        try{
            cursor.moveToFirst();
            retval = fillOutEngineInfo(cursor);
        }
        finally{
            cursor.close();
        }
        return retval;
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Clears all rows in the engines table.
     */
    public static void clearAll(){
        sDatabase.delete(sTableName, null, null);
    }

    /**
     * Adds an Engine to the engines table.
     * @param engine Engine representing the engine to be added to the engines table.
     * @return true if successful, false if not.
     */
    public static boolean addEngine(Engine engine){
        //// engines(baseSpeed, baseTurnRate, attachPoint, imagePath, imageWidth, imageHeight)";
        ContentValues values = new ContentValues();
        values.put("baseSpeed", engine.getEngineSpeed());
        values.put("baseTurnRate", engine.getEngineTurnRate());
        values.put("attachPoint", Spaceship.covertPointFtoString(engine.getAttachPoint()));
        values.put("imagePath", engine.getImagePath());
        values.put("imageWidth", engine.getImageWidth());
        values.put("imageHeight", engine.getImageHeight());

        long id = sDatabase.insert(sTableName, null, values);

        if(id >= 0) return true;
        else return false;
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private static Engine fillOutEngineInfo(Cursor cursor) {
        Engine tempEngine = new Engine();
        tempEngine.setEngineSpeed(cursor.getInt(cursor.getColumnIndex("baseSpeed")));
        tempEngine.setEngineTurnRate(cursor.getInt(cursor.getColumnIndex("baseTurnRate")));
        String attachPoint = cursor.getString(cursor.getColumnIndex("attachPoint"));
        tempEngine.setAttachPoint(Spaceship.convertStringToPointF(attachPoint));
        tempEngine.setImagePath(cursor.getString(cursor.getColumnIndex("imagePath")));
        tempEngine.setImageWidth(cursor.getInt(cursor.getColumnIndex("imageWidth")));
        tempEngine.setImageHeight(cursor.getInt(cursor.getColumnIndex("imageHeight")));
        return tempEngine;
    }
//endregion PRIVATE METHODS
}
