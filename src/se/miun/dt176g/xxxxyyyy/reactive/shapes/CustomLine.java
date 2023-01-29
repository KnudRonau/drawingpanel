package se.miun.dt176g.xxxxyyyy.reactive.shapes;

import javafx.scene.canvas.GraphicsContext;

/**
 * <h1>CustomLine</h1> Concrete implementation of {@link  TwoPointShape} representing a straight line.
 *
 * Uses the TwoPointShape's constructor for object creation.
 * Implements the draw() method to draw a Line.
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-01-20
 */
public class CustomLine extends TwoPointShape {

    /**
     * Constructor with all necessary attributes to draw a Line.
     * @param firstPoint starting coords for the shape.
     * @param secondPoint ending coords for the shape.
     * @param graphicsContext used for drawing attributes.
     */
    public CustomLine(Point firstPoint, Point secondPoint, GraphicsContext graphicsContext) {
        super(firstPoint, secondPoint, graphicsContext);
    }

    /**
     * Implementation of draw using strokeLine to draw the line.
     */
    @Override
    public void draw(GraphicsContext graphicsContext) {
        createGraphicsContext(graphicsContext).strokeLine(firstPoint.x(), firstPoint.y(), secondPoint.x(), secondPoint.y());
    }
}
