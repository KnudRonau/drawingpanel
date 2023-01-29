package se.miun.dt176g.xxxxyyyy.reactive.shapes;

import javafx.scene.canvas.GraphicsContext;

/**
 * <h1>CustomRectangle</h1> Concrete implementation of {@link  TwoPointShape} representing a rectangle.
 * <p>
 * Uses the TwoPointShape's constructor for object creation.
 * Implements the draw() method to draw a rectangle.
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-01-20
 */
public class CustomRectangle extends TwoPointShape{

    /**
     * Constructor with all necessary attributes to draw a rectangle.
     * @param firstPoint starting coords for the shape.
     * @param secondPoint ending coords for the shape.
     * @param graphicsContext used for drawing attributes.
     */
    public CustomRectangle(Point firstPoint, Point secondPoint, GraphicsContext graphicsContext) {
        super(firstPoint, secondPoint, graphicsContext);
    }

    /**
     * Implementation of draw using strokeRectangle
     * Calls fixCoordsForDrawing() first as Point values could otherwise end up negative.
     */
    @Override
    public void draw(GraphicsContext graphicsContext) {
        fixCoordsForDrawing();
        createGraphicsContext(graphicsContext).strokeRect(firstPoint.x(), firstPoint.y(),
                secondPoint.x() - firstPoint.x(), secondPoint.y() - firstPoint.y());
    }
}
