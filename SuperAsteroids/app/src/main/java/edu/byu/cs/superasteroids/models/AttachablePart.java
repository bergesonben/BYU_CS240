package edu.byu.cs.superasteroids.models;

import android.graphics.PointF;

/**
 * Created by bergeson on 7/7/2016.
 */
abstract class AttachablePart extends ImageObject {

    //TODO: remove
    //private PointF attachPoint;

    /**
     * Returns the attach point.
     * @return PointF representing the attach point.
     */
    abstract PointF getAttachPoint();

    /**
     * Sets the attach point.
     * @param attachPoint PointF representing the attach point.
     * @return true if successful, false if not.
     */
    abstract void setAttachPoint(PointF attachPoint);
}
