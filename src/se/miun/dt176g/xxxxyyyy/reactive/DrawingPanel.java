package se.miun.dt176g.xxxxyyyy.reactive;

import io.reactivex.rxjava3.core.Observable;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.*;

/**
 * <h1>DrawingPanel</h1> Creates a Canvas-object for displaying all graphics
 * already drawn.
 *
 * @author 	--Knud Ronau Larsen--
 * @version 1.0
 * @since 	2022-09-08
 */

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel {

//	private Drawings drawings;
	private int startX;
	private int startY;
	private Observable<MouseEvent> mouseEventObservable;


	public DrawingPanel() {
//		drawings = new Drawings();
		setBackground(Color.gray);

		mouseEventObservable = Observable.create( emitter -> {
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					emitter.onNext(e);
				}
			});
			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					emitter.onNext(e);
				}
			});
		});

	}

	public Observable<MouseEvent> getMouseEventObservable() {
		return mouseEventObservable;
	}

	public void redraw() {
		repaint();
	}

	public void setDrawing(Drawings d) {
//		drawings = d;
		repaint();
	}

//	public Drawings getDrawing() {
//		return drawings;
//	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
//		drawings.draw(g);
	}

}
