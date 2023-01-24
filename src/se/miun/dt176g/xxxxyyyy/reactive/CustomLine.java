package se.miun.dt176g.xxxxyyyy.reactive;

import javafx.scene.canvas.GraphicsContext;

public class CustomLine extends Shape {

    public CustomLine(Point firstPoint, Point secondPoint, GraphicsContext graphicsContext) {
        super(firstPoint, secondPoint, graphicsContext);
    }

    @Override
    public void draw() {
        graphicsContext.strokeLine(firstPoint.x(), firstPoint.y(), secondPoint.x(), secondPoint.y());
    }
}
