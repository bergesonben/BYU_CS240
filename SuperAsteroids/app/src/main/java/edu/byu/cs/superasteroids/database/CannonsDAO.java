package edu.byu.cs.superasteroids.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.superasteroids.content.Sound;
import edu.byu.cs.superasteroids.models.Cannon;
import edu.byu.cs.superasteroids.models.Missile;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by bergeson on 7/13/2016.
 */
public class CannonsDAO {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static SQLiteDatabase sDatabase = SQLiteDatabase.openDatabase("/data/data/edu.byu.cs.superasteroids/databases/asteroids.sqlite",null,0);
    private static CannonsDAO instance;
    private static String sTableName = "cannons";
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    private CannonsDAO(){}
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of an CannonsDAO.
     * @return CannonsDAO
     */
    public static CannonsDAO getInstance(){
        if(instance == null){
            instance = new CannonsDAO();
        }
        return instance;
    }

    /**
     * Returns a list of all Cannon image paths.
     * @return List of all Cannon image paths.
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
//     * Returns a list of all Cannon objects in the cannons table.
//     * @return List of all cannons in the cannons table.
//     */
//    public static List<Cannon> getAll(){
//        //cannons(attachPoint, emitPoint, imagePath, imageWidth, imageHeight, attackImagePath, attackImageWidth, attackImageHeight, attackSoundPath, damage)";
//
//        ArrayList<Cannon> retval = new ArrayList<>();
//        Cursor cursor = sDatabase.query(sTableName,null,null,null,null,null,null);
//        try{
//            cursor.moveToFirst();
//            while(!cursor.isAfterLast()){
//                Cannon tempCannon = fillOutCannonInfo(cursor);
//                retval.add(tempCannon);
//                cursor.moveToNext();
//            }
//        }
//        finally{
//            cursor.close();
//        }
//  TODO: remove
//        return retval;
//    }

    /**
     * Returns a Cannon object specified by the <code>id</code>.
     * @param id int representing the Cannon wanted.
     * @return Cannon specified by the <code>id</code>.
     */
    public static Cannon getCannon(int id){
        Cannon retval;

        Cursor cursor = sDatabase.query(sTableName,
                null,
                "rowid = ?",
                new String[]{Integer.toString(id)},
                null, null, null);
        try{
            cursor.moveToFirst();
            retval = fillOutCannonInfo(cursor);
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
     * Clears all rows in the cannons table.
     */
    public static void clearAll(){
        sDatabase.delete(sTableName, null, null);
    }

    /**
     * Adds an Cannon to the cannons table.
     * @param cannon Cannon representing the cannon to be added to the cannons table.
     */
    public static boolean addCannon(Cannon cannon){
        //cannons(attachPoint,  attackImageWidth, attackImageHeight, attackSoundPath, damage)";
        ContentValues values = new ContentValues();
        values.put("attachPoint", Spaceship.covertPointFtoString(cannon.getAttachPoint()));
        values.put("emitPoint", Spaceship.covertPointFtoString(cannon.getAttackEmitPoint()));
        values.put("imagePath", cannon.getImagePath());
        values.put("imageWidth", cannon.getImageWidth());
        values.put("imageHeight", cannon.getImageHeight());
        Missile missile = cannon.getMissile();
        values.put("attackImagePath", missile.getImagePath());
        values.put("attackImageWidth", missile.getImageWidth());
        values.put("attackImageHeight", missile.getImageHeight());
        values.put("attackSoundPath", cannon.getAttackSound().filePath);
        values.put("damage", cannon.getAttackDamage());

        long id = sDatabase.insert(sTableName, null, values);

        if(id >= 0) return true;
        else return false;

    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private static Cannon fillOutCannonInfo(Cursor cursor) {
        Cannon tempCannon = new Cannon();
        String attachPoint = cursor.getString(cursor.getColumnIndex("attachPoint"));
        tempCannon.setAttachPoint(Spaceship.convertStringToPointF(attachPoint));
        String emitPoint = cursor.getString(cursor.getColumnIndex("emitPoint"));
        tempCannon.setAttackEmitPoint(Spaceship.convertStringToPointF(emitPoint));
        tempCannon.setImagePath(cursor.getString(cursor.getColumnIndex("imagePath")));
        tempCannon.setImageWidth(cursor.getInt(cursor.getColumnIndex("imageWidth")));
        tempCannon.setImageHeight(cursor.getInt(cursor.getColumnIndex("imageHeight")));

        Missile tempMissile = new Missile();
        tempMissile.setImagePath(cursor.getString(cursor.getColumnIndex("attackImagePath")));
        tempMissile.setImageWidth(cursor.getInt(cursor.getColumnIndex("attackImageWidth")));
        tempMissile.setImageHeight(cursor.getInt(cursor.getColumnIndex("attackImageHeight")));

        tempCannon.setMissile(tempMissile);
        Sound tempSound = new Sound(-1, cursor.getString(cursor.getColumnIndex("attackSoundPath")));
        tempCannon.setAttackSound(tempSound);
        tempCannon.setAttackDamage(cursor.getInt(cursor.getColumnIndex("damage")));
        return tempCannon;
    }
//endregion PRIVATE METHODS
}
