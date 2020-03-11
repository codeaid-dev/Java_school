import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HomeWork3 extends JFrame implements ActionListener {
  BufferedImage bimage1, bimage2, getImage;
	JButton button1, button2, button3, button4;
  String filename = "imageA.jpg";
  int[][] rect = {{0,0},{0,0}};
  int[] center = {0,0};
  int xCenter = 330, yCenter = 300;
  double rate;
  double scale = 1.0;
  double rotate = 0.0;

  public HomeWork3() {
    setSize(660, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Home Work 3");
    setVisible(true);

		button1 = new JButton("Zoom In");
		button2 = new JButton("Zoom Out");
		button3 = new JButton("Left Rotation");
		button4 = new JButton("Right Rotation");
		JPanel panel1 = new JPanel();
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel1.add(button1);
		panel1.add(button2);
		panel1.add(button3);
		panel1.add(button4);
		getContentPane().add(panel1, BorderLayout.CENTER);

    MyListener myListener = new MyListener();
    addMouseListener(myListener);
    addMouseMotionListener(myListener);

    setVisible(true);
    initialization();
    repaint();
  }

  public void paint(Graphics g) {
    int hposition = 550;
    double width;
    double height;

    g.clearRect(10, hposition, 640, 260);
    if (bimage1 != null) {
      g.drawImage(bimage1, 10, 60, this);
    }
    if (bimage2 != null) {
      g.drawImage(bimage2, 10, 60, bimage2.getWidth(), bimage2.getHeight(), this);
    }
    if (getImage != null) {
      rate = (double)240 / getImage.getHeight();
      if (rate >= 1.0) {
        rate = 1.0;
      }
      width = (double)getImage.getWidth() * rate;
      height = (double)getImage.getHeight() * rate;
      g.drawImage(getImage, 10, hposition, (int)width, (int)height, this);
    }

    g.setColor(Color.red);
    g.drawRect(rect[0][0], rect[0][1], rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
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
    new HomeWork3();
  }

  public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button1) {
      //scale = 1.1;
      scale += 0.1;
			transform();
	        getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 60, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
			repaint();
		} else if (event.getSource() == button2) {
      //scale = 0.9;
      scale -= 0.1;
			transform();
	        getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 60, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
			repaint();
		} else if (event.getSource() == button3) {
      rotate -= Math.toRadians(1);
			transform();
	        getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 60, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
			repaint();
		} else if (event.getSource() == button4) {
      rotate += Math.toRadians(1);
			transform();
	        getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 60, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
			repaint();
    }
  }

  class MyListener extends MouseAdapter {
    int cx, cy;
    String str;

    public void mousePressed(MouseEvent e) {
      rect[0][0] = e.getX();
      rect[0][1] = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
      if (bimage2 != null) {
        getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 60, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
      } else {
        getImage = bimage1.getSubimage(rect[0][0] - 10, rect[0][1] - 60, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
      }
      repaint();
    }

    public void mouseDragged(MouseEvent e) {
      rect[1][0] = e.getX();
      rect[1][1] = e.getY();
      repaint();
    }
  }
}