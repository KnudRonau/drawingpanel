package se.miun.dt176g.xxxxyyyy.reactive;

import javax.swing.SwingUtilities;


/**
* <h1>AppStart</h1>
*
* @author  --Knud Ronau Larsen--
* @version 1.0
* @since   2022-09-08
*/
public class AppStart {

	public static void main(String[] args) {
		MainApplication.launch();
		// Make sure GUI is created on the event dispatching thread
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				new MainFrame().setVisible(true);
//			}
//		});
	}
}