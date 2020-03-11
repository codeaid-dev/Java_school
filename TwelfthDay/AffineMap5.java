import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class AffineMap5 extends JFrame {
  BufferedImage bimage1, bimage2;
  String filename = "imageA.jpg";
  int xCenter, yCenter;
  double scale = 0.7;
  double rotate = Math.toRadians(30);

  public AffineMap5() {
    setSize(660, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    MyListener myListener = new MyListener();
    addMouseListener(myListener);
    initialization();
    repaint();
  }

  public void paint(Graphics g) {
    int hposition = 550;
    int size = 5;

    if (bimage1 != null) {
      g.drawImage(bimage1, 10, 60, this);
    }
    if (bimage2 != null) {
      g.drawImage(bimage2, 10, hposition, bimage2.getWidth() / 2, bimage2.getHeight() / 2, this);
    }

    g.setColor(Color.red);
    g.fillRect(xCenter - size / 2 + 10, yCenter - size / 2 + 60, size, size);
  }

  void initialization() {
    try {
      bimage1 = ImageIO.read(new File(filename));
    } catch (Exception e) {
      System.out.println("error " + e);
    }
  }

  void transform() {
    bimage2 = new BufferedImage(bimage1.getWidth(), bimage1.getHeight(), bimage1.getType());
    AffineTransform affine = new AffineTransform();
    affine.translate(xCenter, yCenter);
    affine.scale(scale, scale);
    affine.rotate(rotate);
    affine.translate(-xCenter, -yCenter);
    AffineTransformOp operator = new AffineTransformOp(affine, AffineTransformOp.TYPE_BICUBIC);
    operator.filter(bimage1, bimage2);
  }

  public static void main(String[] args) {
    new AffineMap5();
  }

  class MyListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      xCenter = e.getX() - 10;
      yCenter = e.getY() - 60;
      System.out.println(xCenter + ", " + yCenter);
      transform();
      repaint();
    }
  }
}