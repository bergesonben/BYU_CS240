package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.models.BackgroundObject;

/**
 * Created by bergeson on 7/13/2016.
 */
public class ObjectsDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static ObjectsDAO instance;
    private static String sTableName = "objects";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private ObjectsDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of an ObjectsDAO.
     * @return ObjectsDAO
     */
    public static ObjectsDAO getInstance(){
        if(instance == null){
            instance = new ObjectsDAO();
        }
        return instance;
    }

    //TODO: remove
//    /**
//     * Returns a list of all BackgroundObject objects in the objects table.
//     * @return List of all backgroundobjects in the objects table.
//     */
//    public static List<BackgroundObject> getAll(){
//        return null;
//    }

    /**
     * Returns a list of all BackgroundObject paths.
     * @return List of all BackgroundObject paths.
     */
    public static List<String> getImages() {
        ArrayList<String> retval = new ArrayList<>();
        Cursor cursor = sDatabase.query(sTableName,null,null,null,null,null,null);
        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                retval.add(cursor.getString(cursor.getColumnIndex("objPath")));
                cursor.moveToNext();
            }
        }
        finally{
            cursor.close();
        }

        return retval;
    }

    /**
     * Returns a BackgroundObject specified by the <code>id</code>.
     * @param id int representing the BackgroundObject wanted.
     * @return BackgroundObject specified by the <code>id</code>.
     */
    public static BackgroundObject getObject(int id){
        BackgroundObject retval = new BackgroundObject();

        Cursor cursor = sDatabase.query("objects",
                null,
                "rowid = ?",
                new String[]{Integer.toString(id)},
                null, null, null);
        try{
            cursor.moveToFirst();
            retval.setImagePath(cursor.getString(cursor.getColumnIndex("objPath")));
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
     * Clears all rows in the objects table.
     */
    public static void clearAll(){
        sDatabase.delete("objects", null, null);
    }

    /**
     * Adds a BackgroundObject to the objects table.
     * @param object BackgroundObject representing the BackgroundObject to be added to the objects table.
     */
    public static boolean addObject(BackgroundObject object){
        ContentValues values = new ContentValues();
        values.put("objPath", object.getImagePath());

        long id = sDatabase.insert("objects", null, values);

        if(id >= 0) return true;
        else return false;
    }
//endregion COMMANDS
}
