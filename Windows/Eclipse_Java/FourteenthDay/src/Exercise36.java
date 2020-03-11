/*
 * When i press the button, the people face will go down and show it.
 *  Created by Shin Ikeda on Jan 14th 2020
 *
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

//import Presen_not.MyListener;


public class Exercise36 extends JFrame implements ActionListener {
 //BufferedImage bimage1,bimage[];
 Mat imageArray, part;
 int width, height;
 JButton button;
 boolean flag = true;
 String path ="C:\\Users\\codea\\opencv4.1.2\\build\\etc\\haarcascades\\";
 String cascade = "haarcascade_frontalface_alt2.xml";
 Rect[] area = null;
 CascadeClassifier faceDetector;
 MatOfRect faceDetections;
 Rect[] faceArea = null;
 int count = 0;
 BufferedImage bimage1, bimage2, getImage,bimage3,bimage[];
	JButton button1, button2, button3, button4, button5, button6, button7, button8, button9;
	JTextField text1;
  String filename = "imageA.jpg";
  int[][] rect = {{0,0},{0,0}};
  int[] center = {0,0};
 // int xCenter = 330, yCenter = 300;
  double rate;
  double scale = 1.0;
  double rotate = 0.0;
  Mat src, gray, binary;//



 public Exercise36() {
  System.loadLibrary(Core.NATIVE_LIBRARY_NAME) ;
  button1 = new JButton("Z I");
	button2 = new JButton("Z O");
	button3 = new JButton("L R");
	button4 = new JButton("R R");
	button5 = new JButton("C M");
	button6 = new JButton("I M");
	button7 = new JButton("S D");
	button9 = new JButton("O P");//


	JPanel panel1 = new JPanel();
	button1.addActionListener(this);
	button2.addActionListener(this);
	button3.addActionListener(this);
	button4.addActionListener(this);
	button5.addActionListener(this);
	button6.addActionListener(this);
	button7.addActionListener(this);
	button9.addActionListener(this);//

	panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
	panel1.add(button1);
	panel1.add(button2);
	panel1.add(button3);
	panel1.add(button4);
	panel1.add(button5);
	panel1.add(button6);
	panel1.add(button7);
	panel1.add(button9);//
	getContentPane().add(panel1, BorderLayout.NORTH);


	button8 = new JButton("Dispaly");
	JPanel p2 = new JPanel();
	button8.addActionListener(this);
	text1 = new JTextField("",15);
	p2.setLayout(new FlowLayout(FlowLayout.LEFT));
	p2.add(button8);
	p2.add(text1);
	getContentPane().add(p2, BorderLayout.CENTER);


MyListener myListener = new MyListener();
addMouseListener(myListener);
addMouseMotionListener(myListener);
  setTitle("sentation");
  setSize(660, 860);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  //button = new JButton("stop");
  //button.setActionCommand("stop");
 // button.addActionListener(this);
 // JPanel panel = new JPanel();
  //panel.add(button);
 // getContentPane().add(panel, BorderLayout.NORTH);
  System.out.println("aaa");
  setVisible(true);
  initialization();
  repaint();
  faceDetector = new CascadeClassifier(path + cascade);
  System.out.println("bbb");
  //cameraImage();
 }


 public void paint(Graphics g) {
	  g.clearRect(10, 610, 650, 790);//Even I changed number, the result is same = why is it?
   int hposition = 610;
   int size = 7; // In this case, you can change the green point size.
   double width1;// The one I use it for 152 and 153 places.
   double height1;// The one I use it for 152 and 153 places.

   if (bimage1 != null) {
     g.drawImage(bimage1, 10, 120, this);
   }
   if (bimage2 != null) {
     g.drawImage(bimage2, 10, 120, bimage2.getWidth(), bimage2.getHeight(), this);
   }
   if(bimage3 != null){
	   System.out.println("in bimage3");
   	 g.drawImage(bimage3, 10, 120,width, height, this);
}
   if (getImage != null) {
     rate = (double)240 / getImage.getHeight();
     if (rate >= 1.0) {
       rate = 1.0;
     }
     width1 = (double)getImage.getWidth() * rate;
     height1 = (double)getImage.getHeight() * rate;
     g.drawImage(getImage, 10, hposition, (int)width1, (int)height1, this);
   }
   if(faceArea != null) {
    g.setColor(Color. yellow);
    for(Rect rect : faceArea) {
     g.drawRect(rect.x / 2 + 10, rect.y / 2 + 60,
       rect.width / 2, rect.height / 2);
    }
    for(int i = 0; i < count; i++) {
     g.drawImage(bimage[i], 10 + 130 * i, 450,120,120, this);
    }
   }

   g.setColor(Color.green);
   g.fillRect((rect[0][0] + ((rect[1][0] - rect[0][0])/2)) , (rect[0][1]+(rect[1][1] - rect[0][1])/2) , size,  size);
   g.setColor(Color.red);
   g.drawRect(rect[0][0], rect[0][1], rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);


 }

 void initialization() {
   try {
     bimage1 = ImageIO.read(new File(filename)); // To read the photo.(I reckon)
   } catch (Exception e) {
     System.out.println("error " + e);
   }

 }
//I still cannot explain to someone  how to use centre.(106,107,110,113 lines)
 void transform() {
	  center[0] = 330;
	  center[1] = 300;
   bimage2 = new BufferedImage(bimage1.getWidth(), bimage1.getHeight(), bimage1.getType());
   AffineTransform affine = new AffineTransform(); //I reckon process the photo.
   affine.translate(center[0], center[1]);
   affine.scale(scale, scale);// Make the photo big or small.
   affine.rotate(rotate); // Tilt right or left.
   affine.translate(-center[0], -center[1]);
   AffineTransformOp operator = new AffineTransformOp(affine, AffineTransformOp.TYPE_BICUBIC);
   operator.filter(bimage1, bimage2);
 }

 public void takeImage() {
	     try {
	  // File file2 = new File(filename);
	  // bimage2  = ImageIO.read(file2);
	   File file1 = new File(filename);
	   bimage1  = ImageIO.read(file1);
	  } catch(IOException e) {
	  }
	  repaint();
	 }

 public void takeImage2() {
	     try {
	   File file2 = new File(filename);
	   bimage2  = ImageIO.read(file2);
	  // File file1 = new File(filename);
	  // bimage1  = ImageIO.read(file1);
	  } catch(IOException e) {
	  }
	  repaint();
	 }
// public void takeImage3(){
	//  src = new Mat();
	//  gray =  new Mat();
	//  binary = new Mat();
	 // src = Imgcodecs.imread(filename);
	//  Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
	//  Imgproc.threshold(gray, binary, 80, 255, Imgproc.THRESH_BINARY);
	//  new1 = matToBufferedImage(src);
	//  new1 = matToBufferedImage(gray);
	 // new1 = matToBufferedImage(binary);
	 // repaint();
	// }

 public void cameraImage(){
	  imageArray = new Mat();
	  VideoCapture videoDevice = new VideoCapture(0);
	  if (videoDevice.isOpened()) {
	   videoDevice. read(imageArray);
	   while(imageArray.cols() == 0) {
	    System. out.println("imageArray is null");
	    videoDevice.read(imageArray);
	   }
	   bimage3 = matToBufferedImage(imageArray);
	   System. out.println("width = " + bimage3.getWidth()
	   + " height = " + bimage3.getHeight());
	   width = bimage3.getWidth() / 2;
	   height = bimage3.getHeight() / 2;
	   while(flag) {
	    videoDevice.read(imageArray);
	    bimage3 = matToBufferedImage(imageArray);
	    faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(imageArray,faceDetections);
	    faceArea = faceDetections.toArray();
	    repaint();
	   }

	   videoDevice.release();

	  } else {
	   System. out.println("Error.");
	  }
	 }

 void getFace() {
	  if(faceArea != null) {
	   count = faceArea.length;
	   if(count > 5) {
	    count = 5;
	   }
	   bimage = new BufferedImage[count];
	   for(int i = 0; i < count; i++) {
	    part = new Mat(imageArray, faceArea[i]);
	    bimage[i] = matToBufferedImage(part);
	   }
	  }
	 }



 public static void main(String[] args) {
   new Exercise36();
 }


 public void actionPerformed(ActionEvent event) {
	  String str;
		if (event.getSource() == button1) {
     //scale = 1.1; I thought I had to use it.
     scale += 0.1; //All the time, when you press button1, 0.1 will increase.
			transform();
			getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
			repaint();
		} else if (event.getSource() == button2) {
     //scale = 0.9;
     scale -= 0.1; //All the time, when you press button2, 0.1 will decrease.
			transform();
			getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
			repaint();
		} else if (event.getSource() == button3) {
     rotate -= Math.toRadians(1); //All the time, when you press button3, 1 degree will rotate.
			transform();
			getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
			repaint();
		} else if (event.getSource() == button4) {
     rotate += Math.toRadians(1); //All the time, when you press button4, 1 degree will rotate.
			transform();
			getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
			repaint();
   }
		//transform();
		 // getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
		 // repaint();
		else if (event.getSource() == button6) {
			Graphics g = getGraphics();
			g.clearRect(10,120,640,480);
				  cameraImage();
				  }


		else if(event.getSource()==button7) {
		     try {
		    	 // The most importance place.
		   // BufferedImage simage2 = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] -rect[0][0], rect[1][1] - rect[0][1]);
		    BufferedImage simage1 = bimage1.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] -rect[0][0], rect[1][1] - rect[0][1]);
		  ///  File output2 = new File("save_photo2.jpg");
		   // ImageIO.write(simage2, "jpg", output2);
		    File output1 = new File("save_photo1.jpg");
		    ImageIO.write(simage1, "jpg", output1);
		     } catch (IOException g) {
		      System.out.println("error! " + g);
		     }
		    }


		else if (event.getSource() == button8) {     //I have added else.
			   str = text1.getText();
				Graphics g = getGraphics();
				g.clearRect(400,60,260,50);// I have changed 100 to 50.
				Font font1 = new Font("Diaog", Font.PLAIN, 30);
				g.setFont(font1);
				g.setColor(Color.blue);
				g.drawString(str,400,90);
		   } else if(event.getSource()==button9) {
			     try {
			    	 // The most importance place.
			    BufferedImage simage2 = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] -rect[0][0], rect[1][1] - rect[0][1]);
			   // BufferedImage simage1 = bimage1.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] -rect[0][0], rect[1][1] - rect[0][1]);
			    File output2 = new File("save_photo2.jpg");
			    ImageIO.write(simage2, "jpg", output2);
			   // File output1 = new File("save_photo1.jpg");
			   // ImageIO.write(simage1, "jpg", output1);
			     } catch (IOException g) {
			      System.out.println("error! " + g);
			     }
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
       getImage = bimage2.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);

     } else {
       getImage = bimage1.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
     }
     repaint();
   }

   public void mouseDragged(MouseEvent e) {
     rect[1][0] = e.getX();
     rect[1][1] = e.getY();
     repaint();
   }
 }
 public static BufferedImage matToBufferedImage(Mat matrix) {
	 //  System.out.println("in mat to buff");
	  int cols = matrix.cols();
	  int rows = matrix.rows();
	  int elemSize = (int)matrix.elemSize();
	  byte[] data = new byte[cols * rows * elemSize];
	  int type;

	  matrix.get(0, 0, data);

	  switch (matrix.channels()) {
	  case 1:
	   type = BufferedImage.TYPE_BYTE_GRAY;
	   break;
	  case 3:
	   type = BufferedImage.TYPE_3BYTE_BGR;
	   byte b;
	   for(int l = 0; l < data.length; l =l + 3) {
	    b = data[l];
	    data[l] = data[l+2];
	    data[l+2] = b;
	   }
	   break;
	  default:
	   return null;
	  }
	  BufferedImage image3 = new BufferedImage(cols, rows, type);
	  image3.getRaster().setDataElements(0, 0, cols, rows, data);
	  // System.out.println(image3);
	  return image3;
	 }
}
