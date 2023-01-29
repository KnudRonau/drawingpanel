package se.miun.dt176g.xxxxyyyy.reactive;

import javafx.scene.canvas.GraphicsContext;
import se.miun.dt176g.xxxxyyyy.reactive.shapes.Shape;

import java.util.ArrayList;

/**
 * <h1>Drawings</h1>
 * Class acting as container for stored {@link  Shape} objects.
 * Consist of an ArrayList storing shapes and manipulate the list and draw all the shapes.
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-09-08
 */

public class Drawings {
	private GraphicsContext graphicsContext;
	private ArrayList<Shape> drawingsList;

	/**
	 * Constructor initializing the ArrayList.
	 */
	public Drawings(GraphicsContext graphicsContext) {
		this.graphicsContext = graphicsContext;
		drawingsList = new ArrayList<>();
	}

	/**
	 * <h1>addShape</h1> add a shape to the "SomeContainer shapes"
	 * Calls draw() afterwards.
	 *
	 * @param s a {@link Shape} object.
	 */
	public void addShape(Shape s) {
		drawingsList.add(s);
		draw(graphicsContext);
	}

	/**
	 * Empties the ArrayList
	 */
	public void emptyDrawings() {
		drawingsList.clear();
	}

	/**
	 * Traverses the ArrayList and draws all the objects.
	 */
	public void draw(GraphicsContext graphicsContext) {
		for (Shape s : drawingsList) {
			s.draw(graphicsContext);
		}
	}
}
