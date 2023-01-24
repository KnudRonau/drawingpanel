package se.miun.dt176g.xxxxyyyy.reactive;

import javafx.scene.canvas.GraphicsContext;

public class CustomRectangle extends Shape{

    public CustomRectangle(Point firstPoint, Point secondPoint, GraphicsContext graphicsContext) {
        super(firstPoint, secondPoint, graphicsContext);
    }

    @Override
    public void draw() {
        fixCoordsForDrawing();
        graphicsContext.strokeRect(firstPoint.x(), firstPoint.y(),
                secondPoint.x() - firstPoint.x(), secondPoint.y() - firstPoint.y());
    }
}
