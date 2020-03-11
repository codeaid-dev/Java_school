import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Image2 extends JFrame {
  BufferedImage bimage;
  String filename = "imageA.jpg";
  double rate = 0.7;

  public Image2() {
    setTitle("Image2");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    takeImage();
  }

  public void paint(Graphics g) {
    if (bimage != null) {
      
      g.drawImage(bimage, 10, 30, (int)(bimage.getWidth() * rate), (int)(bimage.getHeight() * rate), this);
      System.out.println("width = " + (int)(bimage.getWidth() * rate) + " height = " + (int)(bimage.getHeight() * rate));
      
      //g.drawImage(bimage, 10, 30, 310, 330, 150, 100, 450, 400, this);
    }
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
    new Image2();
  }
}