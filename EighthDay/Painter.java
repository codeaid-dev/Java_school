import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Painter extends JFrame {
  Face face[] = new Face[10];
  int count = 0; // the number of Pac-Mac
  int flag = 0; // 0: PacMan, 1: PacManWithEye
  int mode = 0; // 0: create, 1: check, 2: change
  boolean max = false; // true: max draw, false: not yet max

  JButton button1, button2;
  JTextField text;

  public Painter() {
    setTitle("Painter");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    MyListener myListener = new MyListener();
    addMouseListener(myListener);
    addMouseMotionListener(myListener);

    button1 = new JButton();
    button1.addMouseListener(new ButtonListener1());
    button2 = new JButton();
    button2.addMouseListener(new ButtonListener2());
    text = new JTextField();
    text.setColumns(16);
    JPanel p = new JPanel();
    p.add(button2);
    p.add(button1);
    p.add(text);
    getContentPane().add(p, BorderLayout.SOUTH);
    button2.setText("create mode");
    button1.setText("PacMan");
    text.setText("Click the center point");
    setVisible(true);
  }

  public void paint(Graphics g) {
    super.paint(g);
    g.clearRect(0, 0, 500, 450);
    if (count > 0) {
      if (count > 10) {
        count = 10;
      }
      for (int i = 0; i < count; i++) {
        face[i].make(g);
      }
    }
  }

  public static void main(String[] args) {
    new Painter();
  }

  class MyListener extends MouseAdapter {
    int distance, xCenter, yCenter;

    public void mousePressed(MouseEvent e) {
      if (mode == 0) {
        // create mode
        count++;
        if (count <= 10) {
          if (flag == 0) {
            face[count-1] = new PacMan(0, e.getX(), e.getY(), 60);
          } else if (flag == 1) {
            face[count-1] = new PacManWithEye(0, e.getX(), e.getY(), 60);
          } else if (flag == 2) {
            face[count-1] = new PacManWithAngry(0, e.getX(), e.getY(), 60);
          }
          text.setText("The center is (" + e.getX() + ", " + e.getY() + ")");
        } else {
          max = true;
          count = 10;
          text.setText("No more PacMan");
        }
        repaint();
      } else if (mode == 1) {
        // check mode
        Boolean found = false;
        for (int num = 0; num < count; num++) {
          if (Math.hypot(e.getX() - face[num].getXCenter(),
                    e.getY() - face[num].getYCenter()) < face[num].getSize()/2) {
            found = true;
            if (face[num] instanceof PacMan) {
              text.setText("This is PacMan");
            } else if (face[num] instanceof PacManWithEye) {
              text.setText("This is PacManWithEye");
            } else if (face[num] instanceof PacManWithAngry) {
              text.setText("This is PacManWithAngry");
            } else {
              text.setText("Type is not found");
            }
          }
        }
        if (!found) {
          text.setText("Press a Pac-Man");
        }
      } else if (mode == 2) {
        // change mode
        Boolean found = false;
        for (int num = 0; num < count; num++) {
          if (Math.hypot(e.getX() - face[num].getXCenter(),
                    e.getY() - face[num].getYCenter()) < face[num].getSize()/2) {
            found = true;
            if (face[num] instanceof PacMan) {
              if (flag == 1) {
                  face[num] = new PacManWithEye(face[num].getSize(), face[num].getXCenter(), face[num].getYCenter(), 60);
                  text.setText("Changing to PacManWithEye");
                } else if (flag == 2) {
                  face[num] = new PacManWithAngry(face[num].getSize(), face[num].getXCenter(), face[num].getYCenter(), 60);
                  text.setText("Changing to PacManWithAngry");
                } else {
                  text.setText("Already PacMan");
                }
            } else if (face[num] instanceof PacManWithEye) {
              if (flag == 0) {
                  face[num] = new PacMan(face[num].getSize(), face[num].getXCenter(), face[num].getYCenter(), 60);
                  text.setText("Changing to PacMan");
                } else if (flag == 2) {
                  face[num] = new PacManWithAngry(face[num].getSize(), face[num].getXCenter(), face[num].getYCenter(), 60);
                  text.setText("Changing to PacManWithAngry");
                } else {
                  text.setText("Already PacManWithEye");
                }
            } else if (face[num] instanceof PacManWithAngry) {
              if (flag == 0) {
                  face[num] = new PacMan(face[num].getSize(), face[num].getXCenter(), face[num].getYCenter(), 60);
                  text.setText("Changing to PacMan");
                } else if (flag == 1) {
                  face[num] = new PacManWithEye(face[num].getSize(), face[num].getXCenter(), face[num].getYCenter(), 60);
                  text.setText("Changing to PacManWithEye");
                } else {
                  text.setText("Already PacManWithAngry");
                }
            }
            repaint();
          }
        }
        if (!found) {
          text.setText("Press a Pac-Man");
        }
      }
    }

    public void mouseDragged(MouseEvent e) {
      if (count <= 10 && mode == 0 && max == false) {
        distance = (int)Math.hypot(e.getX() - face[count-1].getXCenter(), e.getY() - face[count-1].getYCenter());
        face[count-1].setSize(distance * 2);
        text.setText(Integer.toString(distance * 2));
        repaint();
      }
    }
  }

  class ButtonListener1 extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      if (++flag > 2) {
        flag = 0;
      }
      if (flag == 0) {
        button1.setText("PacMan");
      } else if (flag == 1) {
        button1.setText("PacManWithEye");
      } else if (flag == 2) {
        button1.setText("PacManWithAngry");
      }
    }
  }

  class ButtonListener2 extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      if (++mode > 2) {
        mode = 0;
      }
      if (mode == 0) {
        button2.setText("create mode");
      } else if (mode == 1) {
        button2.setText("check mode");
      } else if (mode == 2) {
        button2.setText("change mode");
      }
    }
  }
}