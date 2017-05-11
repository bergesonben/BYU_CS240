package edu.byu.cs.superasteroids.core;

import android.test.AndroidTestCase;

import org.json.JSONException;
import org.json.JSONObject;

import edu.byu.cs.superasteroids.database.LevelsDAO;
import edu.byu.cs.superasteroids.models.Level;

/**
 * Created by User on 7/23/2016.
 */
public class LevelsDAOTests extends AndroidTestCase {

    public void test() {
        LevelsDAO.clearAll();

        String levelString = "\"number\":1,\n" +
                "\t\t\t\t\"title\":\"Level 1\",\n" +
                "\t\t\t\t\"hint\":\"Destroy 1 Asteroid\",\n" +
                "\t\t\t\t\"width\":3000,\n" +
                "\t\t\t\t\"height\":3000,\n" +
                "\t\t\t\t\"music\":\"sounds/SpyHunter.ogg\",\n" +
                "\t\t\t\t\"levelObjects\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"position\":\"1000,1000\",\n" +
                "\t\t\t\t\t\t\"objectId\":1,\n" +
                "\t\t\t\t\t\t\"scale\":1.5\n" +
                "\t\t\t\t\t}\n" +
                "\n" +
                "\t\t\t\t],\n" +
                "\t\t\t\t\"levelAsteroids\": [\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"number\":4,\n" +
                "\t\t\t\t\t\t\"asteroidId\":1\n" +
                "\t\t\t\t\t},\n" +
                "\t\t\t\t\t{\n" +
                "\t\t\t\t\t\t\"number\":4,\n" +
                "\t\t\t\t\t\t\"asteroidId\":2\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t]";
        try {
            JSONObject levelJSONObject = new JSONObject(levelString);
            Level level = new Level(levelJSONObject);
            LevelsDAO.addLevel(level);
            //levels(number, title, hint, width, height, musicPath)";
            Level returnedLevel = LevelsDAO.getLevel(1);
            assertEquals(1, returnedLevel.getLevelValue());
            assertEquals("Level 1", returnedLevel.getLevelTitle());
            assertEquals("Destroy 1 Asteroid", returnedLevel.getLevelHint());
            assertEquals(3000, returnedLevel.getLevelWidth());
            assertEquals(3000, returnedLevel.getLevelHeight());
            assertEquals("sounds/SpyHunter.ogg", returnedLevel.getLevelMusic().filePath);
        } catch(JSONException json) {
            json.printStackTrace();
        }
    }
}
