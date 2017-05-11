package edu.byu.cs.familymap;

import android.test.AndroidTestCase;

import edu.byu.cs.familymap.Model.Event;
import edu.byu.cs.familymap.Model.FamilyMapData;
import edu.byu.cs.familymap.Model.Person;
import edu.byu.cs.familymap.Model.Server;
import edu.byu.cs.familymap.Model.User;

import static junit.framework.Assert.*;

/**
 * Created by User on 8/1/2016.
 */
public class LoginTests extends AndroidTestCase{

    public void testLogin() {
        User user = User.get();
        user.setUsername("a");
        user.setPassword("a");
        Server.get().setServerHost("10.2.8.236");
        Server.get().setServerPort(8080);
        assertTrue(new FamilyMapConnector().getLogin());
        assertTrue(new FamilyMapConnector().syncData());

        FamilyMapData data = FamilyMapData.get();
        Event event = data.getEvent("1bl10r0v-ej8d-11v7-i028-q6vb7w5t8wd4");
        assertNotNull(event);
        assertEquals(event.getPersonId(), "n399nju5-5z1p-94d8-b21u-48n1c0ub27o6");
        assertEquals(event.getLatitude(), -15.5);
        assertEquals(event.getLongitude(), -180.0);
        assertEquals(event.getCountry(), "Fiji");
        assertEquals(event.getCity(), "Rabi Island");
        assertEquals(event.getDescription(), Event.EventType.CENSUS);
        assertEquals(event.getYear(), 1720);
        assertEquals(event.getDescendant(), "a");

        /*
        "descendant":"a",
			"personID":"581011v0-t550-5s5q-x010-1jd2vo345021",
			"firstName":"Theodore",
			"lastName":"Mcnulty",
			"gender":"m",
			"father":"px797nfk-h0l5-2qiv-iw92-41kve5m2gv79",
			"mother":"81fc0xxl-4506-r399-168d-sau9h17ba20m",
			"spouse":"3vt9bf25-67l2-0h0p-5it1-7303ii3hjlde"
         */
        Person person = data.getPerson("581011v0-t550-5s5q-x010-1jd2vo345021");
        assertNotNull(person);
        assertEquals(person.getDescendant(), "a");
        assertEquals(person.getFirstName(), "Theodore");
        assertEquals(person.getLastName(), "Mcnulty");
        assertEquals(person.getGender(), Person.Gender.MALE);
        assertEquals(person.getFatherId(), "px797nfk-h0l5-2qiv-iw92-41kve5m2gv79");
        assertEquals(person.getMotherId(), "81fc0xxl-4506-r399-168d-sau9h17ba20m");
        assertEquals(person.getSpouseId(), "3vt9bf25-67l2-0h0p-5it1-7303ii3hjlde");
    }
}
