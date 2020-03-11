import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Image1 extends JFrame {
  BufferedImage bimage;
  String filename = "imageA.jpg";

  public Image1() {
    setTitle("Image1");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    takeImage();
  }

  public void paint(Graphics g) {
    if (bimage != null) {
      g.drawImage(bimage, 10, 30, this);
    }
  }

  public void takeImage() {
    try {
      File file = new File(filename);
      bimage = ImageIO.read(file);
      System.out.println("width = " + bimage.getWidth() + " height = " + bimage.getHeight());
      System.out.println("type: " + bimage.getType() + " field: " + BufferedImage.TYPE_3BYTE_BGR);
    } catch (IOException e) {
      System.out.println("error! " + e);
    }
    repaint();
  }

  public static void main(String[] args) {
    new Image1();
  }
}