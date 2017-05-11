package edu.byu.cs.superasteroids.models;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Iterator;

import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.core.GraphicsUtils;
import edu.byu.cs.superasteroids.database.CannonsDAO;
import edu.byu.cs.superasteroids.database.EnginesDAO;
import edu.byu.cs.superasteroids.database.ExtraPartsDAO;
import edu.byu.cs.superasteroids.database.MainBodiesDAO;
import edu.byu.cs.superasteroids.database.PowerCoresDAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.game.GameController;
import edu.byu.cs.superasteroids.game.InputManager;

/**
 * Created by bergeson on 7/7/2016.
 */
public class Spaceship {

//**************************************************************************************************
//                                           DOMAIN
//**************************************************************************************************
//region DOMAIN
    private static final float SCALE = 0.3f;

    private static MainBody sMainBody;
    private static PowerCore sPowerCore;
    private static Cannon sCannon;
    private static Engine sEngine;
    private static ExtraPart sExtraPart;

    private static int sSpaceshipSpeed;
    private static int sSpaceshipDamage;
    private static PointF sSpaceshipCoordinates = new PointF(0,0);
    private static int sSpaceshipLives;
    private static int sSpaceshipRotation;
    private static RectF sBound;
    private static Spaceship instance;

    private static ArrayList<Missile> sMissiles = new ArrayList<>();
    private static final int TICKS_FIVE_SECONDS = 150;
    private static int sTicksSinceHit = TICKS_FIVE_SECONDS;
    private static boolean sInSafeZone = false;
    private static int MAX_ROATATION_PER_UPDATE = 5;
//endregion DOMAIN


//**************************************************************************************************
//                                           CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    /**
     * Basic Constructors.
     */
    private Spaceship(){
        sSpaceshipCoordinates = new PointF(1500,1500);
    }

    public static Spaceship getInstance() {
        if(instance == null) {
            instance = new Spaceship();
        }

        return instance;
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                           QUERIES
//**************************************************************************************************
//region QUERIES
    //TODO: remove
//    /**
//     * Returns the spaceship speed.
//     * @return int representing the spaceship speed.
//     */
//    public static int getSpaceshipSpeed() {
//        return sSpaceshipSpeed;
//    }
//
//    /**
//     * Returns the spaceship damage.
//     * @return int representing the spaceship damage.
//     */
//    public static int getSpaceshipDamage() {
//        return sSpaceshipDamage;
//    }

    /**
     * Returns the spaceship coordinates.
     * @return PointF representing the spaceship coordinates.
     */
    public static PointF getSpaceshipCoordinates() {
        return sSpaceshipCoordinates;
    }

    //TODO: remove
//    /**
//     * Returns the number of spaceship lives.
//     * @return int representing the number of spaceship lives.
//     */
//    public static int getSpaceshipLives() {
//        return sSpaceshipLives;
//    }
//
//    /**
//     * Returns the spaceship rotation in degrees.
//     * @return int representing the spaceship rotation in degrees.
//     */
//    public static int getSpaceshipRotation(){
//        return sSpaceshipRotation;
//    }

    /**
     * converts a coordinate string into a PointF object.
     * @param point String representing coordinates.
     * @return PointF representing coordinates.
     */
    public static PointF convertStringToPointF(String point) {
        PointF retval = new PointF();
        int commaIndex = point.indexOf(',');
        String xValue = point.substring(0, commaIndex);
        String yValue = point.substring(commaIndex + 1);
        retval.set(Float.valueOf(xValue), Float.valueOf(yValue));
        return retval;
    }

    /**
     * Converts a PointF object to String.
     * @param point PointF representing coordinates.
     * @return String representing the coordinates.
     */
    public static String covertPointFtoString(PointF point) {
        String retval = String.valueOf(point.x) + ", " + String.valueOf(point.y);
        return retval;
    }

    /**
     * Tells if all parts of the spaceship has been initialized.
     * @return true if all parts are initialized, false if not.
     */
    public static boolean isComplete() {
        if(sMainBody != null && sEngine != null && sPowerCore != null && sCannon != null
                && sExtraPart != null) {
            return true;
        }

        return false;
    }

    /**
     * Returns the current Spaceship MainBody
     * @return MainBody
     */
    public static MainBody getMainBody() {
        return sMainBody;
    }

    /**
     * Returns the current Spaceship Engine.
     * @return Engine
     */
    public static Engine getEngine() {
        return sEngine;
    }

    /**
     * Returns the current Spaceship ExtraPart.
     * @return ExtraPart
     */
    public static ExtraPart getExtraPart() {
        return sExtraPart;
    }

    /**
     * Returns the current Spaceship Cannon;
     * @return Cannon
     */
    public static Cannon getCannon() {
        return sCannon;
    }

    /**
     * Returns a list of the missiles flying around.
     * @return
     */
    public static ArrayList<Missile> getMissiles() {
        return sMissiles;
    }

    /**
     * Returns the RectF bounding the spaceship.
     * @return RectF
     */
    public static RectF getSpaceshipBound() {
        return sBound;
    }

    /**
     * Scales the point by the scale provided.
     * @param scale float, the amount to be scaled by.
     * @param point PointF, the point to be scaled.
     * @return PointF of the new point.
     */
    public static PointF scalePointF(float scale, PointF point) {
        PointF retval = new PointF();
        retval.x = point.x * scale;
        retval.y = point.y * scale;
        return retval;
    }
//endregion QUERIES


//**************************************************************************************************
//                                           COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * Set the spaceship coordinates.
     * @param spaceshipCoordinates PointF representing the spaceship coordinates.
     */
    public static void setSpaceshipCoordinates(PointF spaceshipCoordinates) {
        sSpaceshipCoordinates = spaceshipCoordinates;
    }

    /**
     * Set the number of spaceship lives.
     * @param spaceshipLives int representing the number of spaceship lives.
     */
    public static void setSpaceshipLives(int spaceshipLives) {
        sSpaceshipLives = spaceshipLives;
    }

    /**
     * Set the spaceship rotation in degrees.
     * @param spaceshipRotation int representing the spaceship rotation in degrees.
     */
    public static void setSpaceshipRotation(int spaceshipRotation) {
        sSpaceshipRotation = spaceshipRotation;
    }

    /**
     * Updates the information for the spaceship.
     */
    public static void update(){
        if(sTicksSinceHit < TICKS_FIVE_SECONDS) ++sTicksSinceHit;
        else sInSafeZone = false;

        PointF movePoint = InputManager.movePoint;
        if(movePoint != null) {
            move(movePoint);
        }
        if(InputManager.firePressed) {
            fire();
        }

        Iterator<Missile> it = sMissiles.iterator();
        while(it.hasNext()) {
            Missile missile = it.next();
            missile.update();
            if(!missile.isVisible()) it.remove();

            if(missile.getHit()) it.remove();
        }

        sSpaceshipRotation++;
        if(sSpaceshipRotation > 359) sSpaceshipRotation = 0;
    }

    /**
     * Draws the spaceship.
     */
    public static void draw(){
        PointF viewPortCoordinates = ViewPort.getViewPortCoordinate(sSpaceshipCoordinates);
        DrawingHelper.drawImage(sMainBody.getID(), viewPortCoordinates.x, viewPortCoordinates.y,
                sSpaceshipRotation, SCALE, SCALE, 255);

        if(sCannon != null) {
            PointF bodyOffset = GraphicsUtils.subtract(sMainBody.getCannonAttachPoint(),
                    sMainBody.getCenterPoint());
            PointF partOffset = GraphicsUtils.subtract(sCannon.getCenterPoint(), sCannon.getAttachPoint());
            PointF totalOffset = GraphicsUtils.add(bodyOffset, partOffset);
            PointF rotateOffset = GraphicsUtils.rotate(totalOffset, GraphicsUtils.degreesToRadians(sSpaceshipRotation));
            rotateOffset.set(rotateOffset.x*SCALE, rotateOffset.y*SCALE);
            PointF p = GraphicsUtils.add(viewPortCoordinates, rotateOffset);
            DrawingHelper.drawImage(sCannon.getID(),p.x, p.y,sSpaceshipRotation, SCALE, SCALE, 255);
        }
        if(sExtraPart != null) {
            PointF bodyOffset = GraphicsUtils.subtract(sMainBody.getExtraPartAttachPoint(),
                    sMainBody.getCenterPoint());
            PointF partOffset = GraphicsUtils.subtract(sExtraPart.getCenterPoint(),
                    sExtraPart.getAttachPoint());
            PointF totalOffset = GraphicsUtils.add(bodyOffset, partOffset);
            PointF rotateOffset = GraphicsUtils.rotate(totalOffset,
                    GraphicsUtils.degreesToRadians(sSpaceshipRotation));
            rotateOffset.set(rotateOffset.x*SCALE, rotateOffset.y*SCALE);
            PointF p = GraphicsUtils.add(viewPortCoordinates, rotateOffset);
            DrawingHelper.drawImage(sExtraPart.getID(), p.x, p.y,sSpaceshipRotation, SCALE, SCALE, 255);
        }
        if(sEngine != null) {
            PointF bodyOffset = GraphicsUtils.subtract(sMainBody.getEngineAttachPoint(),
                    sMainBody.getCenterPoint());
            PointF partOffset = GraphicsUtils.subtract(sEngine.getCenterPoint(),
                    sEngine.getAttachPoint());
            PointF totalOffset = GraphicsUtils.add(bodyOffset, partOffset);
            PointF rotateOffset = GraphicsUtils.rotate(totalOffset,
                    GraphicsUtils.degreesToRadians(sSpaceshipRotation));
            rotateOffset.set(rotateOffset.x*SCALE, rotateOffset.y*SCALE);
            PointF p = GraphicsUtils.add(viewPortCoordinates, rotateOffset);
            DrawingHelper.drawImage(sEngine.getID(), p.x, p.y, sSpaceshipRotation, SCALE, SCALE, 255);
        }

        drawMissiles();
        drawSafeZone();
    }

    public static void draw(float scale) {
        if(sMainBody != null){
            DrawingHelper.drawImage(sMainBody.getID(),
                    sSpaceshipCoordinates.x, sSpaceshipCoordinates.y,
                    sSpaceshipRotation, scale, scale, 255);
        }
        if(sCannon != null){
            PointF bodyOffset = GraphicsUtils.subtract(scalePointF(scale, sMainBody.getCannonAttachPoint())
                    , scalePointF(scale, sMainBody.getCenterPoint()));
            PointF partOffset = GraphicsUtils.subtract(scalePointF(scale, sCannon.getCenterPoint()),
                    scalePointF(scale, sCannon.getAttachPoint()));
            PointF totOffset = GraphicsUtils.add(bodyOffset, partOffset);
            PointF pos = GraphicsUtils.add(sSpaceshipCoordinates, totOffset);
            DrawingHelper.drawImage(sCannon.getID(),
                    pos.x, pos.y,sSpaceshipRotation, scale, scale, 255);
        }
        if(sExtraPart != null){
            PointF bodyOffset = GraphicsUtils.subtract(scalePointF(scale, sMainBody.getExtraPartAttachPoint()),
                    scalePointF(scale, sMainBody.getCenterPoint()));
            PointF partOffset = GraphicsUtils.subtract(scalePointF(scale, sExtraPart.getCenterPoint()),
                    scalePointF(scale, sExtraPart.getAttachPoint()));
            PointF totOffset = GraphicsUtils.add(bodyOffset, partOffset);
            PointF pos = GraphicsUtils.add(sSpaceshipCoordinates, totOffset);
            DrawingHelper.drawImage(sExtraPart.getID(), pos.x, pos.y,sSpaceshipRotation,
                    scale, scale, 255);
        }
        if(sEngine != null){
            PointF bodyOffset = GraphicsUtils.subtract(scalePointF(scale, sMainBody.getEngineAttachPoint()),
                    scalePointF(scale, sMainBody.getCenterPoint()));
            PointF partOffset = GraphicsUtils.subtract(scalePointF(scale, sEngine.getCenterPoint()),
                    scalePointF(scale, sEngine.getAttachPoint()));
            PointF totOffset = GraphicsUtils.add(bodyOffset, partOffset);
            PointF pos = GraphicsUtils.add(sSpaceshipCoordinates, totOffset);
            DrawingHelper.drawImage(sEngine.getID(), pos.x, pos.y, sSpaceshipRotation,
                    scale, scale, 255);
        }
    }

    /**
     * Sets the MainBody for the Spaceship
     * @param mainBody the MainBody to be set to.
     */
    public static void setMainBody(MainBody mainBody) {
        sMainBody = mainBody;
        if(isComplete()) {
            sSpaceshipDamage = sPowerCore.getCannonBoost() * sCannon.getAttackDamage();
            sSpaceshipSpeed = sPowerCore.getEngineBoost() * sEngine.getEngineSpeed() / 2;
        }
    }

    /**
     * Sets the PowerCore for the Spaceship.
     * @param powerCore the PowerCore for the Spaceship.
     */
    public static void setPowerCore(PowerCore powerCore) {
        sPowerCore = powerCore;
        if(isComplete()) {
            sSpaceshipDamage = sPowerCore.getCannonBoost() * sCannon.getAttackDamage();
            sSpaceshipSpeed = sPowerCore.getEngineBoost() * sEngine.getEngineSpeed() / 2;
        }

    }

    /**
     * Sets the Cannon for the Spaceship
     * @param cannon the Cannon for the Spaceship
     */
    public static void setCannon(Cannon cannon) {
        sCannon = cannon;
        if(isComplete()) {
            sSpaceshipDamage = sPowerCore.getCannonBoost() * sCannon.getAttackDamage();
            sSpaceshipSpeed = sPowerCore.getEngineBoost() * sEngine.getEngineSpeed() / 2;
        }
    }

    /**
     * Sets the Engine for the Spaceship
     * @param engine the Engine for the Spaceship
     */
    public static void setEngine(Engine engine) {
        sEngine = engine;
        if(isComplete()) {
            sSpaceshipDamage = sPowerCore.getCannonBoost() * sCannon.getAttackDamage();
            sSpaceshipSpeed = sPowerCore.getEngineBoost() * sEngine.getEngineSpeed() / 2;
        }
    }

    /**
     * Sets the ExtraPart for the Spaceship
     * @param extraPart the ExtraPart for the Spaceship.
     */
    public static void setExtraPart(ExtraPart extraPart) {
        sExtraPart = extraPart;
        if(isComplete()) {
            sSpaceshipDamage = sPowerCore.getCannonBoost() * sCannon.getAttackDamage();
            sSpaceshipSpeed = sPowerCore.getEngineBoost() * sEngine.getEngineSpeed() / 2;
        }
    }

    /**
     * Creates a random ship.
     */
    public static void randomShip() {
        ContentManager contentManager = ContentManager.getInstance();
        int numberOfShips = MainBodiesDAO.getCount();
        int index = ((int)(Math.random() * 100) % numberOfShips) + 1;
        setMainBody(MainBodiesDAO.getMainBody(index));
        sMainBody.setID(contentManager.loadImage(sMainBody.getImagePath()));
        setExtraPart(ExtraPartsDAO.getExtraPart(index));
        sExtraPart.setID(contentManager.loadImage(sExtraPart.getImagePath()));
        setCannon(CannonsDAO.getCannon(index));
        sCannon.setID(contentManager.loadImage(sCannon.getImagePath()));
        setPowerCore(PowerCoresDAO.getPowerCore(index));
        sPowerCore.setID(contentManager.loadImage(sPowerCore.getPowerCoreImagePath()));
        setEngine(EnginesDAO.getEngine(index));
        sEngine.setID(contentManager.loadImage(sEngine.getImagePath()));
    }

    /**
     * Sets the Spaceship bounds based on the coordinates.
     */
    public static void setSpaceshipBounds() {
        float spaceshipWidth = (sMainBody.getImageWidth() + sCannon.getImageWidth()
                + sExtraPart.getImageWidth()) * SCALE;
        float spaceshipHeight = (sMainBody.getImageHeight() + sEngine.getImageHeight()) * SCALE;
        float boundWidth = Math.min(spaceshipHeight, spaceshipWidth);
        float leftBound = sSpaceshipCoordinates.x - boundWidth/4.0f;
        float topBound = sSpaceshipCoordinates.y - boundWidth/4.0f;
        float rightBound = sSpaceshipCoordinates.x + boundWidth/4.0f;
        float bottomBound = sSpaceshipCoordinates.y + boundWidth/4.0f;
        sBound = new RectF(leftBound,topBound,rightBound,bottomBound);
    }

    /**
     * This gets called when an asteroid hits the ship.
     */
    public static void hit() {
        if(!sInSafeZone) {
            sSpaceshipLives--;
            if(sSpaceshipLives < 1) {
                LevelController.gameOver();
            }
            else {
                sInSafeZone = true;
                sTicksSinceHit = 0;
            }
        }
    }
//endregion COMMANDS


//**************************************************************************************************
//                                           PRIVATE METHODS
//**************************************************************************************************
//region PRIVATE METHODS
    private static void move(PointF movePoint) {
        if(sBound == null) setSpaceshipBounds();
        GraphicsUtils.MoveObjectResult result = GraphicsUtils.moveObject(sSpaceshipCoordinates,
                sBound, sSpaceshipSpeed, GraphicsUtils.degreesToRadians(sSpaceshipRotation-90),
                GameController.ELAPSED_TIME);
        sSpaceshipCoordinates = result.getNewObjPosition();
        sBound = result.getNewObjBounds();

        if(sBound.right > LevelController.getLevelWidth()) {
            sSpaceshipCoordinates.x = LevelController.getLevelWidth() - sBound.width()/2.0f;
            setSpaceshipBounds();
        }
        else if(sBound.left < 0 ){
            sSpaceshipCoordinates.x = sBound.width()/2.0f;
            setSpaceshipBounds();
        }

        if(sBound.bottom > LevelController.getLevelHeight()) {
            sSpaceshipCoordinates.y = LevelController.getLevelHeight() - sBound.height()/2.0f;
            setSpaceshipBounds();
        }
        else if(sBound.top < 0 ) {
            sSpaceshipCoordinates.y = sBound.height()/2.0f;
            setSpaceshipBounds();
        }

        PointF diff = GraphicsUtils.subtract(ViewPort.getViewPortCoordinate(sSpaceshipCoordinates),
                movePoint);
        double angle = GraphicsUtils.radiansToDegrees(Math.atan(diff.x/diff.y));

        int newSpaceshipRotation;
        if(diff.y < 0 && diff.x < 0) {
            newSpaceshipRotation = 180 - (int)angle;
        }
        else if(diff.y < 0 && diff.x >= 0) {
            newSpaceshipRotation = -180 - (int)angle;
        }
        else {
            newSpaceshipRotation = -1 * (int)angle;
        }

        if(sSpaceshipRotation - newSpaceshipRotation > MAX_ROATATION_PER_UPDATE)
            sSpaceshipRotation -= MAX_ROATATION_PER_UPDATE;
        else if(sSpaceshipRotation - newSpaceshipRotation < (-1 * MAX_ROATATION_PER_UPDATE))
            sSpaceshipRotation += MAX_ROATATION_PER_UPDATE;
        else
            sSpaceshipRotation = newSpaceshipRotation;

    }

    /**
     * Fires a missile.
     */
    private static void fire(){
        Missile newMissile = new Missile();
        newMissile.setID(sCannon.getMissile().getID());
        newMissile.setMissileRotation(sSpaceshipRotation);
        newMissile.setMissileSpeed(sSpaceshipSpeed / 2);
        PointF bodyOffset = GraphicsUtils.subtract(sMainBody.getCannonAttachPoint(),
                sMainBody.getCenterPoint());
        PointF partOffset = GraphicsUtils.subtract(sCannon.getAttackEmitPoint(),
                sCannon.getAttachPoint());
        PointF totalOffset = GraphicsUtils.add(bodyOffset, partOffset);
        totalOffset.set(totalOffset.x * SCALE - 23, totalOffset.y * SCALE);
        PointF rotateOffset = GraphicsUtils.rotate(totalOffset , GraphicsUtils.degreesToRadians(sSpaceshipRotation));
        //rotateOffset.set(rotateOffset.x*SCALE , rotateOffset.y*SCALE);
        PointF adjustedEmitPoint = GraphicsUtils.add(sSpaceshipCoordinates, rotateOffset);
        newMissile.setMissileCoordinate(adjustedEmitPoint);
        sMissiles.add(newMissile);
        ContentManager.getInstance().playSound(sCannon.getAttackSound().id, 1.0f, 1.0f);
    }

    /**
     * Draws the missiles
     */
    private static void drawMissiles() {
        for(Missile missile : sMissiles) {
            PointF viewPortPosition = ViewPort.getViewPortCoordinate(missile.getMissileCoordinate());
            DrawingHelper.drawImage(missile.getID(), viewPortPosition.x, viewPortPosition.y,
                   missile.getMissileRotation(), SCALE, SCALE, 255);
        }
    }

    private static void drawSafeZone() {
        if(sInSafeZone) {
            DrawingHelper.drawFilledCircle(ViewPort.getViewPortCoordinate(sSpaceshipCoordinates),
                    sBound.width(), Color.RED, 100);
        }
    }
//endregion PRIVATE METHODS
}