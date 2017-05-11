package edu.byu.cs.superasteroids.core;

import android.test.AndroidTestCase;

import edu.byu.cs.superasteroids.database.LevelAsteroidsDAO;
import edu.byu.cs.superasteroids.models.Asteroid;

/**
 * Created by User on 7/23/2016.
 */
public class LevelAsteroidsDAOTests extends AndroidTestCase{

    public void test() {
        LevelAsteroidsDAO.clearAll();

        LevelAsteroidsDAO.addAsteroid(1,1,1);
        LevelAsteroidsDAO.addAsteroid(2,2,1);
        LevelAsteroidsDAO.addAsteroid(4,3,2);

        assertEquals(3, LevelAsteroidsDAO.getAllForLevel(1).size());
        assertEquals(4, LevelAsteroidsDAO.getAllForLevel(2).size());
    }
}
