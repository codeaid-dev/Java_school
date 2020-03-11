import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.imageio.ImageIO;

public class HW3Prac extends JFrame implements ActionListener {
	BufferedImage bimage1,bimage2,getImage; 
	JButton button1, button2, button3, button4;
	String filename = "imageA.jpg";
	int[][] rect = {{0,0},{0,0}};
	int[] center = {0,0};
	double rate;
	double scale = 1.0;
	double rotate = 0.0;
	int xCenter, yCenter;
	
	
	public HW3Prac() {
		setSize(660, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Home Work 3");
		
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
		takeImage();
	}
	
	public void paint(Graphics g) {
		 int hposition = 600;
	    if (bimage1 != null) {
	      
	      g.drawImage(bimage1, 10, 60, (int)(bimage1.getWidth()), (int)(bimage1.getHeight()), this);
	      
	      //g.drawImage(bimage1, 10, 30, 310, 330, 150, 100, 450, 400, this);
	    }
	    if (bimage2 != null) {
		      
		      g.drawImage(bimage2, 10, 60, (int)(bimage2.getWidth()), (int)(bimage2.getHeight()), this);
		      
		      //g.drawImage(bimage1, 10, 30, 310, 330, 150, 100, 450, 400, this);
		    }
	    if (getImage != null) {
	        g.drawImage(getImage, 10, hposition, this);
	      }
	    g.setColor(Color.red);
	    g.drawRect(rect[0][0], rect[0][1], rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
	  }
	
	public void takeImage() {
	    try {
	      File file = new File(filename);
	      bimage1 = ImageIO.read(file);
	    } catch (IOException e) {
	      System.out.println("error! " + e);
	    }
	    repaint();
	  }
	
	void scale() {
		int width = (int)(bimage1.getWidth()*scale);
		int height = (int)(bimage1.getHeight()*scale);
		bimage2 = new BufferedImage(width, height, bimage1.getType());
		AffineTransform affine = new AffineTransform();
		affine.scale(scale, scale);
		AffineTransformOp operator = 
				new AffineTransformOp(affine, AffineTransformOp.TYPE_BICUBIC);
		operator.filter(bimage1, bimage2);
	}
	void translate() {
		bimage2 = new BufferedImage(bimage1.getWidth(),
				bimage1.getHeight(),bimage1.getType());
		AffineTransform affine = new AffineTransform();
		affine.translate(50,20);
		AffineTransformOp operator = 
				new AffineTransformOp(affine, AffineTransformOp.TYPE_BICUBIC);
		operator.filter(bimage1,bimage2);
	}
	
	void rotate() {
		bimage2 = new BufferedImage(bimage1.getWidth(),
				bimage1.getHeight(),bimage1.getType());
		AffineTransform affine = new AffineTransform();
		affine.rotate(Math.toRadians(30));
		AffineTransformOp operator = 
				new AffineTransformOp(affine, AffineTransformOp.TYPE_BICUBIC);
		operator.filter(bimage1,bimage2);
	}
	
	void rotate2() {
		bimage2 = new BufferedImage(bimage1.getWidth(),
				bimage1.getHeight(),bimage1.getType());
		AffineTransform affine = new AffineTransform();
		affine.rotate(Math.toRadians(30),320,240);
		AffineTransformOp operator = 
				new AffineTransformOp(affine, AffineTransformOp.TYPE_BICUBIC);
		operator.filter(bimage1,bimage2);
	}
	
	void transform() {
    	bimage2 = new BufferedImage(bimage1.getWidth(),
    			bimage1.getHeight(), bimage1.getType());
    	AffineTransform affine = new AffineTransform();
    	affine.translate(xCenter, yCenter);
    	affine.scale(scale,scale);
    	affine.rotate(rotate);
    	affine.translate(-xCenter, -yCenter);
    	AffineTransformOp operator = 
    			new AffineTransformOp(affine,AffineTransformOp.TYPE_BICUBIC);
    	operator.filter(bimage1,bimage2);
    }

	public static void main(String[] args) {
		new HW3Prac();
	}
	
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource()==button1) {
			scale = 1.1; //scale += 0.1;
			scale();
			repaint();
			System.out.println("button1");
			
		}else if(event.getSource()==button2) {
			scale = 0.9;
			scale();
			//translate();
			repaint();
			System.out.println("button2");
			
		}else if(event.getSource()==button3) {
			transform();
			repaint();
			System.out.println("button3");
			
			
		}else if(event.getSource()==button4) {
			rotate2();
			repaint();
			System.out.println("button4");
			
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
	      getImage = bimage2.getSubimage(rect[0][0] - 30, rect[0][1] - 30, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
	      repaint();
	    }

	    public void mouseDragged(MouseEvent e) {
	      rect[1][0] = e.getX();
	      rect[1][1] = e.getY();
	      repaint();
	    }
	  }


}
