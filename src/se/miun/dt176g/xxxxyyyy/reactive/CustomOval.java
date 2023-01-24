package se.miun.dt176g.xxxxyyyy.reactive;

import javafx.scene.canvas.GraphicsContext;

public class CustomOval extends Shape{

    public CustomOval(Point firstPoint, Point secondPoint, GraphicsContext graphicsContext) {
        super(firstPoint, secondPoint, graphicsContext);
    }

    @Override
    public void draw() {
        fixCoordsForDrawing();
        graphicsContext.strokeOval(firstPoint.x(), firstPoint.y(),
                secondPoint.x() - firstPoint.x(), secondPoint.y() - firstPoint.y());
    }
}
