import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PainterOrg extends JFrame {
  Face face[] = new Face[10];
  int count = -1; // the number of Pac-Mac
  int flag = 0; // 0: PacMan, 1: PacManWithEye

  JButton button1;
  JTextField text;

  public PainterOrg() {
    setTitle("Painter");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    MyListener myListener = new MyListener();
    addMouseListener(myListener);
    addMouseMotionListener(myListener);

    button1 = new JButton();
    button1.addMouseListener(new ButtonListener1());
    button1.setForeground(Color.red);
    text = new JTextField();
    text.setColumns(16);
    JPanel p = new JPanel();
    p.add(button1);
    p.add(text);
    getContentPane().add(p, BorderLayout.SOUTH);

    button1.setText("PacMan");
    text.setText("Click the center point");
    setVisible(true);
  }

  public void paint(Graphics g) {
    super.paint(g);
    g.clearRect(0, 0, 500, 450);
    if (count >= 0) {
      if (count > 9) {
        count = 9;
      }
      for (int i = 0; i < count + 1; i++) {
        face[i].make(g);
      }
    }
  }

  public static void main(String[] args) {
    new PainterOrg();
  }

  class MyListener extends MouseAdapter {
    int distance, xCenter, yCenter;

    public void mousePressed(MouseEvent e) {
      if (++count < 10) {
        if (flag == 0) {
          face[count] = new PacMan(0, e.getX(), e.getY(), 60);
        } else if (flag == 1) {
          face[count] = new PacManWithEye(0, e.getX(), e.getY(), 60);
        }
        repaint();
        text.setText("The center is (" + e.getX() + ", " + e.getY() + ")");
      } else {
        text.setText("No more PacMan");
      }
    }

    public void mouseDragged(MouseEvent e) {
      if (count < 10) {
        distance = (int)Math.hypot(e.getX() - face[count].getXCenter(), e.getY() - face[count].getYCenter());
        face[count].setSize(distance * 2);
        text.setText(Integer.toString(distance * 2));
        repaint();
      }
    }
  }

  class ButtonListener1 extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      if (++flag > 1) {
        flag = 0;
      }
      if (flag == 0) {
        button1.setText("PacMan");
      } else if (flag == 1) {
        button1.setText("PacManWithEye");
      }
    }
  }
}