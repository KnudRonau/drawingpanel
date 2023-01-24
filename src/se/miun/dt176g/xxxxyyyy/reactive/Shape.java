package se.miun.dt176g.xxxxyyyy.reactive;


import javafx.scene.canvas.GraphicsContext;

/**
 * <h1>Shape</h1> Abstract class which derived classes build on.
 *
 * This class consists of the attribute common to all shapes, GraphicsContext.
 * 
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-09-08
 */

public abstract class Shape implements Drawable{

    protected GraphicsContext graphicsContext;

    /**
     * Constructor used by all Shapes.
     * @param graphicsContext used for drawing attributes.
     */
    public Shape(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    /**
     * Abstract method to draw the concrete shape in a GUI.
     */
    public abstract void draw();
	
}
