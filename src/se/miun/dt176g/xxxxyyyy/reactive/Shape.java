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

public abstract class Shape {

    protected double x1;
    protected double y1;
    protected double x2;
    protected double y2;
    protected GraphicsContext graphicsContext;

    public Shape() {

    }

    public Shape(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public Shape(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
//        this.graphicsContext = graphicsContext;
    }

    public abstract void draw();
	
}
