package se.miun.dt176g.xxxxyyyy.reactive.shapes;

import javafx.scene.canvas.GraphicsContext;

/**
 * <h1>Drawable</h1> Interface implemented by {@link Shape}.
 *
 * 
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-09-08
 */

@FunctionalInterface
interface Drawable {
	void draw(GraphicsContext graphicsContext);
}
