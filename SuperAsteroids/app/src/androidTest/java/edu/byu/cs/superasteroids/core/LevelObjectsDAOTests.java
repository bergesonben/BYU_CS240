package edu.byu.cs.superasteroids.core;

import android.test.AndroidTestCase;

import edu.byu.cs.superasteroids.database.LevelObjectsDAO;

/**
 * Created by User on 7/23/2016.
 */
public class LevelObjectsDAOTests extends AndroidTestCase{

    public void test() {
        LevelObjectsDAO.clearAll();

        LevelObjectsDAO.addLevelObject("1,1", 1, 2.0, 1);
        LevelObjectsDAO.addLevelObject("2,2", 1, 2.0, 1);

        assertEquals(2, LevelObjectsDAO.getAllForLevel(1).size());
    }
}
