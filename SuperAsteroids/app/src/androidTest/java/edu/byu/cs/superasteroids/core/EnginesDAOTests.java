package edu.byu.cs.superasteroids.core;

import android.graphics.PointF;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.EnginesDAO;
import edu.byu.cs.superasteroids.models.Engine;

/**
 * Created by User on 7/23/2016.
 */
public class EnginesDAOTests extends AndroidTestCase {

    public void test() {
        EnginesDAO.clearAll();

        Engine firstEngine = new Engine();
        firstEngine.setEngineSpeed(350);
        firstEngine.setEngineTurnRate(270);
        firstEngine.setAttachPoint(new PointF(106,6));
        firstEngine.setImagePath("images/parts/engine1.png");
        firstEngine.setImageWidth(220);
        firstEngine.setImageHeight(160);
        EnginesDAO.addEngine(firstEngine);

        Engine secondEngine = new Engine();
        secondEngine.setEngineSpeed(500);
        secondEngine.setEngineTurnRate(360);
        secondEngine.setAttachPoint(new PointF(107,7));
        secondEngine.setImagePath("images/parts/engine2.png");
        secondEngine.setImageWidth(208);
        secondEngine.setImageHeight(222);
        EnginesDAO.addEngine(secondEngine);

        assertEquals(true, firstEngine.equals(EnginesDAO.getEngine(1)));
        assertEquals(true, secondEngine.equals(EnginesDAO.getEngine(2)));

        ArrayList<String> images = new ArrayList<>();
        images.add("images/parts/engine1.png");
        images.add("images/parts/engine2.png");

        assertEquals(images, EnginesDAO.getImages());
    }
}
