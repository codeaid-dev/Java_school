/* comment */

import java.awt.Graphics;
import javax.swing.JFrame;

public class Painter12 extends JFrame {
	
	public Painter12() {
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void paint(Graphics g) {
		PacManWithSmile pacman1 = new PacManWithSmile(20, 80, 400, 160);
		pacman1.setSize(200);
		pacman1.make(g);
		PacManWithSmile pacman2 = new PacManWithSmile(20, 400, 90, 160);
		pacman2.setSize(200);
		pacman2.make(g);
	}

	public static void main(String[] args) {
		new Painter12();
	}
}
