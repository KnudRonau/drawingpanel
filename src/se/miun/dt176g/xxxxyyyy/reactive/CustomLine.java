package se.miun.dt176g.xxxxyyyy.reactive;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Line;

import java.awt.*;

public class CustomLine extends Shape {

    public CustomLine() {

    }

    public CustomLine(GraphicsContext graphicsContext) {
        super(graphicsContext);
    }

    public CustomLine(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2, y2);
    }

    public void setFirstCoords(double x1, double y1) {
        this.x1 = x1;
        this.y1 = y1;
    }

    public void setSecondCoords(double x2, double y2) {
        this.x2 = x2;
        this.y2 = y2;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }
    public void setY2(double x2) {
        this.y2 = y2;
    }

    @Override
    public void draw() {
        graphicsContext.strokeLine(x1, y1, x2, y2);
    }
}
