package edu.byu.cs.superasteroids.core;

import android.graphics.PointF;
import android.test.AndroidTestCase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.MainBodiesDAO;
import edu.byu.cs.superasteroids.models.MainBody;

/**
 * Created by User on 7/23/2016.
 */
public class MainBodiesDAOTests extends AndroidTestCase {

    public void test() {
        MainBodiesDAO.clearAll();

        MainBody first = new MainBody();
        first.setCannonAttachPoint(new PointF(1,1));
        first.setEngineAttachPoint(new PointF(2,2));
        first.setExtraPartAttachPoint(new PointF(3,3));
        first.setImagePath("first");
        first.setImageWidth(100);
        first.setImageHeight(200);
        MainBodiesDAO.addMainBody(first);

        MainBody second = new MainBody();
        second.setCannonAttachPoint(new PointF(4,4));
        second.setEngineAttachPoint(new PointF(5,5));
        second.setExtraPartAttachPoint(new PointF(6,6));
        second.setImagePath("second");
        second.setImageWidth(300);
        second.setImageHeight(400);
        MainBodiesDAO.addMainBody(second);

        assertEquals(true, first.equals(MainBodiesDAO.getMainBody(1)));
        assertEquals(true, second.equals(MainBodiesDAO.getMainBody(2)));

        ArrayList<String> images = new ArrayList<>();
        images.add("first");
        images.add("second");

        assertEquals(images, MainBodiesDAO.getImages());
    }
}
