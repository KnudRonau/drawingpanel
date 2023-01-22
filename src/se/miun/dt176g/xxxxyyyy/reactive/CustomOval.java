package se.miun.dt176g.xxxxyyyy.reactive;

import javafx.scene.canvas.GraphicsContext;

public class CustomOval extends Shape{

    public CustomOval(GraphicsContext graphicsContext) {
        super(graphicsContext);
    }

    public void setFirstCoords(double x1, double y1) {
        this.x1 = x1;
        this.y1 = y1;
    }

    public void setSecondCoords(double x2, double y2) {
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw() {
        if(x2 < x1) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if(y2 < y1) {
            double temp = y1;
            y1 = y2;
            y2 = temp;
        }
        graphicsContext.strokeOval(x1, y1,x2-x1, y2-y1);
    }
}
