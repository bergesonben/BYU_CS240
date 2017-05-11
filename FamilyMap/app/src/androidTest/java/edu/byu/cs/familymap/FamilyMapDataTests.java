package edu.byu.cs.familymap;

import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import edu.byu.cs.familymap.Model.Event;
import edu.byu.cs.familymap.Model.FamilyMapData;
import edu.byu.cs.familymap.Model.Person;

/**
 * Created by User on 8/1/2016.
 */
public class FamilyMapDataTests extends AndroidTestCase{

    public void testAddPeople() throws JSONException {
        String people = "[{\n" +
                "\t\t\t\"descendant\":\"a\",\n" +
                "\t\t\t\"personID\":\"407n695j-1n10-c8je-7ud8-8u5zyl8a533y\",\n" +
                "\t\t\t\"firstName\":\"Darron\",\n" +
                "\t\t\t\"lastName\":\"Wasinger\",\n" +
                "\t\t\t\"gender\":\"m\",\n" +
                "\t\t\t\"spouse\":\"n399nju5-5z1p-94d8-b21u-48n1c0ub27o6\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"descendant\":\"a\",\n" +
                "\t\t\t\"personID\":\"n399nju5-5z1p-94d8-b21u-48n1c0ub27o6\",\n" +
                "\t\t\t\"firstName\":\"Carlita\",\n" +
                "\t\t\t\"lastName\":\"Cardiel\",\n" +
                "\t\t\t\"gender\":\"f\",\n" +
                "\t\t\t\"spouse\":\"407n695j-1n10-c8je-7ud8-8u5zyl8a533y\"\n" +
                "\t\t}]";
        JSONArray array = new JSONArray(people);
        FamilyMapData.get().addPeople(array);

        Person firstPerson = FamilyMapData.get().getPerson("407n695j-1n10-c8je-7ud8-8u5zyl8a533y");
        assertNotNull(firstPerson);
        assertEquals(firstPerson.getDescendant(), "a");
        assertEquals(firstPerson.getFirstName(), "Darron");
        assertEquals(firstPerson.getLastName(), "Wasinger");
        assertEquals(firstPerson.getGender(), Person.Gender.MALE);
        assertEquals(firstPerson.getSpouseId(), "n399nju5-5z1p-94d8-b21u-48n1c0ub27o6");

        Person secondPerson = FamilyMapData.get().getPerson("n399nju5-5z1p-94d8-b21u-48n1c0ub27o6");
        assertNotNull(secondPerson);

        Person thirdPerson = FamilyMapData.get().getPerson("asfdasfvsa");
        assertNull(thirdPerson);
    }

    public void testAddEvents() throws JSONException{
        String events = "[{\n" +
                "\t\t\t\"eventID\":\"d858n491-ky2v-8v1m-377g-8bkpn488qg21\",\n" +
                "\t\t\t\"personID\":\"407n695j-1n10-c8je-7ud8-8u5zyl8a533y\",\n" +
                "\t\t\t\"latitude\":41.7167,\n" +
                "\t\t\t\"longitude\":44.7833,\n" +
                "\t\t\t\"country\":\"Georgia\",\n" +
                "\t\t\t\"city\":\"Tbilisi\",\n" +
                "\t\t\t\"description\":\"birth\",\n" +
                "\t\t\t\"year\":\"1685\",\n" +
                "\t\t\t\"descendant\":\"a\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"eventID\":\"tl1x6w21-3plt-086s-747f-79levp303ufh\",\n" +
                "\t\t\t\"personID\":\"407n695j-1n10-c8je-7ud8-8u5zyl8a533y\",\n" +
                "\t\t\t\"latitude\":31.2,\n" +
                "\t\t\t\"longitude\":29.9167,\n" +
                "\t\t\t\"country\":\"Egypt\",\n" +
                "\t\t\t\"city\":\"Alexandria\",\n" +
                "\t\t\t\"description\":\"death\",\n" +
                "\t\t\t\"year\":\"1749\",\n" +
                "\t\t\t\"descendant\":\"a\"\n" +
                "\t\t}]";
        JSONArray array = new JSONArray(events);
        FamilyMapData.get().addEvents(array);

        Event firstEvent = FamilyMapData.get().getEvent("d858n491-ky2v-8v1m-377g-8bkpn488qg21");
        assertNotNull(firstEvent);
        assertEquals(firstEvent.getPersonId(), "407n695j-1n10-c8je-7ud8-8u5zyl8a533y");
        assertEquals(firstEvent.getLatitude(), 41.7167);
        assertEquals(firstEvent.getLongitude(), 44.7833);
        assertEquals(firstEvent.getCountry(), "Georgia");
        assertEquals(firstEvent.getCity(), "Tbilisi");
        assertEquals(firstEvent.getDescription(), "birth");
        assertEquals(firstEvent.getYear(), 1685);
        assertEquals(firstEvent.getDescendant(), "a");

        Event secondEvent = FamilyMapData.get().getEvent("tl1x6w21-3plt-086s-747f-79levp303ufh");
        assertNotNull(secondEvent);

        Event thirdEvent = FamilyMapData.get().getEvent("asdba");
        assertNull(thirdEvent);
    }

    public void testEventOrder() throws JSONException {
        FamilyMapData data = FamilyMapData.get();
        String events = "[{eventID: \"1\", description: \"birth\", year: \"2001\", personID: \"abc\"}," +
                "{eventID: \"2\", description: \"baptism\", year: \"2002\", personID: \"abc\"}," +
                "{eventID: \"3\", description: \"christening\", year: \"2003\", personID: \"abc\"}," +
                "{eventID: \"4\", description: \"census\", year: \"2004\", personID: \"abc\"}," +
                "{eventID: \"5\", description: \"graduated college\", year: \"2005\", personID: \"abc\"}," +
                "{eventID: \"6\", description: \"death\", year: \"2006\", personID: \"abc\"}]";
        JSONArray array = new JSONArray(events);
        data.addEvents(array);

        Person person = new Person();
        person.setId("abc");

        Vector<Event> returnedEvents = data.getEventsByOrder(person);
        assertNotNull(returnedEvents);
        assertEquals(returnedEvents.elementAt(0).getDescription(), "birth");
        assertEquals(returnedEvents.elementAt(1).getDescription(), "baptism");
        assertEquals(returnedEvents.elementAt(2).getDescription(), "christening");
        assertEquals(returnedEvents.elementAt(3).getDescription(), "census");
        assertEquals(returnedEvents.elementAt(4).getDescription(), "graduated college");
        assertEquals(returnedEvents.elementAt(5).getDescription(), "death");
    }

}
