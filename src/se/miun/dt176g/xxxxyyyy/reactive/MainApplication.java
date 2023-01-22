package se.miun.dt176g.xxxxyyyy.reactive;

//import io.reactivex.rxjava3.core.Observable;
//import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjava3.core.Observable;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.pdfsam.rxjavafx.observables.JavaFxObservable;

import java.awt.*;
import java.util.PrimitiveIterator;


public class MainApplication extends Application {
    private Canvas canvas;
    private GraphicsContext gc;
    private CustomLine customLine;
    private CustomRectangle customRectangle;
    private CustomOval customOval;
    private Freehand freehand;
    private ToggleButton lineButton;
    private ToggleButton rectangleButton;
    private ToggleButton ovalButton;
    private ToggleButton freehandButton;



    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Drawing Area");

        canvas = new Canvas(1200, 800);
        gc = canvas.getGraphicsContext2D();
        BorderPane root = new BorderPane();

        root.setCenter(canvas);
        root.setTop(addButtons());

        Observable<MouseEvent> mousePressed = JavaFxObservable.eventsOf(canvas, MouseEvent.MOUSE_PRESSED);
        Observable<MouseEvent> mouseDragged = JavaFxObservable.eventsOf(canvas, MouseEvent.MOUSE_DRAGGED);
        Observable<MouseEvent> mouseReleased = JavaFxObservable.eventsOf(canvas, MouseEvent.MOUSE_RELEASED);

        Observable.merge(mousePressed, mouseReleased, mouseDragged)
                .filter(e -> lineButton.isSelected())
                .subscribe(this::line);

        Observable.merge(mousePressed, mouseReleased, mouseDragged)
                .filter(e -> rectangleButton.isSelected())
                .subscribe(this::rectangle);

        Observable.merge(mousePressed, mouseReleased, mouseDragged)
                .filter(e -> ovalButton.isSelected())
                .subscribe(this::oval);

        Observable.merge(mousePressed, mouseReleased, mouseDragged)
                .filter(e -> freehandButton.isSelected())
                .subscribe(this::freehand);


//        mouseDragged.subscribe(s -> System.out.println(s.getX() + " " + s.getY()));
        mousePressed.subscribe(s -> System.out.println(s.getX()+ " " + s.getY()));
        mouseReleased.subscribe(s -> System.out.println(s.getX() + " " + s.getY()));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void freehand(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            freehand = new Freehand(gc);
            freehand.addPoint(makeToPoint(mouseEvent));
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_DRAGGED)) {
            freehand.addPoint(makeToPoint(mouseEvent));
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            freehand.addPoint(makeToPoint(mouseEvent));
            freehand.draw();
        }
    }

    private Point makeToPoint(MouseEvent mouseEvent) {
        return new Point((int)mouseEvent.getX(), (int)mouseEvent.getY());
    }

    private void line(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            customLine = new CustomLine(gc);
            customLine.setFirstCoords(mouseEvent.getX(), mouseEvent.getY());
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            customLine.setSecondCoords(mouseEvent.getX(), mouseEvent.getY());
            customLine.draw();
        }
    }

    private void rectangle(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            customRectangle = new CustomRectangle(gc);
            customRectangle.setFirstCoords(mouseEvent.getX(), mouseEvent.getY());
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            customRectangle.setSecondCoords(mouseEvent.getX(), mouseEvent.getY());
            customRectangle.draw();
        }
    }

    private void oval(MouseEvent mouseEvent) {
        if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            customOval = new CustomOval(gc);
            customOval.setFirstCoords(mouseEvent.getX(), mouseEvent.getY());
        } else if(mouseEvent.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
            customOval.setSecondCoords(mouseEvent.getX(), mouseEvent.getY());
            customOval.draw();
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
        clearEvent.subscribe(event -> gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()));

        buttonBox.getChildren().addAll(rectangleButton, ovalButton, lineButton,
                freehandButton, colorButton, thicknessButton, clearButton);

        return buttonBox;
    }
}
