import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Image5 extends JFrame {
  BufferedImage bimage, getImage;
  String filename = "imageA.jpg";
  int rect[][] = {{0, 0}, {0, 0}};

  public Image5() {
    setTitle("Image5");
    setSize(700, 800);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    MyListener myListener = new MyListener();
    addMouseListener(myListener);
    addMouseMotionListener(myListener);

    setVisible(true);
    takeImage();
  }

  public void paint(Graphics g) {
    int hposition = 520;

    g.clearRect(0, 0, 700, 800);
    if (bimage != null) {
      g.drawImage(bimage, 30, 30, this);
    }
    if (getImage != null) {
      g.drawImage(getImage, 30, hposition, this);
    }
    g.setColor(Color.red);
    g.drawRect(rect[0][0], rect[0][1], rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
  }

  public void takeImage() {
    try {
      File file = new File(filename);
      bimage = ImageIO.read(file);
    } catch (IOException e) {
      System.out.println("error! " + e);
    }
    repaint();
  }

  public static void main(String[] args) {
    new Image5();
  }

  class MyListener extends MouseAdapter {
    int cx, cy;
    String str;

    public void mousePressed(MouseEvent e) {
      rect[0][0] = e.getX();
      rect[0][1] = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
      getImage = bimage.getSubimage(rect[0][0] - 30, rect[0][1] - 30, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
      repaint();
    }

    public void mouseDragged(MouseEvent e) {
      rect[1][0] = e.getX();
      rect[1][1] = e.getY();
      repaint();
    }
  }
}