import java.awt.Graphics;
import javax.swing.JFrame;

public class Painter extends JFrame {

  public Painter() {
    setSize(400, 300);
    setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void paint(Graphics g) {
    PacMan pacman1 = new PacManWithEye(100, 80, 150, 60);
    pacman1.make(g);
    PacMan pacman2 = new PacManWithEye(100, 200, 150, 60);
    pacman2.make(g);
    PacMan pacman3 = new PacManWithSmile(100, 320, 150, 60);
    pacman3.make(g);
  }

  public static void main(String[] args) {
    new Painter();
  }
}