package edu.byu.cs.familymap.Model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 * Created by User on 8/1/2016.
 */
public class FamilyMapData {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN

    private Map<String, Person> mPeople;
    private Map<String, Event> mEvents;
    private Map<String, Set<Event>> mEventsByPerson;
    private static FamilyMapData instance;

//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS

    private FamilyMapData() {
        mPeople = new HashMap<>();
        mEvents = new HashMap<>();
        mEventsByPerson = new HashMap<>();
    }

//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES

    public static FamilyMapData get() {
        if (instance == null) {
            instance = new FamilyMapData();
        }

        return instance;
    }

    public Person getPerson(String id) {
        return mPeople.get(id);
    }

    public Event getEvent(String id) {
        return mEvents.get(id);
    }

    public Map<String, Event> getEvents() {
        return mEvents;
    }

    public Event getSpouseLineEvent(Person person) {
        Person spouse = getPerson(person.getSpouseId());
        if (spouse != null) {
            return getEarliestEvent(spouse);
        }
        return null;
    }

    public Event getFatherLineEvent(Person person) {
        Person father = getPerson(person.getFatherId());
        if (father != null) {
            return getEarliestEvent(father);
        }
        return null;
    }

    public Event getMotherLineEvent(Person person) {
        Person mother = getPerson(person.getMotherId());
        if (mother != null) {
            return getEarliestEvent(mother);
        }
        return null;
    }

    public Vector<Event> getEventsByOrder(Person person) {
        if (person == null) return null;

        Vector<Event> retval = new Vector<>();
        Set<Event> events = new HashSet<>(mEventsByPerson.get(person.getId()));
        if (events == null) return null;

        while(!events.isEmpty()) {
            Event earliestEvent = new Event();
            for(Event event : events) {
                if (event.getDescription() == "birth") {
                    earliestEvent = event;
                    break;
                }
                if (event.isEarlierThan(earliestEvent)) {
                    earliestEvent = event;
                }
            }
            retval.add(earliestEvent);
            events.remove(earliestEvent);
        }

        return retval;
    }

    public Vector<Person> getFamily(Person person) {
        Vector<Person> family = new Vector<>();
        Person father = getPerson(person.getFatherId());
        if (father != null) {
            father.setRelationship("Father");
            family.add(father);
        }
        Person mother = getPerson(person.getMotherId());
        if (mother != null) {
            mother.setRelationship("Mother");
            family.add(mother);
        }
        Person spouse = getPerson(person.getSpouseId());
        if (spouse != null) {
            spouse.setRelationship("Spouse");
            family.add(spouse);
        }

        Set<String> chlidren = person.getChildrenIds();
        for(String childId : chlidren) {
            Person child = getPerson(childId);
            if (child != null) {
                child.setRelationship("Child");
                family.add(child);
            }
        }
        return family;
    }

    public List<Object> searchFor(String s) {
        List<Object> searchResult = new Vector<>();

        //search people first
        for(String personId : mPeople.keySet()) {
            Person person = mPeople.get(personId);
            if (person.contains(s))
                searchResult.add(person);
        }

        //search through events
        for(String eventId : mEvents.keySet()) {
            Event event = mEvents.get(eventId);
            if (event.contains(s))
                searchResult.add(event);
        }
        return searchResult;
    }

//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS

    public void addPeople(JSONArray jsonPeople) {
        Gson gson = new Gson();
        try {
            for(int i = 0; i < jsonPeople.length(); i++) {
                Person tempPerson = gson.fromJson(jsonPeople.getString(i), Person.class);
                mPeople.put(tempPerson.getId(), tempPerson);
            }
        } catch(JSONException json) {
            json.printStackTrace();
        }

        for(String key : mPeople.keySet()) {
            Person person = mPeople.get(key);
            Person father = getPerson(person.getFatherId());
            if (father != null)
                father.addChild(key);
            Person mother = getPerson(person.getMotherId());
            if (mother != null)
                mother.addChild(key);
        }

        Person user = getPerson(User.get().getPersonId());
        Person father = getPerson(user.getFatherId());
        setFathersSide(father, true);
        Person mother = getPerson(user.getMotherId());
        setFathersSide(mother, false);
    }

    public void addEvents(JSONArray jsonEvents) {
        Gson gson = new Gson();
        try{
            for(int i = 0; i < jsonEvents.length(); i++) {
                Event tempEvent = gson.fromJson(jsonEvents.getString(i), Event.class);
                mEvents.put(tempEvent.getEventId(), tempEvent);
            }
        } catch(JSONException json) {
            json.printStackTrace();
        }

        for(String key : mEvents.keySet()) {
            Event event = mEvents.get(key);
            if (mEventsByPerson.containsKey(event.getPersonId())) {
                Set<Event> events = mEventsByPerson.get(event.getPersonId());
                events.add(event);
            } else {
                Set<Event> events = new HashSet<>();
                events.add(event);
                mEventsByPerson.put(event.getPersonId(), events);
            }
        }
    }

    public void clearAll() {
        mPeople.clear();
        mEvents.clear();
        mEventsByPerson.clear();
    }

//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS

    private Event getEarliestEvent(Person person) {
        Set<Event> events = mEventsByPerson.get(person.getId());
        if (events != null) {
            Event earliestEvent = new Event();
            for(Event event : events) {
                if (event.getDescription() == "birth") {
                    return event;
                }
                if (event.isEarlierThan(earliestEvent)) {
                    earliestEvent = event;
                }
            }
            return earliestEvent;
        }
        return null;
    }

    private void setFathersSide(Person person, boolean onFathersSide) {
        if (person != null) {
            person.setOnFathersSide(onFathersSide);
        }
        Person father = getPerson(person.getFatherId());
        if (father != null) {
            setFathersSide(father, onFathersSide);
        }
        Person mother = getPerson(person.getMotherId());
        if (mother != null) {
            setFathersSide(mother, onFathersSide);
        }
    }

//endregion PRIVATE METHODS
}
