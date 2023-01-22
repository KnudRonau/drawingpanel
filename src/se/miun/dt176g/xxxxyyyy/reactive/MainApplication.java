package se.miun.dt176g.xxxxyyyy.reactive;

//import io.reactivex.rxjava3.core.Observable;
//import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjava3.core.Observable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.pdfsam.rxjavafx.observables.JavaFxObservable;

import java.util.PrimitiveIterator;


public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        Canvas canvas = new Canvas(1200, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        BorderPane root = new BorderPane();

        HBox buttonBox = new HBox();

        ToggleGroup shapeButtons = new ToggleGroup();

        ToggleButton rectangleButton = new ToggleButton("Rectangle");
        rectangleButton.setToggleGroup(shapeButtons);
        ToggleButton ovalButton = new ToggleButton("Oval");
        ovalButton.setToggleGroup(shapeButtons);
        ToggleButton lineButton = new ToggleButton("Line");
        lineButton.setToggleGroup(shapeButtons);
        ToggleButton freehandButton = new ToggleButton("Freehand");
        freehandButton.setToggleGroup(shapeButtons);

        Button colorButton = new Button("Change color!");
        Button thicknessButton = new Button("Change thickness!");
        Button clearButton = new Button("Clear canvas!");

        buttonBox.getChildren().addAll(rectangleButton, ovalButton, lineButton,
                freehandButton, colorButton, thicknessButton, clearButton);

        root.setCenter(canvas);
        root.setTop(buttonBox);


        Observable<MouseEvent> mousePressed = JavaFxObservable.eventsOf(canvas, MouseEvent.MOUSE_PRESSED);
        Observable<MouseEvent> mouseDragged = JavaFxObservable.eventsOf(canvas, MouseEvent.MOUSE_DRAGGED);
        Observable<MouseEvent> mouseReleased = JavaFxObservable.eventsOf(canvas, MouseEvent.MOUSE_RELEASED);

        mouseDragged.subscribe(s -> System.out.println(s.getX() + " " + s.getY()));


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



    }
}
