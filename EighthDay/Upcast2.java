import java.awt.Graphics;
import javax.swing.JFrame;

public class Upcast2 extends JFrame {

  public Upcast2() {
    setTitle("Painter");
    setSize(400, 300);
    setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void paint(Graphics g) {
    Face pacman1 = new PacMan(100, 100, 150, 60);
    pacman1.make(g);
    pacman1.showData();
    System.out.println(pacman1.special);
    Face pacman2 = new PacManWithEye(100, 300, 150, 60);
    pacman2.make(g);
    pacman2.showData();
    System.out.println(pacman2.special);
  }

  public static void main(String[] args) {
    new Upcast2();
  }
}