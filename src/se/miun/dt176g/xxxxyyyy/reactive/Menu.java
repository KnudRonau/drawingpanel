package se.miun.dt176g.xxxxyyyy.reactive;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.*;


/**
 * <h1>Menu</h1>
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-09-08
 */
public class Menu extends JMenuBar {

	private static final long serialVersionUID = 1L;

	enum Shape{LINE, RECTANGLE, OVAL, FREEHAND}
	private static Color color;
	private static Shape currentShape;
	private static int thickness;


	public Menu(MainFrame frame) {
		init(frame);
	}

	private void init(MainFrame frame) {

		JMenu menuAttributes;
		JMenuItem menuItemAttributes;
		JMenu menuShapes;
		JMenuItem menuItemShapes;
		JMenu menuClear;
		JMenuItem menuItemClear;

		menuAttributes = new JMenu("Change Attributes");
		menuShapes = new JMenu("Change Shape");
		menuClear = new JMenu("Clear canvas");
		this.add(menuAttributes);
		this.add(menuShapes);
		this.add(menuClear);

		menuItemAttributes = new JMenuItem("Change thickness");
		menuItemAttributes.addActionListener(e -> thicknessEvent(frame));
		menuAttributes.add(menuItemAttributes);

		menuItemAttributes = new JMenuItem("Change color");
		menuItemAttributes.addActionListener(e ->  colorEvent());
		menuAttributes.add(menuItemAttributes);

		menuItemShapes = new JMenuItem("Rectangle");
		menuItemShapes.addActionListener(e -> shapeEvent(Shape.RECTANGLE));
		menuShapes.add(menuItemShapes);

		menuItemShapes = new JMenuItem("Oval");
		menuItemShapes.addActionListener(e -> shapeEvent(Shape.OVAL));
		menuShapes.add(menuItemShapes);

		menuItemShapes = new JMenuItem("Straight Line");
		menuItemShapes.addActionListener(e -> shapeEvent(Shape.LINE));
		menuShapes.add(menuItemShapes);

		menuItemShapes = new JMenuItem("Free Hand");
		menuItemShapes.addActionListener(e -> shapeEvent(Shape.FREEHAND));
		menuShapes.add(menuItemShapes);

		menuItemClear = new JMenuItem("Clear!");
		menuItemClear.addActionListener(e -> clearEvent());
		menuClear.add(menuItemClear);

		color = Color.black;
		thickness = 5;
		currentShape = Shape.RECTANGLE;
	}

	private void clearEvent() {
	}

	private void shapeEvent(Shape shape) {
		currentShape = shape;
		System.out.println(currentShape);
	}

	private void colorEvent() {
		if(color.equals(Color.black)) {
			color = Color.white;
		} else {
			color = Color.black;
		}
	}

	private void thicknessEvent(MainFrame frame) {
		try {
			thickness  = Integer.parseInt(JOptionPane.showInputDialog(frame, "Choose a thickness"));
			System.out.println(thickness);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private void anEvent(MainFrame frame) {

		String message = (String) JOptionPane.showInputDialog(frame,
				"Send message to everyone:");

		if(message != null && !message.isEmpty()) {
			JOptionPane.showMessageDialog(frame, message);
		}
	}

	private void anotherEvent(MainFrame frame) {

	}

}
