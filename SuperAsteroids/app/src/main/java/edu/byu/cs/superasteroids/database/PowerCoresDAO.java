package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.models.PowerCore;

/**
 * Created by bergeson on 7/13/2016.
 */
public class PowerCoresDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static PowerCoresDAO instance;
    private static String sTableName = "powerCores";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private PowerCoresDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns a singleton instance of the PowerCoresDAO.
     * @return an instance of a PowerCoresDAO.
     */
    public static PowerCoresDAO getInstance(){
        if(instance == null){
            instance = new PowerCoresDAO();
        }
        return instance;
    }

    /**
     * Returns a list of PowerCore image paths.
     * @return List of all PowerCore image paths.
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
//     * Returns a list of all power cores from the database.
//     * @return list of all power cores from the database.
//     */
//    public static List<PowerCore> getAll(){
//        //powerCores(cannonBoost, engineBoost, imagePath )";
//
//        ArrayList<PowerCore> retval = new ArrayList<>();
//        Cursor cursor = sDatabase.query(sTableName,null,null,null,null,null,null);
//        try{
//            cursor.moveToFirst();
//            while(!cursor.isAfterLast()){
//                PowerCore tempPowerCore = fillOutPowerCoreInfo(cursor);
//                retval.add(tempPowerCore);
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
     * Returns a power core based on the id provided.
     * @param id representing the power core to be returned.
     * @return PowerCore specified by the <code>id</code>
     */
    public static PowerCore getPowerCore(int id){
        PowerCore retval;

        Cursor cursor = sDatabase.query(sTableName,
                null,
                "rowid = ?",
                new String[]{Integer.toString(id)},
                null, null, null);
        try{
            cursor.moveToFirst();
            retval = fillOutPowerCoreInfo(cursor);
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
     * Clears all the power cores in the powerCores table.
     */
    public static void clearAll(){
        sDatabase.delete(sTableName, null, null);
    }

    /**
     * Adds a power core to the powerCores table.
     * @param powerCore PowerCore to be added to the table.
     * @return true if successful, false if not.
     */
    public static boolean addPowerCore(PowerCore powerCore){
        // powerCores(cannonBoost, engineBoost, imagePath )";
        ContentValues values = new ContentValues();
        values.put("cannonBoost", powerCore.getCannonBoost());
        values.put("engineBoost", powerCore.getEngineBoost());
        values.put("imagePath", powerCore.getPowerCoreImagePath());

        long id = sDatabase.insert(sTableName, null, values);

        if(id >= 0) return true;
        else return false;
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private static PowerCore fillOutPowerCoreInfo(Cursor cursor) {
        PowerCore tempPowerCore = new PowerCore();
        tempPowerCore.setCannonBoost(cursor.getInt(cursor.getColumnIndex("cannonBoost")));
        tempPowerCore.setEngineBoost(cursor.getInt(cursor.getColumnIndex("engineBoost")));
        tempPowerCore.setPowerCoreImagePath(cursor.getString(cursor.getColumnIndex("imagePath")));
        return  tempPowerCore;
    }
//endregion PRIVATE METHODS
}
