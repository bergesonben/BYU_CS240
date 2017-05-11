package edu.byu.cs.superasteroids.core;

import android.graphics.PointF;
import android.test.AndroidTestCase;

import java.util.ArrayList;

import edu.byu.cs.superasteroids.content.Sound;
import edu.byu.cs.superasteroids.database.CannonsDAO;
import edu.byu.cs.superasteroids.models.Cannon;
import edu.byu.cs.superasteroids.models.Missile;

/**
 * Created by User on 7/23/2016.
 */
public class CannonsDAOTests extends AndroidTestCase {

    public void test() {
        CannonsDAO.clearAll();

        Cannon FirstCannon = new Cannon();
        FirstCannon.setAttachPoint(new PointF(14,240));
        FirstCannon.setAttackEmitPoint(new PointF(104,36));
        FirstCannon.setImagePath("images/parts/cannon1.png");
        FirstCannon.setImageWidth(160);
        FirstCannon.setImageHeight(360);
        Missile FirstMissile = new Missile();
        FirstMissile.setImagePath("images/parts/laser.png");
        FirstMissile.setImageWidth(50);
        FirstMissile.setImageHeight(250);
        FirstCannon.setMissile(FirstMissile);
        FirstCannon.setAttackSound(new Sound(1,"sounds/laser.mp3"));
        FirstCannon.setAttackDamage(1);
        CannonsDAO.addCannon(FirstCannon);

        Cannon SecondCannon = new Cannon();
        SecondCannon.setAttachPoint(new PointF(19,137));
        SecondCannon.setAttackEmitPoint(new PointF(184,21));
        SecondCannon.setImagePath("images/parts/cannon2.png");
        SecondCannon.setImageWidth(325);
        SecondCannon.setImageHeight(386);
        Missile SecondMissile = new Missile();
        SecondMissile.setImagePath("images/parts/laser2.png");
        SecondMissile.setImageWidth(105);
        SecondMissile.setImageHeight(344);
        SecondCannon.setMissile(SecondMissile);
        SecondCannon.setAttackSound(new Sound(1,"sounds/laser.mp3"));
        SecondCannon.setAttackDamage(2);
        CannonsDAO.addCannon(SecondCannon);

        assertEquals(FirstCannon.equals(CannonsDAO.getCannon(1)), true);
        assertEquals(SecondCannon.equals(CannonsDAO.getCannon(2)), true);

        ArrayList<String> images = new ArrayList<>();
        images.add("images/parts/cannon1.png");
        images.add("images/parts/cannon2.png");

        assertEquals(images, CannonsDAO.getImages());
    }
}
