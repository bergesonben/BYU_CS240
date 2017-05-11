package edu.byu.cs.superasteroids.ship_builder;

import android.graphics.PointF;

import java.util.List;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.content.ContentManager;
import edu.byu.cs.superasteroids.database.CannonsDAO;
import edu.byu.cs.superasteroids.database.EnginesDAO;
import edu.byu.cs.superasteroids.database.ExtraPartsDAO;
import edu.byu.cs.superasteroids.database.MainBodiesDAO;
import edu.byu.cs.superasteroids.database.PowerCoresDAO;
import edu.byu.cs.superasteroids.drawing.DrawingHelper;
import edu.byu.cs.superasteroids.models.Cannon;
import edu.byu.cs.superasteroids.models.Engine;
import edu.byu.cs.superasteroids.models.ExtraPart;
import edu.byu.cs.superasteroids.models.LevelController;
import edu.byu.cs.superasteroids.models.MainBody;
import edu.byu.cs.superasteroids.models.PowerCore;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by User on 7/16/2016.
 */
public class ShipBuildingController implements IShipBuildingController {

//**************************************************************************************************
//                                          DOMAIN
//**************************************************************************************************
//region DOMAIN
    private IShipBuildingView mView;
    private IShipBuildingView.PartSelectionView mCurrentPartView;
    private boolean isInitialized = false;
    private List<Integer> mMainBodies;
    private List<Integer> mCannons;
    private List<Integer> mExtraParts;
    private List<Integer> mEngines;
    private List<Integer> mPowerCores;
//endregion DOMAIN


//**************************************************************************************************
//                                          CONSTRUCTORS
//**************************************************************************************************
//region CONSTRUCTORS
    public ShipBuildingController(IShipBuildingView view) {
        mView = view;
        mCurrentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;
        isInitialized = false;
        Spaceship.setMainBody(null);
        Spaceship.setCannon(null);
        Spaceship.setExtraPart(null);
        Spaceship.setEngine(null);
        Spaceship.setPowerCore(null);
    }
//endregion CONSTRUCTORS


//**************************************************************************************************
//                                          QUERIES
//**************************************************************************************************
//region QUERIES
    /**
     * Gets the controller's view
     *
     * @return the controller's view
     */
    @Override
    public IView getView() {
        return null;
    }
//endregion QUERIES


//**************************************************************************************************
//                                          COMMANDS
//**************************************************************************************************
//region COMMANDS
    /**
     * The ship building view calls this function when a part selection view is loaded. This function
     * should be used to configure the part selection view. Example: Set the arrows for the view in
     * this function.
     *
     * @param partView
     */
    @Override
    public void onViewLoaded(IShipBuildingView.PartSelectionView partView) {
        if(isInitialized){
            switch(partView){
                case MAIN_BODY:
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, true, "Extra Part");
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, true, "Cannon");
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.UP, true, "Power Core");
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, true, "Engine");
                    break;
                case EXTRA_PART:
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, false, null);
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, true, "Main Body");
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, null);
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, null);
                    break;
                case CANNON:
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, true, "Main Body");
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, false,null);
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, null);
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, null);
                    break;
                case POWER_CORE:
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, false, null);
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, false,null);
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.UP, false, null);
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, true, "Main Body");
                    break;
                case ENGINE:
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.LEFT, false, null);
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.RIGHT, false,null);
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.UP, true, "Main Body");
                    mView.setArrow(partView, IShipBuildingView.ViewDirection.DOWN, false, null);
                    break;
                default:
                    break;
            }
        }

        if(Spaceship.isComplete()) {
            mView.setStartGameButton(true);
        }
    }

    /**
     * The ShipBuildingView calls this function as it is created. Load ship building content in this function.
     *
     * @param content An instance of the content manager. This should be used to load images and sound.
     */
    @Override
    public void loadContent(ContentManager content) {
        mMainBodies = content.loadImages(MainBodiesDAO.getImages());
        mCannons = content.loadImages(CannonsDAO.getImages());
        mExtraParts = content.loadImages(ExtraPartsDAO.getImages());
        mPowerCores = content.loadImages(PowerCoresDAO.getImages());
        mEngines = content.loadImages(EnginesDAO.getImages());

        mView.setPartViewImageList(IShipBuildingView.PartSelectionView.MAIN_BODY, mMainBodies);
        mView.setPartViewImageList(IShipBuildingView.PartSelectionView.ENGINE, mEngines);
        mView.setPartViewImageList(IShipBuildingView.PartSelectionView.CANNON, mCannons);
        mView.setPartViewImageList(IShipBuildingView.PartSelectionView.EXTRA_PART, mExtraParts);
        mView.setPartViewImageList(IShipBuildingView.PartSelectionView.POWER_CORE, mPowerCores);

        isInitialized = true;
    }

    /**
     * Unloads content from the game. The GameActivity will call this function after the game engine
     * exits the game loop. The ShipBuildingActivity will call this function after the "Start Game"
     * button has been pressed.
     *
     * @param content An instance of the content manager. This should be used to unload image and
     *                sound files.
     */
    @Override
    public void unloadContent(ContentManager content) {
        for(int id : mMainBodies)
            content.unloadImage(id);
        for(int id : mEngines)
            content.unloadImage(id);
        for(int id : mCannons)
            content.unloadImage(id);
        for(int id : mExtraParts)
            content.unloadImage(id);
        for(int id : mPowerCores)
            content.unloadImage(id);
    }

    /**
     * Draws the game delegate. This function will be 60 times a second.
     */
    @Override
    public void draw() {
        int centerX = DrawingHelper.getGameViewWidth()/2;
        int yOffset = DrawingHelper.getGameViewHeight()/3;
        PointF center = new PointF(centerX,yOffset);
        Spaceship.setSpaceshipCoordinates(center);
        Spaceship.draw(0.7f);

    }

    /**
     * The ShipBuildingView calls this function when the user makes a swipe/fling motion in the
     * screen. Respond to the user's swipe/fling motion in this function.
     *
     * @param direction The direction of the swipe/fling.
     */
    @Override
    public void onSlideView(IShipBuildingView.ViewDirection direction) {
        if(isInitialized) {
            switch (mCurrentPartView) {
                case MAIN_BODY:
                    switch (direction) {
                        case LEFT:
                            mCurrentPartView = IShipBuildingView.PartSelectionView.CANNON;
                            mView.animateToView(mCurrentPartView, IShipBuildingView.ViewDirection.RIGHT);
                            break;
                        case RIGHT:
                            mCurrentPartView = IShipBuildingView.PartSelectionView.EXTRA_PART;
                            mView.animateToView(mCurrentPartView, IShipBuildingView.ViewDirection.LEFT);
                            break;
                        case UP:
                            mCurrentPartView = IShipBuildingView.PartSelectionView.ENGINE;
                            mView.animateToView(mCurrentPartView, IShipBuildingView.ViewDirection.DOWN);
                            break;
                        case DOWN:
                            mCurrentPartView = IShipBuildingView.PartSelectionView.POWER_CORE;
                            mView.animateToView(mCurrentPartView, IShipBuildingView.ViewDirection.UP);
                            break;
                        default:
                            break;
                    }
                    break;
                case EXTRA_PART:
                    if (direction == IShipBuildingView.ViewDirection.LEFT) {
                        mCurrentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        mView.animateToView(mCurrentPartView, IShipBuildingView.ViewDirection.RIGHT);
                    }
                    break;
                case CANNON:
                    if (direction == IShipBuildingView.ViewDirection.RIGHT) {
                        mCurrentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        mView.animateToView(mCurrentPartView, IShipBuildingView.ViewDirection.LEFT);
                    }
                    break;
                case POWER_CORE:
                    if(direction == IShipBuildingView.ViewDirection.UP) {
                        mCurrentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        mView.animateToView(mCurrentPartView, IShipBuildingView.ViewDirection.DOWN);
                    }
                    break;
                case ENGINE:
                    if(direction == IShipBuildingView.ViewDirection.DOWN) {
                        mCurrentPartView = IShipBuildingView.PartSelectionView.MAIN_BODY;
                        mView.animateToView(mCurrentPartView, IShipBuildingView.ViewDirection.UP);
                    }
                    break;
                default: break;
            }
        }
    }

    /**
     * The part selection fragments call this function when a part is selected from the parts list. Respond
     * to the part selection in this function.
     *
     * @param index The list index of the selected part.
     */
    @Override
    public void onPartSelected(int index) {
        if(isInitialized) {
            switch (mCurrentPartView) {
                case MAIN_BODY:
                    MainBody tempMainBody = MainBodiesDAO.getMainBody(index+1);
                    tempMainBody.setID(mMainBodies.get(index));
                    Spaceship.setMainBody(tempMainBody);
                    break;
                case EXTRA_PART:
                    ExtraPart tempExtraPart = ExtraPartsDAO.getExtraPart(index+1);
                    tempExtraPart.setID(mExtraParts.get(index));
                    Spaceship.setExtraPart(tempExtraPart);
                    break;
                case CANNON:
                    Cannon tempCannon = CannonsDAO.getCannon(index+1);
                    tempCannon.setID(mCannons.get(index));
                    Spaceship.setCannon(tempCannon);
                    break;
                case POWER_CORE:
                    PowerCore tempPowerCore = PowerCoresDAO.getPowerCore(index+1);
                    tempPowerCore.setID(mPowerCores.get(index));
                    Spaceship.setPowerCore(tempPowerCore);
                    break;
                case ENGINE:
                    Engine tempEngine = EnginesDAO.getEngine(index+1);
                    tempEngine.setID(mEngines.get(index));
                    Spaceship.setEngine(tempEngine);
                    break;
                default:
                    break;
            }
        }
        if(Spaceship.isComplete()) {
            mView.setStartGameButton(true);
        }
    }

    /**
     * The ShipBuildingView calls this function is called when the start game button is pressed.
     */
    @Override
    public void onStartGamePressed() {
        if(isInitialized && Spaceship.isComplete()) {
            LevelController.startingNewGame();
            mView.startGame();
        }
    }

    /**
     * The ShipBuildingView calls this function when ship building has resumed. Reset the Camera and
     * the ship position as needed when this is called.
     */
    @Override
    public void onResume() {
        //Do Nothing
    }

    /**
     * Sets the controller's view
     *
     * @param view the view to set
     */
    @Override
    public void setView(IView view) {
        //Do Nothing
    }

    /**
     * Updates the game delegate. The game engine will call this function 60 times a second
     * once it enters the game loop.
     *
     * @param elapsedTime Time since the last update. For this game, elapsedTime is always
     *                    1/60th of second
     */
    @Override
    public void update(double elapsedTime) {
        //Do Nothing
    }
//endregion COMMANDS





}
