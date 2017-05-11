package edu.byu.cs.superasteroids.models;

/**
 * Created by bergeson on 7/7/2016.
 */
abstract class ImageObject {

    //TODO: remove
//    private String imagePath;
//    private int imageWidth;
//    private int imageHeight;

    /**
     * Returns the String representing the image path.
     * @return String representing the image path.
     */
    abstract String getImagePath();

    /**
     * Returns the image width.
     * @return int representing the image width.
     */
    abstract  int getImageWidth();

    /**
     * Returns the image height.
     * @return int representing the image height.
     */
    abstract  int getImageHeight();

    /**
     * Sets the path for the image.
     * @param imagePath String representing the path for the image.
     * @return true if successful, false if not.
     */
    abstract  void setImagePath(String imagePath);

    /**
     * Sets the width for the image.
     * @param imageWidth int representing the width for the image.
     * @return true if successful, false if not.
     */
    abstract  void setImageWidth(int imageWidth);

    /**
     * Sets the height for the image.
     * @param imageHeight int representing the height for the image.
     * @return true if successful, false if not.
     */
    abstract  void setImageHeight(int imageHeight);
}
