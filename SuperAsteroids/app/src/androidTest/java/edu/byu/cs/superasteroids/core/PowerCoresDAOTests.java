package edu.byu.cs.superasteroids.core;

import android.test.AndroidTestCase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.database.PowerCoresDAO;
import edu.byu.cs.superasteroids.models.PowerCore;

/**
 * Created by User on 7/23/2016.
 */
public class PowerCoresDAOTests extends AndroidTestCase{

    public void test() {
        PowerCoresDAO.clearAll();

        PowerCore first = new PowerCore();
        first.setCannonBoost(1);
        first.setEngineBoost(2);
        first.setPowerCoreImagePath("first");
        PowerCoresDAO.addPowerCore(first);

        PowerCore second = new PowerCore();
        second.setCannonBoost(3);
        second.setEngineBoost(4);
        second.setPowerCoreImagePath("second");
        PowerCoresDAO.addPowerCore(second);

        assertEquals(true, first.equals(PowerCoresDAO.getPowerCore(1)));
        assertEquals(true, second.equals(PowerCoresDAO.getPowerCore(2)));

        ArrayList<String> images = new ArrayList<>();
        images.add("first");
        images.add("second");

        assertEquals(images, PowerCoresDAO.getImages());
    }
}
