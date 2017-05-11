package edu.byu.cs.superasteroids.core;

import android.test.AndroidTestCase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.ObjectsDAO;
import edu.byu.cs.superasteroids.models.BackgroundObject;

/**
 * Created by User on 7/23/2016.
 */
public class ObjectsDAOTests extends AndroidTestCase{

    public void test() {
        ObjectsDAO.clearAll();

        BackgroundObject first = new BackgroundObject("first");
        ObjectsDAO.addObject(first);

        BackgroundObject second = new BackgroundObject("second");
        ObjectsDAO.addObject(second);

        assertEquals(true, first.equals(ObjectsDAO.getObject(1)));
        assertEquals(true, second.equals(ObjectsDAO.getObject(2)));

        ArrayList<String> images = new ArrayList<>();
        images.add("first");
        images.add("second");

        assertEquals(ObjectsDAO.getImages(), images);
    }
}
