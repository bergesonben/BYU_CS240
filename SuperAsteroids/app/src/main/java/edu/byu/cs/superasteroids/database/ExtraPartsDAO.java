package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.models.ExtraPart;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by bergeson on 7/13/2016.
 */
public class ExtraPartsDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static ExtraPartsDAO instance;
    private static String sTableName = "extraParts";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private ExtraPartsDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of an ExtraPartsDAO.
     * @return ExtraPartsDAO
     */
    public static ExtraPartsDAO getInstance(){
        if(instance == null){
            instance = new ExtraPartsDAO();
        }
        return instance;
    }

    /**
     * Returns a list of all ExtraPart image paths.
     * @return List of all ExtraPart image paths.
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

    //TODO: remove
//    /**
//     * Returns a list of all ExtraPart objects in the engines table.
//     * @return List of all ExtraPart in the extraParts table.
//     */
//    public static List<ExtraPart> getAll(){
//        //extraParts(attachPoint, imagePath, imageWidth, imageHeight)";
//
//        ArrayList<ExtraPart> retval = new ArrayList<>();
//        Cursor cursor = sDatabase.query(sTableName,null,null,null,null,null,null);
//        try{
//            cursor.moveToFirst();
//            while(!cursor.isAfterLast()){
//                ExtraPart tempExtraPart = fillOutExtraPartInfo(cursor);
//                retval.add(tempExtraPart);
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
     * Returns an ExtraPart object specified by the <code>id</code>.
     * @param id int representing the ExtraPart wanted.
     * @return ExtraPart specified by the <code>id</code>.
     */
    public static ExtraPart getExtraPart(int id){
        ExtraPart retval;

        Cursor cursor = sDatabase.query(sTableName,
                null,
                "rowid = ?",
                new String[]{Integer.toString(id)},
                null, null, null);
        try{
            cursor.moveToFirst();
            retval = fillOutExtraPartInfo(cursor);
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
     * Clears all rows in the extraParts table.
     */
    public static void clearAll(){
        sDatabase.delete(sTableName, null, null);
    }

    /**
     * Adds an ExtraPart to the extraParts table.
     * @param extraPart ExtraPart representing the extra part to be added to the extraParts table.
     * @return true if successful, false if not.
     */
    public static boolean addExtraPart(ExtraPart extraPart){
        ContentValues values = new ContentValues();
        values.put("attachPoint", Spaceship.covertPointFtoString(extraPart.getAttachPoint()));
        values.put("imagePath", extraPart.getImagePath());
        values.put("imageWidth", extraPart.getImageWidth());
        values.put("imageHeight", extraPart.getImageHeight());

        long id = sDatabase.insert(sTableName, null, values);

        if(id >= 0) return true;
        else return false;
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private static ExtraPart fillOutExtraPartInfo(Cursor cursor) {
        ExtraPart retval = new ExtraPart();
        String attachPoint = cursor.getString(cursor.getColumnIndex("attachPoint"));
        retval.setAttachPoint(Spaceship.convertStringToPointF(attachPoint));
        retval.setImagePath(cursor.getString(cursor.getColumnIndex("imagePath")));
        retval.setImageWidth(cursor.getInt(cursor.getColumnIndex("imageWidth")));
        retval.setImageHeight(cursor.getInt(cursor.getColumnIndex("imageHeight")));
        return retval;
    }
//endregion PRIVATE METHODS
}
