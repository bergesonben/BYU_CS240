package edu.byu.cs.superasteroids.game;

import java.io.IOException;

import edu.byu.cs.superasteroids.base.IGameDelegate;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.content.Sound;
import edu.byu.cs.superasteroids.database.AsteroidTypesDAO;
import edu.byu.cs.superasteroids.database.ObjectsDAO;
import edu.byu.cs.superasteroids.models.LevelController;
import edu.byu.cs.superasteroids.models.Missile;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by bergeson on 7/18/2016.
 */
public class GameController implements IGameDelegate {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private LevelController mLevelController;
    public static int mBackgroundId;
    public static int mBackgroundSoundId;
    public static int mImpactSoundId;
    public static final double ELAPSED_TIME = 0.01667;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    public GameController() {
        mLevelController = LevelController.getInstance();
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    @Override
    public void update(double elapsedTime) {
        mLevelController.update();
    }

    @Override
    public void loadContent(ContentManager content) {
        Spaceship.getMainBody().setID(content.loadImage(Spaceship.getMainBody().getImagePath()));
        Spaceship.getEngine().setID(content.loadImage(Spaceship.getEngine().getImagePath()));
        Spaceship.getExtraPart().setID(content.loadImage(Spaceship.getExtraPart().getImagePath()));
        Spaceship.getCannon().setID(content.loadImage(Spaceship.getCannon().getImagePath()));
        Missile tempMissile = Spaceship.getCannon().getMissile();
        tempMissile.setID(content.loadImage(tempMissile.getImagePath()));
        Spaceship.getCannon().setMissile(tempMissile);
        Sound tempSound = Spaceship.getCannon().getAttackSound();
        try {
            tempSound.id = content.loadSound(tempSound.filePath);
        } catch(IOException io) {
            io.printStackTrace();
        }
        Spaceship.getCannon().setAttackSound(tempSound);

        content.loadImages(ObjectsDAO.getImages());
        content.loadImages(AsteroidTypesDAO.getImages());

        mBackgroundId = content.loadImage("images/space.bmp");
        try {
            mBackgroundSoundId = content.loadLoopSound("sounds/SpyHunter.ogg");
            mImpactSoundId = content.loadSound("sounds/impact.wav");
        } catch(IOException io) {
            //DO NOTHING.
        }

    }

    @Override
    public void unloadContent(ContentManager content) {

    }

    @Override
    public void draw() {
        mLevelController.draw();
    }
//endregion COMMANDS
}
