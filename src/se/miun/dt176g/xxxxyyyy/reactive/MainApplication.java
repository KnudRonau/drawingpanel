package se.miun.dt176g.xxxxyyyy.reactive;

import io.reactivex.rxjava3.core.Observable;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.pdfsam.rxjavafx.observables.JavaFxObservable;

import java.util.ArrayList;

public class MainApplication extends Application {
    private Canvas canvas;
    private ToggleButton lineButton;
    private ToggleButton rectangleButton;
    private ToggleButton ovalButton;
    private ToggleButton freehandButton;
    private GraphicsContext gc;
    private Drawings drawings;
    private Point firstPoint;
    private ArrayList<Point> dots;


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Drawing Area");

        canvas = new Canvas(1200, 800);
        gc = canvas.getGraphicsContext2D();
        drawings = new Drawings();
        BorderPane root = new BorderPane();

        root.setCenter(canvas);
        root.setTop(addButtons());

        Observable<MouseEvent> mousePressed = JavaFxObservable.eventsOf(canvas, MouseEvent.MOUSE_PRESSED);
        Observable<MouseEvent> mouseDragged = JavaFxObservable.eventsOf(canvas, MouseEvent.MOUSE_DRAGGED);
        Observable<MouseEvent> mouseReleased = JavaFxObservable.eventsOf(canvas, MouseEvent.MOUSE_RELEASED);

        Observable.merge(mousePressed, mouseReleased, mouseDragged)
                .subscribe(e -> {
                    if(lineButton.isSelected()) {
                        line(e);
                    } else if(rectangleButton.isSelected()) {
                        rectangle(e);
                    } else if(ovalButton.isSelected()) {
                        oval(e);
                    } else if(freehandButton.isSelected()) {
                        freehand(e);
                    }
                });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void freehand(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            dots = new ArrayList<>();
            dots.add(makeToPoint(mouseEvent));
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            dots.add(makeToPoint(mouseEvent));
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            dots.add(makeToPoint(mouseEvent));
            drawings.addShape(new Freehand(dots, gc));
        }
    }

    private Point makeToPoint(MouseEvent mouseEvent) {
        return new Point((int)mouseEvent.getX(), (int)mouseEvent.getY());
    }

    private void line(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            firstPoint = makeToPoint(mouseEvent);
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            drawings.addShape(new CustomLine(firstPoint, makeToPoint(mouseEvent), gc));
        }
    }

    private void rectangle(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            firstPoint = makeToPoint(mouseEvent);
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            drawings.addShape(new CustomRectangle(firstPoint, makeToPoint(mouseEvent), gc));
        }
    }

    private void oval(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            firstPoint = makeToPoint(mouseEvent);
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            drawings.addShape(new CustomOval(firstPoint, makeToPoint(mouseEvent), gc));
        }
    }

    private HBox addButtons() {

        HBox buttonBox = new HBox();
        ToggleGroup shapeButtons = new ToggleGroup();

        rectangleButton = new ToggleButton("Rectangle");
        rectangleButton.setToggleGroup(shapeButtons);
        rectangleButton.setSelected(true);
        ovalButton = new ToggleButton("Oval");
        ovalButton.setToggleGroup(shapeButtons);
        lineButton = new ToggleButton("Line");
        lineButton.setToggleGroup(shapeButtons);
        freehandButton = new ToggleButton("Freehand");
        freehandButton.setToggleGroup(shapeButtons);

        Button colorButton = new Button("Change color!");
        Observable<ActionEvent> colorEvent = JavaFxObservable.actionEventsOf(colorButton);
        colorEvent.subscribe(e -> {
            if(gc.getStroke().equals(Color.BLACK)) {
                gc.setStroke(Color.WHITE);
            } else {
                gc.setStroke(Color.BLACK);
            }
        });

        Button thicknessButton = new Button("Change thickness!");
        Observable<ActionEvent> thicknessEvent = JavaFxObservable.actionEventsOf(thicknessButton);
        thicknessEvent.subscribe(event -> {
            TextInputDialog thicknessDialog = new TextInputDialog();
            thicknessDialog.setTitle("Input the desired thickness!");
            try {
                thicknessDialog.showAndWait().ifPresent(thickness -> gc.setLineWidth(Double.parseDouble(thickness)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        });

        Button clearButton = new Button("Clear canvas!");
        Observable<ActionEvent> clearEvent = JavaFxObservable.actionEventsOf(clearButton);
        clearEvent.subscribe(event -> {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            drawings.emptyDrawings();
        });

        buttonBox.getChildren().addAll(rectangleButton, ovalButton, lineButton,
                freehandButton, colorButton, thicknessButton, clearButton);

        return buttonBox;
    }
}
