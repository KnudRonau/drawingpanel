package se.miun.dt176g.xxxxyyyy.reactive;


import javafx.scene.canvas.GraphicsContext;

/**
 * <h1>Shape</h1> Abstract class which derived classes builds on.
 * <p>
 * This class consists of the attributes common to all geometric shapes.
 * Specific shapes are based on this class.
 * 
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-09-08
 */

public abstract class Shape implements Drawable{

    protected Point firstPoint;
    protected Point secondPoint;
    protected GraphicsContext graphicsContext;

    public Shape(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public Shape(Point firstPoint, Point secondPoint, GraphicsContext graphicsContext) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.graphicsContext = graphicsContext;
    }

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

    public abstract void draw();
	
}
