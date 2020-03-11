import java.awt.Graphics;
import javax.swing.JFrame;

public class Upcast1 extends JFrame {

  public Upcast1() {
    setTitle("Painter");
    setSize(400, 300);
    setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void paint(Graphics g) {
    Face pacman1 = new PacMan(100, 100, 150, 60);
    pacman1.make(g);
    Face pacman2 = new PacManWithEye(100, 300, 150, 60);
    pacman2.make(g);
  }

  public static void main(String[] args) {
    new Upcast1();
  }
}