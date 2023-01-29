package se.miun.dt176g.xxxxyyyy.reactive.shapes;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

/**
 * <h1>CustomRectangle</h1> Concrete implementation of {@link  Shape} representing a free hand drawing.
 *
 * Uses the Shape-class' constructor for object creation.
 * Implements the draw() method to draw the free-handed drawing.
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-01-20
 */
public class Freehand extends Shape {
    private ArrayList<Point> dots;

    /**
     * Constructor with all necessary attributes to draw a free-handed drawing.
     * @param dots ArrayList containing all the drawing's points.
     * @param graphicsContext used for drawing attributes.
     */
    public Freehand(ArrayList<Point> dots, GraphicsContext graphicsContext) {
        super(graphicsContext);
        this.dots = dots;
    }

    /**
     * Draws a free-handed drawing by connecting all the points with a straight line.
     */
    @Override
    public void draw(GraphicsContext graphicsContext) {
        for(int i = 0; i + 1 < dots.size(); i++) {
            createGraphicsContext(graphicsContext).strokeLine(dots.get(i).x(), dots.get(i).y(),dots.get(i+1).x(),dots.get(i+1).y());
        }
    }
}
