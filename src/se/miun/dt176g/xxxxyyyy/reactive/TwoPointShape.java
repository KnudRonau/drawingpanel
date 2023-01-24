package se.miun.dt176g.xxxxyyyy.reactive;

import javafx.scene.canvas.GraphicsContext;

/**
 * <h1>TwoPointShape</h1> Abstract class extending {@link Shape} which shapes created from two points build on.
 *
 * Consists of attributes specific to shapes created from two points.
 * These shapes are based on this class
 * Extends Shape.
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-01-24
 */
public abstract class TwoPointShape extends Shape{

    protected Point firstPoint;
    protected Point secondPoint;

    /**
     * Constructor to create any shape based on exactly two points.
     * @param firstPoint starting coords for the shape.
     * @param secondPoint ending coords for the shape.
     * @param graphicsContext contains drawing attributes.
     */
    public TwoPointShape(Point firstPoint, Point secondPoint, GraphicsContext graphicsContext) {
        super(graphicsContext);
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    /**
     * Method to switch values for some shapes when drawing bottom right to top left.
     */
    protected void fixCoordsForDrawing() {
        if (secondPoint.x() < firstPoint.x()) {
            int temp = firstPoint.x();
            firstPoint.x(secondPoint.x());
            secondPoint.x(temp);
        }
        if (secondPoint.y() < firstPoint.y()) {
            int temp = firstPoint.y();
            firstPoint.y(secondPoint.y());
            secondPoint.y(temp);
        }
    }

    /**
     * Abstract method to draw the concrete shape in a GUI.
     */
    @Override
    public abstract void draw();
}
