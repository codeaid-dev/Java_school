/*
 *
 * by Shin Ikeda on 19 Jan 2020
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;//
import org.opencv.core.Size;//
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Presentation_ike extends JFrame implements ActionListener {
  BufferedImage bimage1, bimage2, getImage,   bimage5,bimage6;
	JButton button1, button2, button3, button4, button5, button6, button7, button8, button9;
	JTextField text1;
	 Mat src, gray, binary, mosaic, part;
  String filename = "imageB.jpg";//imageA
  int[][] rect = {{0,0},{0,0}};
  int[] center = {0,0};
 // int xCenter = 330, yCenter = 300;
  double rate;
  double scale = 1.0;
  double rotate = 0.0;


  String path ="/usr/local/Cellar/opencv/4.2.0_1/share/opencv4/haarcascades/";////55 to 63
  String cascade1 = "haarcascade_frontalcatface.xml";
  Rect[] faceArea1 = null;

  String cascade2 =  "haarcascade_eye_tree_eyeglasses.xml";
  Rect[] faceArea2 = null;

  String cascade3 = "haarcascade_eye.xml";
  Rect[] faceArea3 = null;

  public Presentation_ike() {
	  System.loadLibrary(Core.NATIVE_LIBRARY_NAME);////
    setSize(660, 860);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Final Presentation");
    setVisible(true);

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


	   // setDefaultCloseOperation(EXIT_ON_CLOSE);////Needless
    MyListener myListener = new MyListener();
    addMouseListener(myListener);
    addMouseMotionListener(myListener);


    setVisible(true);
    takeImage();
    initialization();
    detect();
    repaint();//Why we need repaint in this place?
  }

  public void paint(Graphics g) {
	  g.clearRect(10, 610, 650, 790);//Even I changed number, the result is same = why is it?
    int hposition = 610;
    int size = 7; // In this case, you can change the green point size.
    double width;
    double height;

    if (bimage1 != null) {
      g.drawImage(bimage1, 10, 120, this);
    }
    if (bimage2 != null) {
      g.drawImage(bimage2, 10, 120, bimage2.getWidth(), bimage2.getHeight(), this);
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

    g.setColor(Color.green);
    g.fillRect((rect[0][0] + ((rect[1][0] - rect[0][0])/2)) , (rect[0][1]+(rect[1][1] - rect[0][1])/2) , size,  size);
    g.setColor(Color.red);
    g.drawRect(rect[0][0], rect[0][1], rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);


    	  if(bimage5 != null) {
    	   g.drawImage(bimage5, 480, 640, this);
    	  }
    	 // if(bimage6 != null) {
    	  // g.drawImage(bimage6, 450, 610, this);
    	 // }



    		  if(faceArea1 != null) {   //////163 to 184
    		   g.setColor(Color.yellow);
    		   for(Rect rect : faceArea1) {
    		    g.drawRect(rect.x + 10, rect.y + 120, rect.width, rect.height);
    		   }
    		  }


    		  if(faceArea2 != null) {
    		   g.setColor(Color.red);
    		   for(Rect rect : faceArea2) {
    		    g.drawRect(rect.x + 10, rect.y + 120, rect.width, rect.height);
    		   }
    		  }


    		  if(faceArea3 != null) {
    		   g.setColor(Color.green);
    		   for(Rect rect : faceArea3) {
    		    g.drawRect(rect.x + 10, rect.y + 120, rect.width, rect.height);
    		   }
    		  }

  }

  void detect(){   /////////188 to 198

	  src = new Mat();
	  src = Imgcodecs. imread(filename);
	  bimage1 = matToBufferedImage(src);

	  faceArea1 = getArea(cascade1);
	  faceArea2 = getArea(cascade2);
	  faceArea3 = getArea(cascade3);

	 }


  Rect[] getArea(String str) {    ///////201 to 210
	  Rect[] area = null;
	  CascadeClassifier faceDetector = new CascadeClassifier(path+str);
	  MatOfRect faceDetections = new MatOfRect();
	  faceDetector .detectMultiScale(src,faceDetections);
	  System. out.println("Detected "
	    + faceDetections.toArray().length + "faces using" + str);
	  area = faceDetections. toArray();
	  return area;
	 }

  void initialization() {
    try {
      bimage1 = ImageIO.read(new File(filename)); // To read the photo.(I reckon)
    } catch (Exception e) {
      System.out.println("error " + e);
    }

  }
// I still cannot explain to someone  how to use centre.(106,107,110,113 lines)
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

  public void takeImage3() {  //To apply the filter

	  src = new Mat();
	  src = Imgcodecs.imread(filename);
	  bimage1 = matToBufferedImage(src);

	  repaint();
	 }

  public void partImage() {
	  Rect roi = new Rect(rect[0][0] - 10,rect[0][1] - 120,
			  rect[1][0] - rect[0][0],rect[1][1] - rect[0][1] );

	  part =  new Mat(src,roi);
	 // bimage6 =  matToBufferedImage(part);
	  mosaic = new Mat(src,roi);
	  Imgproc.resize(mosaic,mosaic, new Size(),
			  0.1,0.1,Imgproc.INTER_NEAREST);
	  Imgproc.resize(mosaic,mosaic, new Size(),
			  10.0,10.0,Imgproc.INTER_NEAREST);

	  bimage5 = matToBufferedImage(mosaic);
	  repaint();
  }



  public static void main(String[] args) {
    new Presentation_ike();
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
else if (event.getSource() == button5) {  //yomikomi Second press 5  it will console below this area.
	String st;
	  try {
	   FileReader isr = new FileReader("words.txt");
	   BufferedReader br = new BufferedReader(isr);
	   st = br.readLine();
	   while(st != null) {
	    System.out.println(st);
	    st = br.readLine();
	   }
	   System.out.println("end of file loading ");
	   br.close();
	  }catch(IOException e) {
	   System.out.println(e);
	  }
		}
		else if (event.getSource() == button6) { //kakikomi First press 6  it will write in txt.
			int num;
			String wstr;
			wstr = text1.getText();

			try {
				FileWriter fw = new FileWriter("words.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(wstr);
				//for(int i  = 0; i < 11; i++) {
				//	num = (int)(10.0 * Math.random());
				//	bw.write(Integer.toString(num));
				//	bw.newLine();
				//}
				bw.close();
			}catch(IOException e) {
				System.out.println(e);
			}
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
       // partImage();
      } else {
        getImage = bimage1.getSubimage(rect[0][0] - 10, rect[0][1] - 120, rect[1][0] - rect[0][0], rect[1][1] - rect[0][1]);
      }
      partImage();
      repaint();
    }

    public void mouseDragged(MouseEvent e) {
      rect[1][0] = e.getX();
      rect[1][1] = e.getY();
      repaint();
    }
  }
  public static BufferedImage matToBufferedImage(Mat matrix) {
	  int cols = matrix.cols();
	  int rows = matrix.rows();
	  int elemSize = (int)matrix.elemSize();
	  byte[] data = new byte[cols * rows * elemSize];
	  int type;

	  matrix.get(0, 0, data);

	  switch(matrix.channels()) {
	  case 1:
	   type = BufferedImage.TYPE_BYTE_GRAY;
	   break;
	  case 3:
	   type = BufferedImage.TYPE_3BYTE_BGR;
	   byte b;
	   for(int i = 0; i < data.length; i = i + 3) {
	    b = data[i];
	    data[i] = data[i+2];
	    data[i+2] = b;
	   }
	   break;
	  default:
	   return null;
	  }
	  BufferedImage image2 = new BufferedImage(cols, rows, type);
	  image2.getRaster().setDataElements(0, 0, cols, rows, data);
	  return image2;
	 }

}