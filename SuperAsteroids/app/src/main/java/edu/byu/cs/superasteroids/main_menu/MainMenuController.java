package edu.byu.cs.superasteroids.main_menu;

import android.graphics.PointF;

import edu.byu.cs.superasteroids.base.IView;
import edu.byu.cs.superasteroids.models.LevelController;
import edu.byu.cs.superasteroids.models.Spaceship;

/**
 * Created by User on 7/17/2016.
 */
public class MainMenuController implements IMainMenuController {

    private MainActivity mView;

    /**
     * Basic Constructor.
     * @param view
     */
    public MainMenuController(MainActivity view) {
        mView = view;
    }

    /**
     * The MainActivity calls this function when the "quick play" button is pressed.
     */
    @Override
    public void onQuickPlayPressed() {
        Spaceship.randomShip();
        Spaceship.setSpaceshipRotation(0);
        Spaceship.setSpaceshipCoordinates(new PointF(1500,1500));
        LevelController.startingNewGame();
        mView.startGame();
    }

    /**
     * Gets the controller's view
     *
     * @return the controller's view
     */
    @Override
    public IView getView() {
        return null;
    }

    /**
     * Sets the controller's view
     *
     * @param view the view to set
     */
    @Override
    public void setView(IView view) {

    }
}
