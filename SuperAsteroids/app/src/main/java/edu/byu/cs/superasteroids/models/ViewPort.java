package edu.byu.cs.superasteroids.models;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import edu.byu.cs.superasteroids.drawing.DrawingHelper;

/**
 * Created by bergeson on 7/18/2016.
 */
public class ViewPort {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static int mWidth;
    private static int mHeight;
    private static int mLevelWidth;
    private static int mLevelHeight;
    private static Rect mPosition;
    private static Rect mMiniMap;
    private Rect mMiniViewPort;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic Constructor.
     * @param levelLength the current level length.
     * @param levelHeight the current level height.
     */
    public ViewPort(int levelLength, int levelHeight) {
        mLevelWidth = levelLength;
        mLevelHeight = levelHeight;
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Returns the RectF of where the ViewPort is in relation to the level.
     * @return
     */
    public Rect getPosition() {
        return mPosition;
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region Commands
    /**
     * Draws the view port in the top left corner of the screen.
     */
    public void draw(){
        //Draws the minimap
        DrawingHelper.drawRectangle(mMiniMap, Color.WHITE, 255);

        //Draws the mini view port in the minimap
        DrawingHelper.drawRectangle(mMiniViewPort, Color.WHITE, 255);

        //Draws the mini spaceship in the minimap
        PointF spaceship = Spaceship.getSpaceshipCoordinates();
        PointF miniSpaceship = getMiniMapCoordinates(spaceship);
        DrawingHelper.drawPoint(miniSpaceship,10, Color.GREEN, 255);

        //Draws all the asteroids in the minimap
        Level currentLevel = LevelController.getCurrentLevel();
        for(Asteroid asteroid : currentLevel.getLevelAsteroids()) {
            PointF asteroidPosition = asteroid.getAsteroidPosition();
            PointF miniAsteroid = getMiniMapCoordinates(asteroidPosition);
            DrawingHelper.drawPoint(miniAsteroid, 5, Color.RED, 255);
        }


    }

    /**
     * Updates the ViewPort position based on the Spaceship location.
     */
    public void update(){

        updatePosition();


        updateMiniViewPort();

        if(mPosition.left < 0) {
            mPosition.left = 0;
            mPosition.right = mWidth/2;
        }
        else if(mPosition.right > mLevelWidth) {
            mPosition.right = mLevelWidth - 1;
            mPosition.left = mPosition.right - mWidth/2;
        }

        if(mPosition.top < 0) {
            mPosition.top = 0;
            mPosition.bottom = mHeight/2;
        }
        else if(mPosition.bottom > mLevelHeight) {
            mPosition.bottom = mLevelHeight - 1;
            mPosition.top = mPosition.bottom - mHeight/2;
        }
    }

    /**
     * Converts from the game world coordinates to the view port coordinates.
     * @param worldCoordinate the game world coordinates.
     * @return PointF representing the ViewPort coordinates.
     */
    public static PointF getViewPortCoordinate(PointF worldCoordinate){
        if(mPosition.contains((int)worldCoordinate.x, (int)worldCoordinate.y)) {


        }
        PointF output = new PointF();
        double ratioX = (double)(worldCoordinate.x - mPosition.left)/(double) mWidth;
        double ratioY = (double)(worldCoordinate.y - mPosition.top)/(double) mHeight;
        output.x = ((float)(ratioX * (double)DrawingHelper.getGameViewWidth()));
        output.y = ((float)(ratioY * (double)DrawingHelper.getGameViewHeight()));
        return output;

    }

    //TODO: remove
//    /**
//     * Converts from ViewPort coordinates to game world coordinates.
//     * @param viewCoordinate the ViewPort coordinates.
//     * @return PointF representing the game world coordinates.
//     */
//    public static PointF getWorldCoordinate(PointF viewCoordinate){
//        PointF output = new PointF();
//        double ratioX = (double)(viewCoordinate.x)/(double)DrawingHelper.getGameViewWidth();
//        double ratioY = (double)(viewCoordinate.y)/(double)DrawingHelper.getGameViewHeight();
//        output.x = (mPosition.left + (float)(ratioX * (double) mWidth));
//        output.y = (mPosition.top + (float)(ratioY * (double) mHeight));
//        return output;
//    }

//    /**
//     * Converts from world bounds to ViewPort bounds.
//     * @param worldBound the world coordinate system bound.
//     * @return RectF representing ViewPort bounds.
//     */
//    public static RectF getViewPortBound(RectF worldBound) {
//        PointF topLeft = new PointF(worldBound.left, worldBound.top);
//        PointF bottomRight = new PointF(worldBound.right, worldBound.bottom);
//        topLeft = getViewPortCoordinate(topLeft);
//        bottomRight = getViewPortCoordinate(bottomRight);
//        return new RectF(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
//    }
//
//    public static RectF getWorldBound(RectF viewPortBound) {
//        RectF output = new RectF();
//        float ratioX = viewPortBound.centerX()/(float)DrawingHelper.getGameViewWidth();
//        float ratioY = viewPortBound.centerY()/(float)DrawingHelper.getGameViewHeight();
//        float outputCenterX = mPosition.left + ratioX * (float)mWidth;
//        float outputCenterY = mPosition.top + ratioY * (float)mHeight;
//        float outputWidth = ratioX * viewPortBound.width();
//        float outputHeight = ratioY * viewPortBound.height();
//
//        output.left = outputCenterX - outputWidth/2.0f;
//        output.right = outputCenterX + outputWidth/2.0f;
//        output.top = outputCenterY - outputHeight/2.0f;
//        output.bottom = outputCenterY + outputHeight/2.0f;
//        return output;
//    }

    /**
     * Initializes the viewport based on current level dimensions.
     */
    public void initialize() {
        mWidth = DrawingHelper.getGameViewWidth()/2;
        mHeight = DrawingHelper.getGameViewHeight()/2;
        mPosition = new Rect(mLevelWidth/2 - mWidth/2,
                mLevelHeight/2 - mHeight/2,
                mLevelWidth/2 + mWidth/2,
                mLevelHeight/2 + mHeight/2);

        int miniMapLeft = DrawingHelper.getGameViewWidth()*9/10;
        int miniMapTop = 0;
        int miniMapRight = DrawingHelper.getGameViewWidth()-1;
        int miniMapBottom = DrawingHelper.getGameViewWidth()/10;
        int miniMapWidth = miniMapRight - miniMapLeft;
        int mimiMapHeight = miniMapBottom;
        mMiniMap = new Rect(miniMapLeft,miniMapTop,miniMapRight,miniMapBottom);

        mMiniViewPort = new Rect();
        int miniViewLeft = miniMapLeft + (mPosition.left * miniMapWidth / mLevelWidth);
        int miniViewRight = miniMapLeft + (mPosition.right * miniMapWidth / mLevelWidth);
        int miniViewTop = mPosition.top * mimiMapHeight / mLevelHeight;
        int miniViewBottom = mPosition.bottom * mimiMapHeight / mLevelHeight;
        mMiniViewPort.set(miniViewLeft,miniViewTop,miniViewRight,miniViewBottom);
    }

    /**
     * Checks if the specified coordinate is in view.
     * @param worldCoordinate PointF representing the world coordinate.
     * @return true if the point is visible, false if not.
     */
    public static boolean isVisible(PointF worldCoordinate) {
        if(worldCoordinate.x < mPosition.left || worldCoordinate.x > mPosition.right)
            return false;

        if(worldCoordinate.y < mPosition.top || worldCoordinate.y > mPosition.bottom)
            return false;

        return true;
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private static PointF getMiniMapCoordinates(PointF worldCoordinate) {
        float xCoordinate = mMiniMap.left + worldCoordinate.x * mMiniMap.width() / (float)mLevelWidth;
        float yCoordinate = worldCoordinate.y * mMiniMap.height() / (float)mLevelHeight;
        return new PointF(xCoordinate, yCoordinate);
    }

    private void updateMiniViewPort() {
        int miniViewLeft = mMiniMap.left + (mPosition.left * mMiniMap.width() / mLevelWidth);
        int miniViewRight = mMiniMap.left + (mPosition.right * mMiniMap.width() / mLevelWidth);
        int miniViewTop = mPosition.top * mMiniMap.height() / mLevelHeight;
        int miniViewBottom = mPosition.bottom * mMiniMap.height() / mLevelHeight;
        mMiniViewPort.set(miniViewLeft,miniViewTop,miniViewRight,miniViewBottom);
    }

    private void updatePosition() {
        PointF shipPosition = Spaceship.getSpaceshipCoordinates();
        mPosition.left = (int)shipPosition.x - (mWidth/2);
        mPosition.right = (int)shipPosition.x + (mWidth/2);
        mPosition.top = (int)shipPosition.y - (mHeight/2);
        mPosition.bottom = (int)shipPosition.y + (mHeight/2);
    }
//endregion PRIVATE METHODS
}
