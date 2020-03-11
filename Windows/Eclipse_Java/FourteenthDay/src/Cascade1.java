import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

public class Cascade1 extends JFrame {
	BufferedImage bimage1;
	Mat src;
	String filename = "imageB.jpg";
	String path = "C:\\Users\\codea\\opencv4.1.2\\build\\etc\\haarcascades\\";
	String cascade = "haarcascade_frontalface_default.xml";
	Rect[] faceArea = null;

	public Cascade1() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		setTitle("Cascade1");
		setSize(660, 550);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		detect();
		repaint();
	}

	public void paint(Graphics g) {
		if (bimage1 != null) {
			g.drawImage(bimage1, 10, 60, this);
		}
		if (faceArea != null) {
			g.setColor(Color.yellow);
			for (Rect rect : faceArea) {
				g.drawRect(rect.x + 10, rect.y + 60, rect.width, rect.height);
			}
		}
	}

	void detect() {
		src = new Mat();
		src = Imgcodecs.imread(filename);
		bimage1 = matToBufferedImage(src);

		CascadeClassifier faceDetector = new CascadeClassifier(path + cascade);
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(src, faceDetections);
		System.out.println("Detected " + faceDetections.toArray().length + " faces");
		faceArea = faceDetections.toArray();
	}

	public static void main(String[] args) {
		new Cascade1();
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
