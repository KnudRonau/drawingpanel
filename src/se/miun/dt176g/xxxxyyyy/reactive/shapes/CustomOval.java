package se.miun.dt176g.xxxxyyyy.reactive.shapes;

import javafx.scene.canvas.GraphicsContext;

/**
 * <h1>CustomOval</h1> Concrete implementation of {@link  TwoPointShape} representing an oval.
 * <p>
 * Uses the TwoPointShape's constructor for object creation.
 * Implements the draw() method to draw an oval.
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-01-20
 */
public class CustomOval extends TwoPointShape{

    /**
     * Constructor with all necessary attributes to draw an oval.
     * @param firstPoint starting coords for the shape.
     * @param secondPoint ending coords for the shape.
     * @param graphicsContext used for drawing attributes.
     */
    public CustomOval(Point firstPoint, Point secondPoint, GraphicsContext graphicsContext) {
        super(firstPoint, secondPoint, graphicsContext);
    }

    /**
     * Implementation of draw using strokeOval
     * Calls fixCoordsForDrawing() first as Point values could otherwise end up negative.
     */
    @Override
    public void draw(GraphicsContext graphicsContext) {
        fixCoordsForDrawing();
        createGraphicsContext(graphicsContext).strokeOval(firstPoint.x(), firstPoint.y(),
                secondPoint.x() - firstPoint.x(), secondPoint.y() - firstPoint.y());
    }
}
