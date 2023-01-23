package se.miun.dt176g.xxxxyyyy.reactive;


import java.awt.Graphics;
import java.util.ArrayList;


/**
 * <h1>Drawing</h1> 
 * Let this class store an arbitrary number of AbstractShape-objects in
 * some kind of container. 
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-09-08
 */


public class Drawings {
//	private DrawingPanel drawingPanel;
	private ArrayList<Shape> drawingsList;

	public Drawings() {
		drawingsList = new ArrayList<>();
	}

	// private SomeContainer shapes;

	
	/**
	 * <h1>addShape</h1> add a shape to the "SomeContainer shapes"
	 * 
	 * @param s a {@link Shape} object.
	 */
	public void addShape(Shape s) {
		drawingsList.add(s);
	}

	public void emptyDrawings() {
		drawingsList.clear();
	}

	public void draw() {
		for (Shape s : drawingsList) { s.draw();
		}
		// iterate over all shapes and draw them using the draw-method found in
		// each concrete subclass.
	}

}
