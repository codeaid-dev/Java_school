/* comment */

import java.awt.Graphics;

import javax.swing.JFrame;

public class Painter11 extends JFrame {

	public Painter11() {
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void paint(Graphics g) {
		PacManWithEye pacman1 = new PacManWithEye(300, 100, 100, 160);
		pacman1.make(g);
		PacManWithEye pacman2 = new PacManWithEye(200, 350, 430, 60);
		pacman2.make(g);
	}

	public static void main(String[] args) {
		new Painter11();
	}
}
