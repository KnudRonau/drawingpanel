package se.miun.dt176g.xxxxyyyy.reactive;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class Freehand  extends Shape {
    private ArrayList<Point> dots;

    Freehand(ArrayList<Point> dots, GraphicsContext graphicsContext) {
        super(graphicsContext);
        this.dots = dots;
    }

             @Override
    public void draw() {
        for(int i = 0; i + 1 < dots.size(); i++) {
            graphicsContext.strokeLine(dots.get(i).x(), dots.get(i).y(),dots.get(i+1).x(),dots.get(i+1).y());
        }
    }
}
