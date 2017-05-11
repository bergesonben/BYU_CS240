package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.models.MainBody;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by bergeson on 7/13/2016.
 */
public class MainBodiesDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static MainBodiesDAO instance;
    private static String sTableName = "mainBodies";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private MainBodiesDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of an MainBodiesDAO.
     * @return MainBodiesDAO
     */
    public static MainBodiesDAO getInstance(){
        if(instance == null){
            instance = new MainBodiesDAO();
        }
        return instance;
    }

    /**
     * Returns a list of all MainBody image paths.
     * @return List of all MainBody image paths.
     */
    public static List<String> getImages() {
        List<String> retval = new ArrayList<>();
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
//     * Returns a list of all MainBody objects in the main bodies table.
//     * @return List of all main bodies in the main bodies table.
//     */
//    public static List<MainBody> getAll(){
//        //mainBodies(cannonAttach, engineAttach, extraAttach, imagePath, imageWidth, imageHeight)";
//
//        ArrayList<MainBody> retval = new ArrayList<>();
//        Cursor cursor = sDatabase.query(sTableName,null,null,null,null,null,null);
//        try{
//            cursor.moveToFirst();
//            while(!cursor.isAfterLast()){
//                MainBody tempMainBody;
//                tempMainBody = fillOutMainBodyInfo(cursor);
//                retval.add(tempMainBody);
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
     * Returns an MainBody object specified by the <code>id</code>.
     * @param id int representing the MainBody wanted.
     * @return MainBody specified by the <code>id</code>.
     */
    public static MainBody getMainBody(int id){
        MainBody retval;

        Cursor cursor = sDatabase.query(sTableName,
                null,
                "rowid = ?",
                new String[]{Integer.toString(id)},
                null, null, null);
        try{
            cursor.moveToFirst();
            retval = fillOutMainBodyInfo(cursor);
        }
        finally{
            cursor.close();
        }
        return retval;
    }

    public static int getCount() {
        return (int)DatabaseUtils.queryNumEntries(sDatabase, sTableName);
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Clears all rows in the mainBodies table.
     */
    public static void clearAll(){
        sDatabase.delete(sTableName, null, null);
    }

    /**
     * Adds an MainBody to the mainBodies table.
     * @param mainBody MainBody representing the main body to be added to the mainBodies table.
     */
    public static boolean addMainBody(MainBody mainBody){
        ContentValues values = new ContentValues();
        values.put("cannonAttach", Spaceship.covertPointFtoString(mainBody.getCannonAttachPoint()));
        values.put("engineAttach", Spaceship.covertPointFtoString(mainBody.getEngineAttachPoint()));
        values.put("extraAttach", Spaceship.covertPointFtoString(mainBody.getExtraPartAttachPoint()));
        values.put("imagePath", mainBody.getImagePath());
        values.put("imageWidth", mainBody.getImageWidth());
        values.put("imageHeight", mainBody.getImageHeight());

        long id = sDatabase.insert(sTableName, null, values);

        if(id >= 0) return true;
        else return false;
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private static MainBody fillOutMainBodyInfo(Cursor cursor) {
        MainBody retval = new MainBody();
        String cannonAttachPoint = cursor.getString(cursor.getColumnIndex("cannonAttach"));
        retval.setCannonAttachPoint(Spaceship.convertStringToPointF(cannonAttachPoint));
        String engineAttachPoint = cursor.getString(cursor.getColumnIndex("engineAttach"));
        retval.setEngineAttachPoint(Spaceship.convertStringToPointF(engineAttachPoint));
        String extraAttachPoint = cursor.getString(cursor.getColumnIndex("extraAttach"));
        retval.setExtraPartAttachPoint(Spaceship.convertStringToPointF(extraAttachPoint));
        retval.setImagePath(cursor.getString(cursor.getColumnIndex("imagePath")));
        retval.setImageWidth(cursor.getInt(cursor.getColumnIndex("imageWidth")));
        retval.setImageHeight(cursor.getInt(cursor.getColumnIndex("imageHeight")));
        return retval;
    }
//endregion PRIVATE METHODS
}
