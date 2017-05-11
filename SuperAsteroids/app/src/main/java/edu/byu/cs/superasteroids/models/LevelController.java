package edu.byu.cs.superasteroids.models;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.LevelsDAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.GameController;

/**
 * Created by User on 7/18/2016.
 */
public class LevelController {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static LevelController instance;
    public static final int BACKGROUND_WIDTH = 2048;
    public static final int BACKGROUND_HEIGHT = 2048;
    private static Level mCurrentLevel;
    private static ViewPort mViewPort;
    private static Spaceship sSpaceship;
    private static boolean sIsNewGame;
    private static boolean sIsGameOver = false;
    private static boolean sDisplaySplashScreen = false;
    private static final int TWO_SECONDS_TICKS = 60;
    private static int sTicksSinceSplashScreen = 0;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic Contructor.
     */
    private LevelController() {
        mCurrentLevel = LevelsDAO.getLevel(1);
        mViewPort = new ViewPort(mCurrentLevel.getLevelWidth(), mCurrentLevel.getLevelHeight());
        sSpaceship = Spaceship.getInstance();
        PointF p = new PointF(mCurrentLevel.getLevelWidth()/2, mCurrentLevel.getLevelHeight()/2);
        sSpaceship.setSpaceshipCoordinates(p);
        sSpaceship.setSpaceshipLives(3);
        sSpaceship.setSpaceshipRotation(0);
        sIsNewGame = true;
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns an instance of the LevelController.
     * @return LevelController.
     */
    public static LevelController getInstance() {
        if(instance == null) {
            instance = new LevelController();
        }

        return instance;
    }

    /**
     * Returns the current level.
     * @return
     */
    public static Level getCurrentLevel() {
        return mCurrentLevel;
    }

    /**
     * Returns the current level length;
     * @return int representing the current level length.
     */
    public static int getLevelWidth() {
        return mCurrentLevel.getLevelWidth();
    }

    /**
     * Returns the current level height;
     * @return int representing the current level height.
     */
    public static int getLevelHeight() {
        return mCurrentLevel.getLevelHeight();
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Lets the LevelController know that it's starting a new game.
     */
    public static void startingNewGame() {
        sIsNewGame = true;
    }

    /**
     * Updates all objects in the level, including the ship.
     */
    public static void update() {
        if(sIsNewGame) {
            initializeLevel(1);
            sIsGameOver = false;
        }
        if(sIsGameOver) return;
        if(sDisplaySplashScreen) {
            sTicksSinceSplashScreen++;
            if(sTicksSinceSplashScreen > TWO_SECONDS_TICKS) {
                sDisplaySplashScreen = false;
            }
            return;
        }

        mCurrentLevel.update();
        sSpaceship.update();
        mViewPort.update();

        if(mCurrentLevel.outOfAsteroids()) {
            initializeLevel(mCurrentLevel.getLevelValue()+1);
            sDisplaySplashScreen = true;
            sTicksSinceSplashScreen = 0;
        }
    }

    /**
     * Draws everything in the level, including the ship.
     */
    public static void draw() {
        if(sIsNewGame) {
            initializeLevel(1);
            sIsGameOver = false;
        }
        if(sIsGameOver) {
            Rect wholeScreen = new Rect(0, 0, DrawingHelper.getGameViewWidth(),
                    DrawingHelper.getGameViewHeight());
            DrawingHelper.drawFilledRectangle(wholeScreen, Color.BLACK, 100);
            DrawingHelper.drawCenteredText("GAME OVER", 100, Color.WHITE);
            return;
        }
        if(sDisplaySplashScreen) {
            Rect wholeScreen = new Rect(0, 0, DrawingHelper.getGameViewWidth(),
                    DrawingHelper.getGameViewHeight());
            DrawingHelper.drawFilledRectangle(wholeScreen, Color.BLACK, 100);

            DrawingHelper.drawCenteredText("Level: " + mCurrentLevel.getLevelValue(), 100, Color.WHITE);
            return;
        }

        //Draws the background
        Rect viewPortPosition = mViewPort.getPosition();
        int leftPosition = viewPortPosition.left*BACKGROUND_WIDTH/getLevelWidth();
        int topPosition = viewPortPosition.top*BACKGROUND_HEIGHT/getLevelHeight();
        int rightPosition = viewPortPosition.right*BACKGROUND_WIDTH/getLevelWidth();
        int bottomPosition = viewPortPosition.bottom*BACKGROUND_HEIGHT/getLevelHeight();
        Rect backgroundPosition = new Rect(leftPosition, topPosition, rightPosition, bottomPosition);
        Rect viewPos = new Rect(0,0, DrawingHelper.getGameViewWidth(),DrawingHelper.getGameViewHeight());
        DrawingHelper.drawImage(GameController.mBackgroundId, backgroundPosition,viewPos);

        mCurrentLevel.draw();

        mViewPort.draw();
        sSpaceship.draw();
    }

    /**
     * Gets called when the Spaceship runs out of lives.
     */
    public static void gameOver() {
        sIsGameOver = true;
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private static void initializeLevel(int level) {
        mCurrentLevel = LevelsDAO.getLevel(level);
        mCurrentLevel.initializeAsteroids();
        mViewPort = new ViewPort(mCurrentLevel.getLevelWidth(), mCurrentLevel.getLevelHeight());
        mViewPort.initialize();
        sSpaceship = Spaceship.getInstance();
        PointF p = new PointF(mCurrentLevel.getLevelWidth()/2, mCurrentLevel.getLevelHeight()/2);
        sSpaceship.setSpaceshipCoordinates(p);
        sSpaceship.setSpaceshipLives(3);
        sSpaceship.setSpaceshipRotation(0);
        sSpaceship.setSpaceshipBounds();
        sIsNewGame = false;
        ContentManager.getInstance().playLoop(GameController.mBackgroundSoundId);
    }
//endregion PRIVATE METHODS
}
