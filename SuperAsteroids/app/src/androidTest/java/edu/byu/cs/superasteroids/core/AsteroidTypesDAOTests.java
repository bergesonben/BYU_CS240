package edu.byu.cs.superasteroids.core;

import android.test.AndroidTestCase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.AsteroidTypesDAO;
import edu.byu.cs.superasteroids.models.Asteroid;

/**
 * Created by User on 7/23/2016.
 */
public class AsteroidTypesDAOTests extends AndroidTestCase {

    public void test() {
        AsteroidTypesDAO.clearAll();

        Asteroid FirstAsteroid = new Asteroid();
        FirstAsteroid.setAsteroidName("regular");
        FirstAsteroid.setImagePath("images/asteroids/asteroid.png");
        FirstAsteroid.setImageWidth(169);
        FirstAsteroid.setImageHeight(153);
        FirstAsteroid.setAsteroidType(Asteroid.ASTEROID_TYPE.REGULAR);
        AsteroidTypesDAO.addAsteroid(FirstAsteroid);

        Asteroid SecondAsteroid = new Asteroid();
        SecondAsteroid.setAsteroidName("growing");
        SecondAsteroid.setImagePath("images/asteroids/blueasteroid.png");
        SecondAsteroid.setImageWidth(161);
        SecondAsteroid.setImageHeight(178);
        SecondAsteroid.setAsteroidType(Asteroid.ASTEROID_TYPE.GROWING);
        AsteroidTypesDAO.addAsteroid(SecondAsteroid);

        Asteroid ThirdAsteroid = new Asteroid();
        ThirdAsteroid.setAsteroidName("octeroid");
        ThirdAsteroid.setImagePath("images/asteroids/asteroid.png");
        ThirdAsteroid.setImageWidth(169);
        ThirdAsteroid.setImageHeight(153);
        ThirdAsteroid.setAsteroidType(Asteroid.ASTEROID_TYPE.OCTEROID);
        AsteroidTypesDAO.addAsteroid(ThirdAsteroid);

        assertEquals(FirstAsteroid.equals(AsteroidTypesDAO.getAsteroid(1)), true);
        assertEquals(SecondAsteroid.equals(AsteroidTypesDAO.getAsteroid(2)), true);
        assertEquals(ThirdAsteroid.equals(AsteroidTypesDAO.getAsteroid(3)), true);


        ArrayList<String> images = new ArrayList<>();
        images.add("images/asteroids/asteroid.png");
        images.add("images/asteroids/blueasteroid.png");
        images.add("images/asteroids/asteroid.png");

        assertEquals(AsteroidTypesDAO.getImages(), images);
    }
}
