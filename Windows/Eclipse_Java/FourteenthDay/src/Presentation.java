import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

public class Presentation extends JFrame implements ActionListener {
	BufferedImage bimage1, bimage2, getImage;
	BufferedImage bimage[];
	Mat imageArray, part;
	int width, height;
	JButton button1, button2, button3, button4, button5;
	boolean flag = true;
	boolean cameraSwitch = true;
	String path = "C:\\Users\\codea\\opencv4.1.2\\build\\etc\\haarcascades\\";
	String cascade = "haarcascade_frontalface_alt2.xml";
	Rect[] area = null;
	CascadeClassifier faceDetector;
	MatOfRect faceDetections;
	Rect[] faceArea = null;
	int count = 0;

	public Presentation() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		setTitle("FaceCamera");
		setSize(660, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		button5 = new JButton("Camera OFF");
		button5.setActionCommand("cameraSwitch");
		button5.addActionListener(this);
		JPanel panel = new JPanel();
		panel.add(button5);
		getContentPane().add(panel, BorderLayout.NORTH);
		setVisible(true);
		faceDetector = new CascadeClassifier(path + cascade);
		cameraImage();
	}

	public void paint(Graphics g) {
		if (bimage1 != null) {
			g.drawImage(bimage1, 10, 120, width, height, this);
		}
		if (faceArea != null) {
			g.setColor(Color.yellow);
			for (Rect rect : faceArea) {
				g.drawRect(rect.x / 2 + 10, rect.y / 2 + 120, rect.width / 2, rect.height / 2);
			}
			for (int i = 0; i < count; i++) {
				g.drawImage(bimage[i], 10 + 130 * i, 430, this);
			}
		}
	}

	public void cameraImage() {
		imageArray = new Mat();
		VideoCapture videoDevice = new VideoCapture(0);
		if (videoDevice.isOpened()) {
			videoDevice.read(imageArray);
			bimage1 = matToBufferedImage(imageArray);
			System.out.println("width = " + bimage1.getWidth() + " height = " + bimage1.getHeight());
			//width = bimage1.getWidth();
			//height = bimage1.getHeight();
			width = bimage1.getWidth() / 2;
			height = bimage1.getHeight() / 2;
			while (flag) {
				if (cameraSwitch) {
					videoDevice.read(imageArray);
					bimage1 = matToBufferedImage(imageArray);
					faceDetections = new MatOfRect();
					faceDetector.detectMultiScale(imageArray, faceDetections);
					faceArea = faceDetections.toArray();
					repaint();
				} else {
					try {
						Thread.sleep(500);
					} catch(InterruptedException e) {}
				}
			}
			videoDevice.release();
		} else {
			System.out.println("Error.");
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("cameraSwitch")) {
			if (cameraSwitch) {
				//flag = false;
				//getFace();
				cameraSwitch = false;
			} else {
				cameraSwitch = true;
			}
		}
	}

	void getFace() {
		if (faceArea != null) {
			count = faceArea.length;
			if (count > 5) {
				count = 5;
			}
			bimage = new BufferedImage[count];
			for (int i = 0; i < count; i++) {
				part = new Mat(imageArray, faceArea[i]);
				bimage[i] = matToBufferedImage(part);
			}
		}
	}

	public static void main(String[] args) {
		new Presentation();
	}

	public static BufferedImage matToBufferedImage(Mat matrix) {
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
			for (int i = 0; i < data.length; i = i + 3) {
				b = data[i];
				data[i] = data[i+2];
				data[i+2] = b;
			}
			break;
		default:
			return null;

		}
		BufferedImage image2 = new BufferedImage(cols, rows, type);
		image2.getRaster().setDataElements(0,  0,  cols, rows, data);;
		return image2;
	}
}