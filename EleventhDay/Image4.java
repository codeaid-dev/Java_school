import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Image4 extends JFrame {
  BufferedImage bimage;
  String filename = "imageA.jpg";

  public Image4() {
    setTitle("Image3");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    takeImage();
  }

  public void paint(Graphics g) {
    if (bimage != null) {
      g.drawImage(bimage, 10, 30, 310, 330, 150, 100, 450, 400, this);
    }
  }

  public void takeImage() {
    try {
      File file = new File(filename);
      bimage = ImageIO.read(file);
      System.out.println("width = " + bimage.getWidth() + " height = " + bimage.getHeight());
      BufferedImage simage = bimage.getSubimage(150, 100, 300, 300);
      File output = new File("saveA.jpg");
      ImageIO.write(simage, "jpg", output);
    } catch (IOException e) {
      System.out.println("error! " + e);
    }
    repaint();
  }

  public static void main(String[] args) {
    new Image4();
  }
}