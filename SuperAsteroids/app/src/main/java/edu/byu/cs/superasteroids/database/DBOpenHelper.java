package edu.byu.cs.superasteroids.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bergeson on 7/13/2016.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static final String DB_NAME = "asteroids.sqlite";
    private static final int DB_VERSION = 1;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Default constructor. Creates a database.
     * @param context
     */
    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Creates all the necessary tables in the database.
     * @param db SQLiteDatabase where all the tables will be created in.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String dropObjectsTable = "DROP TABLE IF EXISTS objects";
        final String dropAsteroidTypesTable = "DROP TABLE IF EXISTS asteroidTypes";
        final String dropLevelsTable = "DROP TABLE IF EXISTS levels";
        final String dropLevelObjectsTable = "DROP TABLE IF EXISTS levelObjects";
        final String dropLevelAsteroidsTable = "DROP TABLE IF EXISTS levelAsteroids";
        final String dropMainBodiesTable = "DROP TABLE IF EXISTS mainBodies";
        final String dropCannonsTable = "DROP TABLE IF EXISTS cannons";
        final String dropExtraPartsTable = "DROP TABLE IF EXISTS extraParts";
        final String dropEnginesTable = "DROP TABLE IF EXISTS engines";
        final String dropPowerCoresTable = "DROP TABLE IF EXISTS powerCores";

        final String createObjectsTable = "CREATE TABLE objects(objPath)";
        final String createAsteroidTypesTable = "CREATE TABLE asteroidTypes(name,imagePath,imageWidth,imageHeight,type)";
        final String createLevelsTable = "CREATE TABLE levels(number, title, hint, width, height, musicPath)";
        final String createLevelObjectsTable = "CREATE TABLE levelObjects(position, objectId, scale, level)";
        final String createLevelAsteroidsTable = "CREATE TABLE levelAsteroids(count, asteroidId, level)";
        final String createMainBodiesTable = "CREATE TABLE mainBodies(cannonAttach, engineAttach, extraAttach, imagePath, imageWidth, imageHeight)";
        final String createCannonsTable = "CREATE TABLE cannons(attachPoint, emitPoint, imagePath, imageWidth, imageHeight, attackImagePath, attackImageWidth, attackImageHeight, attackSoundPath, damage)";
        final String createExtraPartsTable = "CREATE TABLE extraParts(attachPoint, imagePath, imageWidth, imageHeight)";
        final String createEnginesTable = "CREATE TABLE engines(baseSpeed, baseTurnRate, attachPoint, imagePath, imageWidth, imageHeight)";
        final String createPowerCoresTable = "CREATE TABLE powerCores(cannonBoost, engineBoost, imagePath )";

        db.execSQL(dropObjectsTable);
        db.execSQL(dropAsteroidTypesTable);
        db.execSQL(dropLevelsTable);
        db.execSQL(dropLevelObjectsTable);
        db.execSQL(dropLevelAsteroidsTable);
        db.execSQL(dropMainBodiesTable);
        db.execSQL(dropCannonsTable);
        db.execSQL(dropExtraPartsTable);
        db.execSQL(dropEnginesTable);
        db.execSQL(dropPowerCoresTable);

        db.execSQL(createObjectsTable);
        db.execSQL(createAsteroidTypesTable);
        db.execSQL(createLevelsTable);
        db.execSQL(createLevelObjectsTable);
        db.execSQL(createLevelAsteroidsTable);
        db.execSQL(createMainBodiesTable);
        db.execSQL(createCannonsTable);
        db.execSQL(createExtraPartsTable);
        db.execSQL(createEnginesTable);
        db.execSQL(createPowerCoresTable);
    }

    /**
     * Does nothing.
     * @param db not used.
     * @param oldVersion not used.
     * @param newVersion not used.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }
//endregion COMMANDS
}
