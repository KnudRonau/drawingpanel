package se.miun.dt176g.xxxxyyyy.reactive.shapes;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * <h1>Shape</h1> Abstract class which derived classes build on.
 *
 * This class consists of the attribute common to all shapes, GraphicsContext.
 * 
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-09-08
 */

public abstract class Shape implements Drawable, Serializable {

    //protected GraphicsContext graphicsContext;
    protected Double lineWidth;
    protected Boolean isBlack;

    /**
     * Constructor used by all Shapes.
     * @param graphicsContext used for drawing attributes.
     */
    public Shape(GraphicsContext graphicsContext) {
        lineWidth = graphicsContext.getLineWidth();
        if(graphicsContext.getStroke().equals(Color.BLACK)) {
            isBlack = true;
        } else if(graphicsContext.getStroke().equals(Color.WHITE)) {
            isBlack = false;
        }

    }

    protected GraphicsContext createGraphicsContext(GraphicsContext graphicsContext) {
        graphicsContext.setLineWidth(this.lineWidth);
        if(isBlack) {
            graphicsContext.setStroke(Color.BLACK);
        } else {
            graphicsContext.setStroke(Color.WHITE);
        }
        return graphicsContext;
    }

    /**
     * Abstract method to draw the concrete shape in a GUI.
     */
    public abstract void draw(GraphicsContext graphicsContext);

	
}
