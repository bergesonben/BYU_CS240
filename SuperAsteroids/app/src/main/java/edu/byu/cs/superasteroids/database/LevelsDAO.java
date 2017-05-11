package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import edu.byu.cs.superasteroids.content.Sound;
import edu.byu.cs.superasteroids.models.Level;

/**
 * Created by bergeson on 7/13/2016.
 */
public class LevelsDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static LevelsDAO instance;
    private static String sTableName = "levels";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private LevelsDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of an LevelsDAO.
     * @return LevelsDAO
     */
    public static LevelsDAO getInstance(){
        if(instance == null){
            instance = new LevelsDAO();
        }
        return instance;
    }

    //TODO: remove
//    /**
//     * Returns a list of all Level objects in the levels table.
//     * @return List of all levels in the levels table.
//     */
//    public static List<Level> getAll(){
//        return null;
//    }

    /**
     * Returns an Level object specified by the <code>id</code>.
     * @param id int representing the Level wanted.
     * @return Level specified by the <code>id</code>.
     */
    public static Level getLevel(int id){
        //levels(number, title, hint, width, height, musicPath)";

        Level output = new Level();

        Cursor cursor = sDatabase.query(sTableName,
                null,
                "rowid = ?",
                new String[]{Integer.toString(id)},
                null, null, null);
        try{
            cursor.moveToFirst();
            output.setLevelValue(cursor.getInt(cursor.getColumnIndex("number")));
            output.setLevelTitle(cursor.getString(cursor.getColumnIndex("title")));
            output.setLevelHint(cursor.getString(cursor.getColumnIndex("hint")));
            output.setLevelWidth(cursor.getInt(cursor.getColumnIndex("width")));
            output.setLevelHeight(cursor.getInt(cursor.getColumnIndex("height")));
            Sound tempSound = new Sound(-1, cursor.getString(cursor.getColumnIndex("musicPath")));
            output.setLevelObjects(LevelObjectsDAO.getAllForLevel(output.getLevelValue()));
            output.setLevelAsteroids(LevelAsteroidsDAO.getAllForLevel(output.getLevelValue()));
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
     * Clears all rows in the levels table.
     */
    public static void clearAll(){
        sDatabase.delete("levels", null, null);
    }

    /**
     * Adds an Level to the levels table.
     * @param level Level representing the level to be added to the levels table.
     */
    public static boolean addLevel(Level level){
        ContentValues values = new ContentValues();
        values.put("number", level.getLevelValue());
        values.put("title", level.getLevelTitle());
        values.put("hint", level.getLevelHint());
        values.put("width", level.getLevelWidth());
        values.put("height", level.getLevelHeight());
        values.put("musicPath", level.getLevelMusic().filePath);

        long id = sDatabase.insert("levels", null, values);

        if(id >= 0) return true;
        else return false;
    }
//endregion COMMANDS
}
