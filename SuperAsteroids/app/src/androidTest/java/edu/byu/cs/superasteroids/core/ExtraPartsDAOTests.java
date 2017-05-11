package edu.byu.cs.superasteroids.core;

import android.graphics.PointF;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.EnginesDAO;
import edu.byu.cs.superasteroids.database.ExtraPartsDAO;
import edu.byu.cs.superasteroids.models.ExtraPart;

/**
 * Created by User on 7/23/2016.
 */
public class ExtraPartsDAOTests extends AndroidTestCase {

    public void test() {
        ExtraPartsDAO.clearAll();

        ExtraPart first = new ExtraPart();
        first.setAttachPoint(new PointF(1, 2));
        first.setImagePath("first");
        first.setImageWidth(10);
        first.setImageHeight(20);
        ExtraPartsDAO.addExtraPart(first);

        ExtraPart second = new ExtraPart();
        second.setAttachPoint(new PointF(3, 4));
        second.setImagePath("second");
        second.setImageWidth(20);
        second.setImageHeight(30);
        ExtraPartsDAO.addExtraPart(second);

        assertEquals(true, first.equals(ExtraPartsDAO.getExtraPart(1)));
        assertEquals(true, second.equals(ExtraPartsDAO.getExtraPart(2)));

        ArrayList<String> images = new ArrayList<>();
        images.add("first");
        images.add("second");

        assertEquals(images, ExtraPartsDAO.getImages());
    }
}
